package com.dr.common.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author rui.duan
 * @version 1.0
 * @className WarehousingReceiptStatusEnum
 * @description 菜单枚举
 * @date 2019-06-10 11:39
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {

    //待通知
    MENU(0,"菜单"),

    BUTTON(1,"按钮"),;

    private Integer code;
    private String value;
    

    /**
     * 根据code获取值
     * @param code
     * @return
     */
    public static String getValueByCode(Integer code) {
        PermissionTypeEnum[] values = PermissionTypeEnum.values();
        for (PermissionTypeEnum type : values) {
            if (type.code.equals(code)) {
                return type.value;
            }
        }
        return null;
    }

    public static List<Integer> getCodes() {
        List<Integer> codes = Lists.newArrayList();
        PermissionTypeEnum[] values = PermissionTypeEnum.values();
        for (PermissionTypeEnum type : values) {
            codes.add(type.getCode());
        }
        return codes;
    }

    /**
     * 根据value获取code
     * @param value
     * @return
     */
    public static Integer getCodeByValue(String value) {
        PermissionTypeEnum[] values = PermissionTypeEnum.values();
        for (PermissionTypeEnum type : values) {
            if (type.value.equalsIgnoreCase(value)) {
                return type.code;
            }
        }
        return null;
    }
}
