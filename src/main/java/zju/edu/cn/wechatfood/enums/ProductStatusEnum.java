package zju.edu.cn.wechatfood.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements  CodeEnum {
    UP(0,"在架"),
    DOWN(1,"下架")
   ;
    private String message;
   private  Integer code;
   ProductStatusEnum(Integer code, String message){
       this.code = code;
       this.message = message;
   }
}
