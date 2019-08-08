package zju.edu.cn.wechatfood.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;
    private  Integer code;
    private String message;


    PayStatusEnum( Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
