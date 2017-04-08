package com.example.liangge.rxjavatest.common.constant;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by guhongliang on 2017/3/30.
 */

public class UserStore {


    /**
     * header : {"response_code":"0","response_msg":""}
     * data : {"isstar":"未查询到相关数据！！","year":"2017","month":"3"}
     */

    private HeaderBean header;
    private DataBean data;

    @Inject
    public UserStore() {
    }

    public void register() {
        Log.d("UserStore", "register:执行了 ");
    }

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
         * response_msg :
         */

        private String response_code;
        private String response_msg;

        public String getResponse_code() {
            return response_code;
        }

        public void setResponse_code(String response_code) {
            this.response_code = response_code;
        }

        public String getResponse_msg() {
            return response_msg;
        }

        public void setResponse_msg(String response_msg) {
            this.response_msg = response_msg;
        }
    }

    public static class DataBean {
        /**
         * isstar : 未查询到相关数据！！
         * year : 2017
         * month : 3
         */

        private String isstar;
        private String year;
        private String month;

        public String getIsstar() {
            return isstar;
        }

        public void setIsstar(String isstar) {
            this.isstar = isstar;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
