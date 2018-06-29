package com.sskj.bfex.m.bean;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LoginBean {


    /**
     * id : 7
     * userId : 3
     * nickname : 刘小雨
     * username : 刘小雨
     * account : 13717645021
     * tel : 13717645021
     * pswd : e10adc3949ba59abbe56e057f20f883e
     * deviceNo : null
     * createTime : 2018-06-11 16:35:17
     * status : 1
     * stockUserInfo : {"id":7,"stockUserId":7,"cardNo":"123456789012345","bankCardNo":null,"cardExpdate":null,"cardImg":"/file/1528719356721fc5e2426-fe4c-4d70-9c3f-ae53cef89b1e.png","cardType":"中国银行","usableFund":"0","allFund":null,"floatFee":null,"markValue":null,"usdFee":null,"outFee":null,"inFee":null,"proportion":null,"frostDeposit":null,"isCanUseFund":null,"hasUsedFund":null,"tradePwd":null,"sex":null,"address":null,"province":null,"city":null,"county":null,"idCardNo":null,"idCardFront":"/file/152871935174397138df0-72b4-4cc5-ab2a-3a8d58ab435d.png","idCardBack":"/file/1528719351749b940ae44-dc74-4964-861a-114101f02b4f.png","selfPortrait":"/file/1528719351737ea32a759-8611-4cbc-8fd9-bf513f76a8b8.png","checkReason":null,"checkTime":null,"createTime":null,"status":null,"timestamp":null}
     * timestamp : 1528763627000
     * yyId : 2
     * oid : 2
     * level : 2
     * number : LV701
     * disable : 1
     * isApplyStatus : 3
     * applyRefuseReason : null
     * accountType : 1
     * concessionRate : 20.0
     */

    private int id;
    private int userId;
    private String nickname;
    private String username;
    private String account;
    private String tel;
    private String pswd;
    private String deviceNo;
    private String createTime;
    private int status;
    private StockUserInfoBean stockUserInfo;
    private long timestamp;
    private int yyId;
    private int oid;
    private int level;
    private String number;
    private int disable;
    private int isApplyStatus;
    private String applyRefuseReason;
    private int accountType;
    private double concessionRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StockUserInfoBean getStockUserInfo() {
        return stockUserInfo;
    }

    public void setStockUserInfo(StockUserInfoBean stockUserInfo) {
        this.stockUserInfo = stockUserInfo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getYyId() {
        return yyId;
    }

    public void setYyId(int yyId) {
        this.yyId = yyId;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getIsApplyStatus() {
        return isApplyStatus;
    }

    public void setIsApplyStatus(int isApplyStatus) {
        this.isApplyStatus = isApplyStatus;
    }

    public String getApplyRefuseReason() {
        return applyRefuseReason;
    }

    public void setApplyRefuseReason(String applyRefuseReason) {
        this.applyRefuseReason = applyRefuseReason;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public double getConcessionRate() {
        return concessionRate;
    }

    public void setConcessionRate(double concessionRate) {
        this.concessionRate = concessionRate;
    }

    public static class StockUserInfoBean {
        /**
         * id : 7
         * stockUserId : 7
         * cardNo : 123456789012345
         * bankCardNo : null
         * cardExpdate : null
         * cardImg : /file/1528719356721fc5e2426-fe4c-4d70-9c3f-ae53cef89b1e.png
         * cardType : 中国银行
         * usableFund : 0
         * allFund : null
         * floatFee : null
         * markValue : null
         * usdFee : null
         * outFee : null
         * inFee : null
         * proportion : null
         * frostDeposit : null
         * isCanUseFund : null
         * hasUsedFund : null
         * tradePwd : null
         * sex : null
         * address : null
         * province : null
         * city : null
         * county : null
         * idCardNo : null
         * idCardFront : /file/152871935174397138df0-72b4-4cc5-ab2a-3a8d58ab435d.png
         * idCardBack : /file/1528719351749b940ae44-dc74-4964-861a-114101f02b4f.png
         * selfPortrait : /file/1528719351737ea32a759-8611-4cbc-8fd9-bf513f76a8b8.png
         * checkReason : null
         * checkTime : null
         * createTime : null
         * status : null
         * timestamp : null
         */

        private int id;
        private int stockUserId;
        private String cardNo;
        private String bankCardNo;
        private String cardExpdate;
        private String cardImg;
        private String cardType;
        private String usableFund;
        private String allFund;
        private String floatFee;
        private String markValue;
        private String usdFee;
        private String outFee;
        private String inFee;
        private String proportion;
        private String frostDeposit;
        private String isCanUseFund;
        private String hasUsedFund;
        private String tradePwd;
        private String sex;
        private String address;
        private String province;
        private String city;
        private String county;
        private String idCardNo;
        private String idCardFront;
        private String idCardBack;
        private String selfPortrait;
        private String checkReason;
        private String checkTime;
        private String createTime;
        private String status;
        private String timestamp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStockUserId() {
            return stockUserId;
        }

        public void setStockUserId(int stockUserId) {
            this.stockUserId = stockUserId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public void setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
        }

        public String getCardExpdate() {
            return cardExpdate;
        }

        public void setCardExpdate(String cardExpdate) {
            this.cardExpdate = cardExpdate;
        }

        public String getCardImg() {
            return cardImg;
        }

        public void setCardImg(String cardImg) {
            this.cardImg = cardImg;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getUsableFund() {
            return usableFund;
        }

        public void setUsableFund(String usableFund) {
            this.usableFund = usableFund;
        }

        public String getAllFund() {
            return allFund;
        }

        public void setAllFund(String allFund) {
            this.allFund = allFund;
        }

        public String getFloatFee() {
            return floatFee;
        }

        public void setFloatFee(String floatFee) {
            this.floatFee = floatFee;
        }

        public String getMarkValue() {
            return markValue;
        }

        public void setMarkValue(String markValue) {
            this.markValue = markValue;
        }

        public String getUsdFee() {
            return usdFee;
        }

        public void setUsdFee(String usdFee) {
            this.usdFee = usdFee;
        }

        public String getOutFee() {
            return outFee;
        }

        public void setOutFee(String outFee) {
            this.outFee = outFee;
        }

        public String getInFee() {
            return inFee;
        }

        public void setInFee(String inFee) {
            this.inFee = inFee;
        }

        public String getProportion() {
            return proportion;
        }

        public void setProportion(String proportion) {
            this.proportion = proportion;
        }

        public String getFrostDeposit() {
            return frostDeposit;
        }

        public void setFrostDeposit(String frostDeposit) {
            this.frostDeposit = frostDeposit;
        }

        public String getIsCanUseFund() {
            return isCanUseFund;
        }

        public void setIsCanUseFund(String isCanUseFund) {
            this.isCanUseFund = isCanUseFund;
        }

        public String getHasUsedFund() {
            return hasUsedFund;
        }

        public void setHasUsedFund(String hasUsedFund) {
            this.hasUsedFund = hasUsedFund;
        }

        public String getTradePwd() {
            return tradePwd;
        }

        public void setTradePwd(String tradePwd) {
            this.tradePwd = tradePwd;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public String getIdCardBack() {
            return idCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            this.idCardBack = idCardBack;
        }

        public String getSelfPortrait() {
            return selfPortrait;
        }

        public void setSelfPortrait(String selfPortrait) {
            this.selfPortrait = selfPortrait;
        }

        public String getCheckReason() {
            return checkReason;
        }

        public void setCheckReason(String checkReason) {
            this.checkReason = checkReason;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
