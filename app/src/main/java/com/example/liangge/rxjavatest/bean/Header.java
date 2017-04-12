package com.example.liangge.rxjavatest.bean;

/**
 * Created by liangge on 2017/4/12.
 */

public class Header {


    /**
     * user_id : 373F0C4ED4D444C6B50B3633EBEC9080
     * service_code : ebt-003
     * sign : 7FB6474E620E20CB920CB37351CEB657
     * serial_id : 9d5bab8c09df473eaae348e077d93ca6
     * request_time : 2017-04-12 22:19:02
     * response_code : 010003
     * response_msg : {"default_msg":"密码错误"}
     */

    private String user_id;
    private String service_code;
    private String sign;
    private String serial_id;
    private String request_time;
    private String response_code;
    private ResponseMsgBean response_msg;
    private static final String SUCCESS = "0";

    public boolean success() {
        return (response_code.equals(SUCCESS));
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public ResponseMsgBean getResponse_msg() {
        return response_msg;
    }

    public void setResponse_msg(ResponseMsgBean response_msg) {
        this.response_msg = response_msg;
    }

    public static class ResponseMsgBean {
        /**
         * default_msg : 密码错误
         */

        private String default_msg;

        public String getDefault_msg() {
            return default_msg;
        }

        public void setDefault_msg(String default_msg) {
            this.default_msg = default_msg;
        }
    }
}
