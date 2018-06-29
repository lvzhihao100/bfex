package com.sskj.bfex.m.bean;

import com.sskj.bfex.m.bean.bean.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class AddressList extends BaseBean{

    /**
     * status : 200
     * data : {"wallone":"522614.7964","tb_fee":"0.1","res":[{"id":"3","cat":"USDT","qiaobao_url":"1EYWD1GbUGRqKkctd538XdL93UUyxCj3rx","notes":""}]}
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wallone : 522614.7964
         * tb_fee : 0.1
         * res : [{"id":"3","cat":"USDT","qiaobao_url":"1EYWD1GbUGRqKkctd538XdL93UUyxCj3rx","notes":""}]
         */

        private String wallone;
        private String tb_fee;
        private List<ResBean> res;

        public String getWallone() {
            return wallone;
        }

        public void setWallone(String wallone) {
            this.wallone = wallone;
        }

        public String getTb_fee() {
            return tb_fee;
        }

        public void setTb_fee(String tb_fee) {
            this.tb_fee = tb_fee;
        }

        public List<ResBean> getRes() {
            return res;
        }

        public void setRes(List<ResBean> res) {
            this.res = res;
        }

        public static class ResBean {
            /**
             * id : 3
             * cat : USDT
             * qiaobao_url : 1EYWD1GbUGRqKkctd538XdL93UUyxCj3rx
             * notes :
             */

            private String id;
            private String cat;
            private String qiaobao_url;
            private String notes;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCat() {
                return cat;
            }

            public void setCat(String cat) {
                this.cat = cat;
            }

            public String getQiaobao_url() {
                return qiaobao_url;
            }

            public void setQiaobao_url(String qiaobao_url) {
                this.qiaobao_url = qiaobao_url;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }
        }
    }
}
