package cn.njyazheng.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo {

    //正常
    public static final int USER_STATUS_NORMAL = 0;
    //暂停
    public static final int USER_STATUS_PAUSE = 1;
    //未锁定
    public static final int USER_UNLOCKED = 0;
    //锁定
    public static final int USER_LOCKED = 1;

    private Integer id;

    private String phone;

    private String username;
    @JsonIgnore
    private String password;

    private Integer locked;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}