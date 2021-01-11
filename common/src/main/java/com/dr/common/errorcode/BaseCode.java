package com.dr.common.errorcode;

/**
 * @Author : Max.
 * @Created : 2018-08-23
 * @E-mail : goday.max@gmail.com
 * @Version : 1.0
 * @Desription : 系统基础错误码  10000 ~ 19999
 */
public enum BaseCode implements Code {
    SUCCESS(10000, "业务处理成功", "业务处理成功"),
    SYSTEM_FAILD(10001, "网络走神了,请稍后重试", "网络走神了,请稍后重试"),
    TIMED_OUT(10002, "业务处理超时", "系统处理超时，请重试"),
    PARAM_ERROR(10003, "参数错误", "请检查参数是否正确"),
    EXCEL_TEMPLE_ERROR(10004, "模板不能为空", "请检查模版路径是否正确"),
    EXCEL_NOT_EMPTY_ERROR(10005, "excel文件不能为空", "请检查excel文件"),
    EXCEL_DEAL_ERROR(10006, "excel文件处理失败", "请联系管理员"),
    LOGIN_FAILED(10007,"账号或密码错误","登录失败，账号或密码错误"),
    LOGIN_TOKEN_GEN_FAILED(10008, "生成token加密失败", "登录失败，请重试"),
    LOGIN_TOKEN_GEN_PARAM_INVALID(10009, "生成token参数缺失", "登录失败，请重试"),
    LOGIN_ELSEWHERE(10010, "token已经失效", "您的账号已在其他地方登录，请重新登录"),
    LOGIN_INVALID(10011, "token校验失败", "登录失效，请重新登录"),
    OBJECT_EMPTY(10012,"查找的数据不存在","查找的数据不存在"),
    OPEN_TOKEN_CREATE_ERROR(10013,"openToken创建失败","openToken创建失败"),
    OPEN_TOKEN_CREATE_GET_KEY_ERROR(10014,"密钥获取失败","openToken创建失败"),
    OPEN_TOKEN_DECRYPT_ERROE(10015,"openToken解密失败","openToken解密失败"),
    OPEN_TOKEN_VALID_ERROR(10016,"openToken校验失败","openToken校验失败"),
    OPEN_TOKEN_VALID_TIME_ERROR(10017,"openToken已过期","openToken已过期"),
    OPEN_SIGN_VALID_ERROR(10018,"签名校验失败","签名校验失败"),
    USER_PASSWORD_VERIFY_ERROR(10019,"密码强度校验不通过,密密码规则(数字+英文,长度6-16位)","密码强度校验不通过,密密码规则(数字+英文,长度6-16位)"),
    OPRATE_OBJECT_EMPTY(10020,"需要操作的数据为空","需要操作的数据为空"),
    EXPORT_OBJECT_EMPTY(10021,"需要导出的数据为空","需要导出的数据为空"),
    NOT_REPEAT_COMMIT(10022,"请勿重复提交","请勿重复提交"),
    USER_AUTH_DISABLE(10023,"用户已停用","用户已停用无法操作"),
    CANCEL_WMS_FAIL(10024,"取消通知失败","取消通知失败"),
    EXCEL_TYPE_ERROR(10025, "导入的格式非excel文件", "导入失败，请下载模板查看格式"),
    EXCEL_OVERFLOW(10026, "excel超出最大导出数量", "excel超出最大导出数量"),
    LOGIN_UNKNOW_ACCOUNT(10027, "未知账户", "未知账户"),
    LOGIN_PASSWORD_INCORRECT(10028, "密码不正确", "密码不正确"),
    LOGIN_ACCOUNT_LOCK(10029, "账户已锁定", "账户已锁定"),
    LOGIN_INCORRECT_OVER_TIMES(10030, "用户名或密码错误次数过多", "用户名或密码错误次数过多"),
    LOGIN_INCORRECT_NAME_OR_PASSWORD(10031, "用户名或密码错误", "用户名或密码错误"),
    LOGIN_VERIFYCODE_INCORRECT(10032, "验证码错误", "验证码错误"),
    LOGIN_MANY_LOCATION(10033, "您已在别处登录，请您修改密码或重新登录", "您已在别处登录，请您修改密码或重新登录"),

    ;

    private final int code;
    private final String info;
    private final String fixTips;

    BaseCode(int code, String info, String fixTips) {
        this.code = code;
        this.info = info;
        this.fixTips = fixTips;
    }

    /**
     * 错误码
     * eg: 200xx gateway
     * 300xx user
     * 400xx order
     * 500xx core
     * 600xx operator
     * 700xx admin
     * <p>
     * universal:
     * 10000 ~ 19999
     * 10000 success
     * 10001 system error
     * 10002 timed out
     * 10003 params error
     * 10004 rpc timeout
     * 10005 rpc invoke error
     *
     * @return
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * 错误码说明(内部日志，统计，查看使用)
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * 错误码描述，对外输出
     *
     * @return
     */
    @Override
    public String getFixTips() {
        return this.fixTips;
    }
}
