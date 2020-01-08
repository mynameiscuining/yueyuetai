package cn.njyazheng.service.system;

import cn.njyazheng.domain.system.DataDictionary;
import cn.njyazheng.domain.system.Permission;
import cn.njyazheng.domain.system.PermissionExample;
import cn.njyazheng.mapper.system.PermissionMapper;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PermissionVo;
import cn.njyazheng.vo.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public List<PermissionVo> getGradingPermissonByUsername(String username) {
        List<PermissionVo> permissionVos = permissionMapper.getGradingPermissonByUsername(username, null);
        permissionVos.parallelStream().forEach(permissionVo -> {
            List<PermissionVo> permissionVosuns = permissionVos
                    .stream().filter(p -> p.getParentid().equals(permissionVo.getId()))
                    .collect(Collectors.toList());
            permissionVo.setPermissionVos(permissionVosuns);
        });
        return permissionVos;
    }

    public List<PermissionVo> getGradingPermissonByUsername(String username, Integer type) {
        List<PermissionVo> permissionVos = permissionMapper.getGradingPermissonByUsername(username, type);
        return permissionVos;
    }

    public List<PermissionVo> getMenuAndNav(String username) {
        return getGradingPermissonByUsername(username)
                .stream()
                .filter(permissionVo -> (permissionVo.getType() != null) && (permissionVo.getType() == 0))
                .collect(Collectors.toList());
    }

    public List<PermissionVo> getOperatorPermission(String username, String uri) {
        log.debug("getOperatorPermission-{}-{}", username, uri);
        List<PermissionVo> permissionVos = getGradingPermissonByUsername(username);
        PermissionVo permission = permissionVos
                .stream()
                .filter(permissionVo -> {
                    if (permissionVo.getUrl() == null) {
                        return false;
                    }
                    return antPathMatcher.match(permissionVo.getUrl(), uri) && permissionVo.getType() != null && permissionVo.getType() == 10;
                })
                .findFirst().orElse(null);

        if (permission != null && permission.getPermissionVos() != null) {
            List<DataDictionary> dataDictionaries = dataDictionaryService.getDataDictionarysBySerialNumber(1);
            permission.getPermissionVos().stream().forEach(permissionVo -> {
                for (DataDictionary d : dataDictionaries) {
                    if (d.getParamno() == permissionVo.getType()) {
                        permissionVo.setOperatename(d.getParamname());
                    }
                }
            });
        }
        return permission == null ? null : permission.getPermissionVos();
    }


    public boolean hasPermission(HttpServletRequest servletRequest, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String name = ((UserDetails) principal).getUsername();
            //根据用户名获取所有的可访问url
            Set<String> urls = getGradingPermissonByUsername(name).stream().map(Permission::getUrl).filter(Objects::nonNull).collect(Collectors.toSet());
            if (urls.stream().filter(url -> antPathMatcher.match(url, servletRequest.getRequestURI())).count() > 0L) {
                return true;
            }
        }
        return false;
    }

    public List<TreeNode> getMenuTree() {
        List<DataDictionary> dataDictionaries = dataDictionaryService.getDataDictionarysBySerialNumber(1);
        List<Permission> permissionVos = permissionMapper.selectByExample(null);
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
            return treeNode;
        }).collect(Collectors.toList());

        treeNodeList.stream().forEach(treeNode -> {
            List<TreeNode> children = treeNodeList
                    .stream()
                    .filter(tree -> tree.getParentId().equals(treeNode.getId()))
                    .collect(Collectors.toList());
            treeNode.setChildren(children);
        });
        List<TreeNode> fulltree = new ArrayList<>();
        TreeNode root = new TreeNode();
        root.setId("0");
        root.setTitle("菜单树");
        root.setSpread(true);
        root.setChildren(treeNodeList
                .stream()
                .filter(treeNode -> treeNode.getType() == 0)
                .collect(Collectors.toList()));
        fulltree.add(root);
        return fulltree;
    }

    public OperatResult savePermission(Permission permission) {
        String url = permission.getUrl();
        if (!StringUtils.isEmpty(url)) {
            PermissionExample permissionExample = new PermissionExample();
            PermissionExample.Criteria criteria = permissionExample.createCriteria();
            criteria.andUrlEqualTo(url);
            List<Permission> duplicates = permissionMapper.selectByExample(permissionExample);
            if (!CollectionUtils.isEmpty(duplicates) && (permission.getId() != duplicates.get(0).getId())) {
                return new OperatResult(OperatResult.ERROR, "URL重复请重填!");
            }
        }

        OperatResult operatResult = new OperatResult();
        if (permission.getType() == 0) {
            permission.setBackcolor(null);
            permission.setIcon(null);
            permission.setUrl(null);
        } else if (permission.getType() == 1) {
            permission.setBackcolor(null);
            permission.setUrl(null);
        } else if (permission.getType() == 10) {
            permission.setBackcolor(null);
            permission.setIcon(null);
        }
        if (permission.getId() == null) {
            int insert = permissionMapper.insertSelective(permission);
            if (insert > 0) {
                operatResult.setStatus(OperatResult.SUCCESS);
                operatResult.setMsg("添加权限成功!");
                operatResult.setObject(permission);
            } else {
                operatResult.setStatus(OperatResult.ERROR);
                operatResult.setMsg("添加权限失败!");
            }
        } else {
            int modify = permissionMapper.updateByPrimaryKeySelective(permission);
            if (modify > 0) {
                operatResult.setStatus(OperatResult.SUCCESS);
                operatResult.setMsg("修改权限成功!");
                operatResult.setObject(permission);
            } else {
                operatResult.setStatus(OperatResult.ERROR);
                operatResult.setMsg("修改权限失败!");
            }
        }
        return operatResult;
    }

    @Transactional
    public OperatResult deletePermission(Integer id) {
        OperatResult operatResult = new OperatResult();
        int delete = permissionMapper.deleteByPrimaryKey(id);
        permissionMapper.deleteRelations(id);
        if (delete > 0) {
            operatResult.setStatus(OperatResult.SUCCESS);
            operatResult.setMsg("删除成功!");
        } else {
            operatResult.setStatus(OperatResult.ERROR);
            operatResult.setMsg("删除失败!");
        }
        return operatResult;
    }

    public OperatResult getPermission(Integer id) {
        OperatResult operatResult = new OperatResult();
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        if (permission != null) {
            PermissionVo permissionVo = new PermissionVo();
            BeanUtils.copyProperties(permission, permissionVo);
            Permission parent = permissionMapper.selectByPrimaryKey(permissionVo.getParentid());
            if (parent != null) {
                permissionVo.setParentName(parent.getName());
            } else {
                permissionVo.setParentName("菜单树");
            }
            operatResult.setStatus(OperatResult.SUCCESS);
            operatResult.setObject(permissionVo);
        } else {
            operatResult.setStatus(OperatResult.ERROR);
            operatResult.setMsg("未获取到数据!");
        }
        return operatResult;
    }


}
