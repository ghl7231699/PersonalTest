package com.example.liangge.rxjavatest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guhongliang on 2017/8/23.
 */
@Entity
public class User {
    @Id
    private long id;
    @Property(nameInDb = "AGENTCODE")
    private String agentCode;
    @Property(nameInDb = "SEX")
    private String sex;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "BIRTHDAY")
    private String birthday;
    @Property(nameInDb = "MOBILEPHONE")
    private String mobilePhone;
    @Generated(hash = 1068694714)
    public User(long id, String agentCode, String sex, String name, String birthday,
            String mobilePhone) {
        this.id = id;
        this.agentCode = agentCode;
        this.sex = sex;
        this.name = name;
        this.birthday = birthday;
        this.mobilePhone = mobilePhone;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAgentCode() {
        return this.agentCode;
    }
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getMobilePhone() {
        return this.mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
