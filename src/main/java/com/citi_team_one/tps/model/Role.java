package com.citi_team_one.tps.model;

public class Role {
    private Integer id;

    private String role;

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}