package zju.edu.cn.wechatfood.exception;

import lombok.Data;
import org.junit.runner.RunWith;
import zju.edu.cn.wechatfood.enums.ResultEnum;

@Data
public class SellException extends RuntimeException {
 private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();

    }
    public SellException(Integer code, String message){
        super();
        this.code = code;
    }
}
