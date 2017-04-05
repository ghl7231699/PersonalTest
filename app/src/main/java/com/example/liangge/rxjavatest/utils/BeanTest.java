package com.example.liangge.rxjavatest.utils;

import java.util.List;

/**
 * Created by guhongliang on 2017/3/29.
 */

public class BeanTest {

    /**
     * header : {"response_code":"0"}
     * data : {"id":"934868364F934FC9A4F073CD8B86CF9D","memberId":"F57D40BB4B6A428D9EABDCB48B9C6D71","agentCode":"38232801","sex":"1","certificateType":"0","certificateNo":"110226198309030520","status":"01","comcode":"86210055","comname":"北京直属分公司","branchattr":"010000006048","branchname":"新华人寿北京分公司","agentgradecode":"A101","name":"贾玉伶","birthday":"1983-09-03 00:00:00","mobilePhone":"18801029689","postaladdress":"北京平谷兴谷中罗庄西路187号","branchtype":"1","channel_id":"2234DA74C0B78172E050A8C03F002B80","session_key":"a6b762b753b14aec8c2f43dc7458cd1a","gxrq":"2015-12-01","isFirstLogin":"0","agentPayMethod":[{"agentCode":"38232801","payMethodCode":"1","payMethodName":"转账支付"},{"agentCode":"38232801","payMethodCode":"1","payMethodName":"转账支付"}]}
     */

    private HeaderBean header;
    private DataBean data;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class HeaderBean {
        /**
         * response_code : 0
         */

        private String response_code;

        public String getResponse_code() {
            return response_code;
        }

        public void setResponse_code(String response_code) {
            this.response_code = response_code;
        }
    }

    public static class DataBean {
        /**
         * id : 934868364F934FC9A4F073CD8B86CF9D
         * memberId : F57D40BB4B6A428D9EABDCB48B9C6D71
         * agentCode : 38232801
         * sex : 1
         * certificateType : 0
         * certificateNo : 110226198309030520
         * status : 01
         * comcode : 86210055
         * comname : 北京直属分公司
         * branchattr : 010000006048
         * branchname : 新华人寿北京分公司
         * agentgradecode : A101
         * name : 贾玉伶
         * birthday : 1983-09-03 00:00:00
         * mobilePhone : 18801029689
         * postaladdress : 北京平谷兴谷中罗庄西路187号
         * branchtype : 1
         * channel_id : 2234DA74C0B78172E050A8C03F002B80
         * session_key : a6b762b753b14aec8c2f43dc7458cd1a
         * gxrq : 2015-12-01
         * isFirstLogin : 0
         * agentPayMethod : [{"agentCode":"38232801","payMethodCode":"1","payMethodName":"转账支付"},{"agentCode":"38232801","payMethodCode":"1","payMethodName":"转账支付"}]
         */

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
        private List<AgentPayMethodBean> agentPayMethod;

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

        public List<AgentPayMethodBean> getAgentPayMethod() {
            return agentPayMethod;
        }

        public void setAgentPayMethod(List<AgentPayMethodBean> agentPayMethod) {
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
}
