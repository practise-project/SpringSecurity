package com.example.springsecurity.enumeration;

/**
 * 权限列表
 * @author ClowLAY
 * create date 2020/5/23
 */

public enum Authority {

    WAM_USER("用户管理-用户"),

    DASHBOARD_DASHBOARD_READ("面板管理");

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    Authority(String description) {
        this.description = description;
    }
}
