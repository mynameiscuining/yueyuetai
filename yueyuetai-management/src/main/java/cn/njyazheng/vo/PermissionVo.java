package cn.njyazheng.vo;

import cn.njyazheng.domain.system.Permission;

import java.util.List;

public class PermissionVo extends Permission {
    private String parentName;
    private String operatename;
    private List<PermissionVo> permissionVos;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOperatename() {
        return operatename;
    }

    public void setOperatename(String operatename) {
        this.operatename = operatename;
    }

    public List<PermissionVo> getPermissionVos() {
        return permissionVos;
    }

    public void setPermissionVos(List<PermissionVo> permissionVos) {
        this.permissionVos = permissionVos;
    }
}
