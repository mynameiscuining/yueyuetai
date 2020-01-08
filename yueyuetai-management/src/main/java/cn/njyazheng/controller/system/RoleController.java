package cn.njyazheng.controller.system;

import cn.njyazheng.domain.system.Role;
import cn.njyazheng.service.system.RoleService;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import cn.njyazheng.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public PageResult<Role> getRoleList(Role role, Pagination pagination) {
        return roleService.rolePageResult(role, pagination);
    }

    @PostMapping("/add")
    public OperatResult addRole(Role role) {
        return roleService.addRole(role);
    }

    @PostMapping("/modify")
    public OperatResult modify(Role role) {
        return roleService.modifyRole(role);
    }

    @PostMapping("/available")
    public OperatResult modifyAvailable(Role role) {
        return roleService.modifyRole(role);
    }

    @GetMapping("/select/menutree/{id}")
    public List<TreeNode> selectMenuTree(@PathVariable("id") Integer id) {
        return roleService.menutree(id);
    }

    @PostMapping("/grant/{id}")
    public OperatResult grant(@RequestParam(value = "selectids[]", required = false) List<Integer> selectids, @PathVariable("id") Integer id) {
        if (selectids == null) {
            selectids = new ArrayList<Integer>();
        }
        return roleService.grant(selectids, id);
    }

    @PostMapping("/delete/{id}")
    public OperatResult delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
