package cn.njyazheng.controller.system;

import cn.njyazheng.domain.system.UserInfo;
import cn.njyazheng.service.system.UserService;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import cn.njyazheng.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userinfo")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public PageResult<UserInfo> loadUserList(UserInfo userInfo, Pagination pagination) {
        return userService.findUserInfos(userInfo, pagination);
    }

    @PostMapping("/add")
    public OperatResult addUser(UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/status")
    public OperatResult modifyStatus(UserInfo userInfo) {
        return userService.modifyUserByID(userInfo);
    }

    @PostMapping("/lock")
    public OperatResult modifyLock(UserInfo userInfo) {
        return userService.modifyUserByID(userInfo);
    }

    @PostMapping("/resetpwd")
    public OperatResult resetpwd(UserInfo userInfo) {
        return userService.resetpwd(userInfo);
    }

    @PostMapping("/delete/{id}")
    public OperatResult delete(@PathVariable Integer id) {
        return userService.deleteUserByID(id);
    }

    @GetMapping("/select/role/{id}")
    public TransferVo grant(@PathVariable("id") Integer id) {
        return userService.getRolesForUser(id);
    }

    @PostMapping("/grant/{id}")
    public OperatResult grant(@RequestParam(value = "selectids[]", required = false) List<Integer> selectids, @PathVariable("id") Integer id) {
        if (selectids == null) {
            selectids = new ArrayList<Integer>();
        }
        return userService.grant(selectids, id);
    }
}
