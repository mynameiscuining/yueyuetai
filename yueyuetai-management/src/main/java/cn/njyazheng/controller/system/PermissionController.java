package cn.njyazheng.controller.system;

import cn.njyazheng.domain.system.Permission;
import cn.njyazheng.service.system.PermissionService;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public List<TreeNode> menuTree() {
        return permissionService.getMenuTree();
    }

    @GetMapping("/detail/{id}")
    public OperatResult getPermission(@PathVariable Integer id) {
        return permissionService.getPermission(id);
    }

    @PostMapping("/{action}")
    public OperatResult savePermission(Permission permission, @PathVariable("action") String action) {
        return permissionService.savePermission(permission);
    }

    @PostMapping("/delete/{id}")
    public OperatResult savePermission(@PathVariable("id") Integer id) {
        return permissionService.deletePermission(id);
    }
}
