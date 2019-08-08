package zju.edu.cn.wechatfood.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"订单结束"),
    CANCEL(2,"已取消"),
    ;
    private Integer code;
    private String message;

   OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
