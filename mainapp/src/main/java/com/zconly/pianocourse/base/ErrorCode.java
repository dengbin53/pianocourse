package com.zconly.pianocourse.base;

/**
 * 请求返回状态
 *
 * @author DengBin
 */
public class ErrorCode {

    // 系统错误（底层）
    public static final int SUCCESS = 0;                // 成功
    public static final int OTHER_ERROR = 1;            // 其他错误
//    public static final int SUCCESS = 100;// 用户不存在或密码不正确 |
//    public static final int SUCCESS = 101;// 用户名不为空 |
//    public static final int SUCCESS = 102;// 密码不能为空 |
//    public static final int SUCCESS = 103;// 验证码不能为空 |
//    public static final int SUCCESS = 104;// 昵称不能为空 |
//    public static final int SUCCESS = 105;// 生日不能为空 |
//    public static final int SUCCESS = 106;// 性别不能为空 |
//    public static final int SUCCESS = 107;// 兴趣不能为空 |
//    public static final int SUCCESS = 108;// 国籍不能为空 |
//    public static final int SUCCESS = 109;// 照片地址不能为空 |
//    public static final int SUCCESS = 110;// 真实姓名不能为空 |
//    public static final int SUCCESS = 111;// 身份证不能为空 |
//    public static final int SUCCESS = 112;// 邮箱不能为空 |
//    public static final int SUCCESS = 113;// 微信号不能为空 |
//    public static final int SUCCESS = 114;// 详细地址不能为空 |
//    public static final int SUCCESS = 115;// 开户行不能为空 |
//    public static final int SUCCESS = 116;// 银行卡号不为空 |
//    public static final int SUCCESS = 117;// 是否有驾照不能为空 |
//    public static final int SUCCESS = 118;// 是否有车不能为空 |
//    public static final int SUCCESS = 119;// 所属行业不能为空 |
//    public static final int SUCCESS = 120;// 公司/学校不能为空 |
//    public static final int SUCCESS = 121;// 用户不存在 |
//    public static final int SUCCESS = 122;// 用户反馈意见必须大于20个字符 |
//    public static final int SUCCESS = 123;// 城市不能为空 |
//    public static final int SUCCESS = 124;// 语言不能为空 |
//    public static final int SUCCESS = 125;// 内容类型不能为空 |
//    public static final int SUCCESS = 126;// 内容id不能为空 |
//    public static final int SUCCESS = 127;// 内容不存在 |
//    public static final int SUCCESS = 128;// 订单id不能为空 |
//    public static final int SUCCESS = 129;// 支付凭证照片地址不能为空 |
//    public static final int SUCCESS = 130;// 护照第一页照片地址不能为空 |
//    public static final int SUCCESS = 131;// 订单不存在 |
//    public static final int SUCCESS = 132;// 用户id不能为空 |
//    public static final int SUCCESS = 133;// 评价内容不能为空 |
//    public static final int SUCCESS = 134;// 分数不能为空 |
//    public static final int SUCCESS = 135;// 开始时间不能为空 |
//    public static final int SUCCESS = 136;// 结束时间不能为空 |
//    public static final int SUCCESS = 137;// 开始时间不能大于结束时间 |
//    public static final int SUCCESS = 138;// 角色id不能为空 |
//    public static final int SUCCESS = 139;// 密码长度需要8-16个字符 |
//    public static final int SUCCESS = 140;// 旧密码不能为空 |
//    public static final int SUCCESS = 141;// 新密码不能为空 |
//    public static final int SUCCESS = 142;// 原密码错误 |
//    public static final int SUCCESS = 200;// 无权访问 |
//    public static final int SUCCESS = 201;// token过期或token不合法 |
//    public static final int SUCCESS = 202;// 发送地址不能为空(验证码接口) |
//    public static final int SUCCESS = 203;// 验证码错误 |
//    public static final int SUCCESS = 204;// 验证类型不能为空 |

    // 自定义
    /**
     * 服务端返回json为空
     */
    public static final int JSON_IS_NULL = 80001;
    /**
     * json解析异常
     */
    public static final int PARSE_JSON_ERROR = 80002;
    /**
     * 刷新token失败
     */
    public static final int CAN_NOT_GET_ACCESS_TOKEN_ERROR = 80004;
    /**
     * 本地无 device token
     */
    public static final int DEVICE_TOKEN_NOT_FOUND_ERROR = 80005;
    /**
     * 本地无 device token
     */
    public static final int NETWORK_INVALID_ERROR = 80006;

}
