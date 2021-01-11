package com.dr.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rui.duan
 * @version 1.0
 * @className WarehousingReceiptStatusEnum
 * @description 管理员枚举
 * @date 2019-06-10 11:39
 */
@Getter
@AllArgsConstructor
public enum AdminTypeEnum {
    //待通知
    NONE_ADMIN(0,"非管理员"),

    IS_ADMIN(1,"管理员"),;

    private Integer code;
    private String value;
    

    /**
     * 根据code获取值
     * @param code
     * @return
     */
    public static String getValueByCode(Integer code) {
        AdminTypeEnum[] values = AdminTypeEnum.values();
        for (AdminTypeEnum type : values) {
            if (type.code.equals(code)) {
                return type.value;
            }
        }
        return null;
    }

    /**
     * 根据value获取code
     * @param value
     * @return
     */
    public static Integer getCodeByValue(String value) {
        AdminTypeEnum[] values = AdminTypeEnum.values();
        for (AdminTypeEnum type : values) {
            if (type.value.equalsIgnoreCase(value)) {
                return type.code;
            }
        }
        return null;
    }
}
