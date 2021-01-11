package com.dr.common.utils.string;

import org.apache.commons.lang3.StringUtils;

/**
 * @className： PhoneUtils
 * @description： 电话号码脱敏处理
 * @author： lxf
 * @date： 2019-09-11 15:29
 * @version：1.0
 */
public class PhoneUtils {

    private PhoneUtils() {}

    /**
     * 手机号格式校验正则
     */
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换正则
     */
    public static final String PHONE_BLUR_REPLACE_REGEX = "$1****$2";

    /**
     * 手机号格式校验
     * @param phone
     * @return
     */
    public static final boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

    /**
     * 手机号脱敏处理 未判断电话号码是否正确是因为电话号码多变 只判断长度
     * @param phone
     * @return
     */
    public static final String blurPhone(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() < 9) {
            return phone;
        }
        return phone.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }
}