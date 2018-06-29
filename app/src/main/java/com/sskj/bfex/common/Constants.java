package com.sskj.bfex.common;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class Constants {
    public static final String SP_Mobile = "sp_mobile";
    public static final String NULL_Str = "";
    public static final String PHONE = "phone";
    public static final String IS_RESET = "isReset";
    public static final String USER = "user";
    public static final int USER_LOGIN = 1000;
    public static final int USER_LOGOUT = 1005;
    public static final int WALLET_ADDRESS_MANAGE = 1001;
    public static final int PAY_PWD_INPUT = 1002;
    public static final int SUCCESS = 1;
    public static final String ADDRESS = "address";
    public static final String NOTICE = "notice";


    public static final int TRANSACTION = 111;

    public static final int LEVELTRANSACTION = 0x1110;
    /**
     * 用户数据对象（序列化对象）
     */
    public static final String SP_USER_INFO = "sp_user_email";
    /**
     * 用户登录状态  boolean 类型
     */
    public static final String SP_LOGIN_STATUS = "sp_login_status";
    public static final String CODE = "code";
    public static final String COIN_TYPE = "coin_type";
    public static final String ID = "id";
    public static final int MARKET = 1102;
    /**
     * 登录状态 true and false
     */
    public static boolean mIsLogin = false;
    public static String mobile;
    /**
     * 获取提现地址
     * startActForResult requestCode
     */
    public static final int CASH_ADDRESS = 1100;
    /**
     * 绑定邮箱
     * startActForResult requestCode
     */
    public static final int BIND_EMAIL_CODE = 1101;

    /**
     * 设置交易密码
     * startActForResult requestCode
     */
    public static final int PAY_PWD_SETTING_CODE = 1102;
    /**
     * 实名认证
     * startActForResult requestCode
     */
    public static final int VERIFY_IDENT_CODE = 1103;
    /**
     * 扫描钱包地址二维码
     */
    public static final int SACN_QR = 1104;
    /**
     * 相机权限
     */
    public static int PERMISSION_REQUEST_CAMERA = 2000;
}
