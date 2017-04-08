package com.example.liangge.rxjavatest.common.constant;

import com.google.gson.Gson;

/**
 * Created by liangge on 2017/3/26.
 */

public class User {
    private String head_url;
    private String id;
    private String username;
    private String agentCode;
    private String name;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String head_url, String id, String username) {
        this.head_url = head_url;
        this.id = id;
        this.username = username;
    }

    public User() {
    }

    public User(String head_url, String id) {
        this.head_url = head_url;
        this.id = id;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
