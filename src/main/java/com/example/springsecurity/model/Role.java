package com.example.springsecurity.model;

import com.example.springsecurity.enumeration.Authority;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author ClowLAY
 * create date 2020/5/23
 */
@Document("role")
public class Role {

    @Id
    private ObjectId id;

    /**
     * 角色名称
     */
    @Field("role_name")
    private  String roleName;

    /**
     * 权限列表
     */
    @Field("role_list")
    private List<Authority> authorities;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", authorityList=" + authorities +
                '}';
    }
}
