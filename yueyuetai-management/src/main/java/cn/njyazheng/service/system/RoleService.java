package cn.njyazheng.service.system;

import cn.njyazheng.domain.system.DataDictionary;
import cn.njyazheng.domain.system.Permission;
import cn.njyazheng.domain.system.PermissionExample;
import cn.njyazheng.domain.system.Role;
import cn.njyazheng.mapper.system.PermissionMapper;
import cn.njyazheng.mapper.system.RoleMapper;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import cn.njyazheng.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Role> getRoleByUserName(String name) {
        return roleMapper.selectRoleByUserName(name);
    }

    public PageResult<Role> rolePageResult(Role role, Pagination pagination) {
        pagination.setStart((pagination.getPage() - 1) * pagination.getLimit());
        PageResult<Role> pageResult = new PageResult<>();
        pageResult.setCode(PageResult.NORMAL_CODE);
        pageResult.setCount(roleMapper.selectCoountByConditions(role));
        pageResult.setData(roleMapper.selectByConditions(role, pagination));
        return pageResult;
    }

    public OperatResult addRole(Role role) {
        OperatResult operatResult = new OperatResult();
        int insert = roleMapper.insertSelective(role);
        if (insert > 0) {
            operatResult.setStatus(OperatResult.SUCCESS);
            operatResult.setMsg("添加成功!");
        } else {
            operatResult.setStatus(OperatResult.ERROR);
            operatResult.setMsg("添加失败!");
        }
        return operatResult;
    }

    public OperatResult modifyRole(Role role) {
        OperatResult operatResult = new OperatResult();
        int modify = roleMapper.updateByPrimaryKeySelective(role);
        if (modify > 0) {
            operatResult.setStatus(OperatResult.SUCCESS);
            operatResult.setMsg("操作成功!");
        } else {
            operatResult.setStatus(OperatResult.ERROR);
            operatResult.setMsg("操作失败!");
        }
        return operatResult;
    }

    public Role getRole(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<TreeNode> menutree(Integer id) {
        List<DataDictionary> dataDictionaries = dataDictionaryService.getDataDictionarysBySerialNumber(1);
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andAvailableEqualTo(0);
        List<Permission> permissionVos = permissionMapper.selectByExample(permissionExample);
        List<Integer> selectIds = roleMapper.selectCheck(id);
        List<TreeNode> treeNodeList = permissionVos.stream().map(permission -> {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(permission.getId().toString());
            Optional<DataDictionary> optional = dataDictionaries
                    .stream()
                    .filter(dataDictionary -> dataDictionary.getParamno() == permission.getType())
                    .findFirst();
            String suffix = "未知操作类型按钮";
            if (optional.isPresent()) {
                suffix = optional.get().getParamname();
            }
            treeNode.setTitle(permission.getName() + "[" + suffix + "]");
            treeNode.setParentId(permission.getParentid().toString());
            treeNode.setSpread(true);
            treeNode.setType(permission.getType());
            treeNode.setChecked(selectIds.stream().anyMatch(selectId -> selectId == permission.getId()));
            return treeNode;
        }).collect(Collectors.toList());

        treeNodeList.stream().forEach(treeNode -> {
            List<TreeNode> children = treeNodeList
                    .stream()
                    .filter(tree -> tree.getParentId().equals(treeNode.getId()))
                    .collect(Collectors.toList());
            if (children != null && children.size() > 0) {
                treeNode.setChildren(children);
                treeNode.setChecked(false);
            } else {
                treeNode.setSpread(false);
            }

        });
        List<TreeNode> fulltree = new ArrayList<>();
        TreeNode root = new TreeNode();
        root.setId("0");
        root.setTitle("菜单树");
        root.setSpread(true);
        root.setDisabled(true);
        root.setChildren(treeNodeList
                .stream()
                .filter(treeNode -> treeNode.getType() == 0)
                .collect(Collectors.toList()));
        fulltree.add(root);
        return fulltree;
    }

    @Transactional
    public OperatResult grant(List<Integer> selectid, Integer id) {
        List<Integer> old = roleMapper.selectCheck(id);
        //新增的
        List<Integer> add = selectid.stream().filter(news -> !old.contains(news)).collect(Collectors.toList());
        if (add.size() > 0) {
            for (Integer permission : add) {
                roleMapper.insertRolePermisson(id, permission);
            }
        }
        //删除的
        List<Integer> delete = old.stream().filter(del -> !selectid.contains(del)).collect(Collectors.toList());
        if (delete.size() > 0) {
            for (Integer permission : delete) {
                roleMapper.deleteRolePermission(id, permission);
            }
        }
        return new OperatResult(OperatResult.SUCCESS, "权限分配成功!");
    }

    @Transactional
    public OperatResult delete(Integer id) {
        int delete = roleMapper.deleteByPrimaryKey(id);
        roleMapper.deleteRolePermisionByRoleId(id);
        if (delete > 0) {
            return new OperatResult(OperatResult.SUCCESS, "删除成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "删除失败!");
        }
    }

    public List<Role> getAllRoles() {
        return roleMapper.selectByExample(null);
    }
}
