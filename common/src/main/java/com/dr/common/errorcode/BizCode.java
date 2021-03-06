package com.dr.common.errorcode;

/**
 *
 */
public enum BizCode implements Code {

    /**************************计费模块错误码******************************/
    GIT_OPERATED_FAIlED(20000, "git拉取代码失败", "请检查git参数配置"),
    CHARGE_PKG_SKU_INVALID(20001, "包裹商品数据不存在", "请检查包裹商品信息是否存在"),
    CREATE_JOB_FAIL(20002, "创建job失败", "请联系管理员"),



    ;

    private final int code;
    private final String info;
    private final String fixTips;

    BizCode(int code, String info, String fixTips) {
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
