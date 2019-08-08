package zju.edu.cn.wechatfood.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import zju.edu.cn.wechatfood.VO.ResultVO;
import zju.edu.cn.wechatfood.config.ProjectUrlConfig;
import zju.edu.cn.wechatfood.exception.ResponseBankException;
import zju.edu.cn.wechatfood.exception.SellException;
import zju.edu.cn.wechatfood.exception.SellerAuthorizeException;
import zju.edu.cn.wechatfood.utils.ResultVOUtil;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException() {
             return new ModelAndView("redirect:"
             .concat(projectUrlConfig.getWechatOpenAuthorize())
             .concat("/sell/wechat/qrAuthorize")
             .concat("?returnUrl=")
             .concat(projectUrlConfig.getSell())
             .concat("/sell/seller/login"));

    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());

    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {

    }
}
