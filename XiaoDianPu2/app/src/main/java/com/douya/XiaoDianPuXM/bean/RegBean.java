package com.douya.XiaoDianPuXM.bean;

/**
 * Created by 杨圆圆 on 2017/10/11.
 */

public class RegBean {

    /**
     * code : 200
     * datas : {"username":"andro","userid":"8","key":"ce30718ab0d11dac3036b959350d4d53"}
     */

    private int code;
    private DatasBean datas;

    @Override
    public String toString() {
        return "RegBean{" +
                "code=" + code +
                ", datas=" + datas +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * username : andro
         * userid : 8
         * key : ce30718ab0d11dac3036b959350d4d53
         */

        private String username;
        private String userid;
        private String key;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
    }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "username='" + username + '\'' +
                    ", userid='" + userid + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }
    }
}
