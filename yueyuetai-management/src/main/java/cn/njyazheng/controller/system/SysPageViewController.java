package cn.njyazheng.controller.system;

import cn.njyazheng.domain.system.DataDictionary;
import cn.njyazheng.domain.system.Role;
import cn.njyazheng.service.system.DataDictionaryService;
import cn.njyazheng.service.system.PermissionService;
import cn.njyazheng.service.system.RoleService;
import cn.njyazheng.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class SysPageViewController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/")
    public String index(Principal principal, ModelMap modelMap) {
        String name = principal.getName();
        List<PermissionVo> permissionVos = permissionService.getMenuAndNav(name);
        modelMap.put("permissionVos", permissionVos);
        return "index.html";
    }

    @RequestMapping("/permission")
    public String toPermissionPage(ModelMap modelMap) {
        List<DataDictionary> menuTypes = dataDictionaryService.getDataDictionarysBySerialNumber(1);
        modelMap.put("menuTypes", menuTypes);
        return "organization/permission.html";
    }


    @RequestMapping({"/role/page/add/{id}", "/role/page/add"})
    public String toRoleAdd(@PathVariable(value = "id", required = false) Integer id, ModelMap modelMap) {
        if (id != null) {
            modelMap.put("role", roleService.getRole(id));
        } else {
            Role role = new Role();
            role.setAvailable(0);
            modelMap.put("role", role);
        }
        return "organization/role-add.html";
    }

    @RequestMapping({"/dictionary/page/add/{serial_number}/{paramno}", "/dictionary/page/add/{serial_number}", "/dictionary/page/add"})
    public String toDictionaryAdd(@PathVariable(value = "serial_number", required = false) Integer serialNumber,
                                  @PathVariable(value = "paramno", required = false) Integer paramno, ModelMap modelMap) {
        if (serialNumber != null || paramno != null) {
            modelMap.put("dictionary", dataDictionaryService.getByID(serialNumber, paramno));
            modelMap.put("action", "modify");
        } else {
            DataDictionary dataDictionary = new DataDictionary();
            modelMap.put("dictionary", dataDictionary);
            modelMap.put("action", "add");
        }
        return "setting/dictionary-add.html";
    }
}
