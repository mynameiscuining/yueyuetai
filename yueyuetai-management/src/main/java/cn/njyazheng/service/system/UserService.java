package cn.njyazheng.service.system;

import cn.njyazheng.domain.system.Role;
import cn.njyazheng.domain.system.UserInfo;
import cn.njyazheng.domain.system.UserInfoExample;
import cn.njyazheng.mapper.system.UserInfoMapper;
import cn.njyazheng.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andUsernameEqualTo(username);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoExample);
        if (userInfoList.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        UserInfo userInfo = userInfoList.get(0);
        String password = userInfo.getPassword();
        boolean enbled = userInfo.getStatus() == UserInfo.USER_STATUS_NORMAL;
        boolean accountNonLocked = userInfo.getLocked() == UserInfo.USER_UNLOCKED;
        List<Role> roleList = roleService.getRoleByUserName(username);
        if (roleList.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> grantedAuthorities = roleList
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(username, password, enbled,
                true, true, accountNonLocked, grantedAuthorities);
    }

    public PageResult<UserInfo> findUserInfos(UserInfo userInfo, Pagination pagination) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        if (!StringUtils.isEmpty(userInfo.getUsername())) {
            criteria.andUsernameLike("%" + userInfo.getUsername() + "%");
        }
        if (userInfo.getStatus() != null) {
            criteria.andStatusEqualTo(userInfo.getStatus());
        }
        if (userInfo.getLocked() != null) {
            criteria.andLockedEqualTo(userInfo.getLocked());
        }

        int count = (int) userInfoMapper.countByExample(userInfoExample);
        pagination.setStart((pagination.getPage() - 1) * pagination.getLimit());
        List<UserInfo> userInfoList = userInfoMapper.selectForPaganation(userInfo, pagination);
        PageResult<UserInfo> pageResult = new PageResult<>();
        pageResult.setCode(PageResult.NORMAL_CODE);
        pageResult.setCount(count);
        pageResult.setData(userInfoList);
        return pageResult;
    }

    @Transactional
    public OperatResult addUser(UserInfo userInfo) {
        OperatResult operatResult = new OperatResult();
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().
                andUsernameEqualTo(userInfo.getUsername());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        if (userInfos != null && !userInfos.isEmpty()) {
            return new OperatResult(OperatResult.ERROR, "用户已注册!");
        }
        String pwd = passwordEncoder.encode("111111");
        userInfo.setPassword(pwd);
        boolean insert = userInfoMapper.insertSelective(userInfo) > 0;
        Integer status = insert ? OperatResult.SUCCESS : OperatResult.ERROR;
        String msg = status == OperatResult.SUCCESS ? "用户添加成功" : "用户添加失败";
        operatResult.setMsg(msg);
        operatResult.setStatus(status);
        return operatResult;
    }

    public OperatResult modifyUserByID(UserInfo userInfo) {
        int modify = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (modify > 0) {
            return new OperatResult(OperatResult.SUCCESS, "修改成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "修改失败!");
        }
    }

    public OperatResult resetpwd(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode("111111"));
        return modifyUserByID(userInfo);
    }

    public OperatResult deleteUserByID(Integer id) {
        int del = userInfoMapper.deleteByPrimaryKey(id);
        if (del > 0) {
            return new OperatResult(OperatResult.SUCCESS, "删除成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "删除失败!");
        }
    }


    public TransferVo getRolesForUser(Integer id) {
        List<Transfer> transfers = roleService.getAllRoles().stream().map(role -> {
            Transfer transfer = new Transfer();
            transfer.setValue(role.getId());
            transfer.setTitle(role.getName());
            transfer.setDisabled(role.getAvailable() == 1);
            return transfer;
        }).collect(Collectors.toList());
        List<Integer> select = userInfoMapper.selectRoleID(id);
        TransferVo transferVo = new TransferVo();
        transferVo.setData(transfers);
        transferVo.setSelect(select);
        return transferVo;
    }

    @Transactional
    public OperatResult grant(List<Integer> selectid, Integer id) {
        List<Integer> old = userInfoMapper.selectRoleID(id);
        //新增的
        List<Integer> add = selectid.stream().filter(news -> !old.contains(news)).collect(Collectors.toList());
        if (add.size() > 0) {
            for (Integer permission : add) {
                userInfoMapper.insertUserRole(id, permission);
            }
        }
        //删除的
        List<Integer> delete = old.stream().filter(del -> !selectid.contains(del)).collect(Collectors.toList());
        if (delete.size() > 0) {
            for (Integer permission : delete) {
                userInfoMapper.deleteUserRole(id, permission);
            }
        }
        return new OperatResult(OperatResult.SUCCESS, "用户权限分配成功!");
    }
}
