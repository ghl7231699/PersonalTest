package com.example.liangge.rxjavatest.bean;

import java.util.List;

/**
 * Created by liangge on 2017/4/11.
 */

public class UserInfo {
    private String id;
    private String memberId;
    private String agentCode;
    private String sex;
    private String certificateType;
    private String certificateNo;
    private String status;
    private String comcode;
    private String comname;
    private String branchattr;
    private String branchname;
    private String agentgradecode;
    private String name;
    private String birthday;
    private String mobilePhone;
    private String postaladdress;
    private String branchtype;
    private String channel_id;
    private String session_key;
    private String gxrq;
    private String isFirstLogin;
    private List<BeanTest.DataBean.AgentPayMethodBean> agentPayMethod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getBranchattr() {
        return branchattr;
    }

    public void setBranchattr(String branchattr) {
        this.branchattr = branchattr;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getAgentgradecode() {
        return agentgradecode;
    }

    public void setAgentgradecode(String agentgradecode) {
        this.agentgradecode = agentgradecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPostaladdress() {
        return postaladdress;
    }

    public void setPostaladdress(String postaladdress) {
        this.postaladdress = postaladdress;
    }

    public String getBranchtype() {
        return branchtype;
    }

    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getGxrq() {
        return gxrq;
    }

    public void setGxrq(String gxrq) {
        this.gxrq = gxrq;
    }

    public String getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(String isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public List<BeanTest.DataBean.AgentPayMethodBean> getAgentPayMethod() {
        return agentPayMethod;
    }

    public void setAgentPayMethod(List<BeanTest.DataBean.AgentPayMethodBean> agentPayMethod) {
        this.agentPayMethod = agentPayMethod;
    }

    public static class AgentPayMethodBean {
        /**
         * agentCode : 38232801
         * payMethodCode : 1
         * payMethodName : 转账支付
         */

        private String agentCode;
        private String payMethodCode;
        private String payMethodName;

        public String getAgentCode() {
            return agentCode;
        }

        public void setAgentCode(String agentCode) {
            this.agentCode = agentCode;
        }

        public String getPayMethodCode() {
            return payMethodCode;
        }

        public void setPayMethodCode(String payMethodCode) {
            this.payMethodCode = payMethodCode;
        }

        public String getPayMethodName() {
            return payMethodName;
        }

        public void setPayMethodName(String payMethodName) {
            this.payMethodName = payMethodName;
        }
    }
}
