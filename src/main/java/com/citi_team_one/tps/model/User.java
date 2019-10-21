package com.citi_team_one.tps.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class User implements Serializable {

    private Integer id;
    private String name;
    private String pwd;
    private Integer roleId;
    

    public User(Integer id, String name, String pwd, Integer roleId) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.roleId = roleId;
    }

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}