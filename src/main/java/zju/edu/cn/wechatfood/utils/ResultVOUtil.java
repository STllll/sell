package zju.edu.cn.wechatfood.utils;

import zju.edu.cn.wechatfood.VO.ResultVO;

public class ResultVOUtil {
    public static ResultVO success(Object objcet){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(objcet);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }
    public static ResultVO error(Integer code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
