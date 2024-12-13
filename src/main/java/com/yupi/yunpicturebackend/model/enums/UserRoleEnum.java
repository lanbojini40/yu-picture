package com.yupi.yunpicturebackend.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;
import lombok.Value;

@Getter
public enum UserRoleEnum {
    USER("用户","user"),
    ADMIN("管理员","admin");
    private final String text;
    private final String value;
    UserRoleEnum(String text,String value){
        this.text=text;
        this.value=value;
    }
    public static UserRoleEnum getEnumByValue(String value){
        if(ObjUtil.isEmpty(value)){
            return null;
        }
          for(UserRoleEnum userRoleEnum : UserRoleEnum.values()){
              if(userRoleEnum.value.equals(value)){
                  return userRoleEnum;
              }
          }
          return null;
    }
}