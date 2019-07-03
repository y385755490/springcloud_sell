package com.ymd.utils;

import com.ymd.enums.ResultEnum;
import com.ymd.vo.ResultVO;

public class ResultVOUtil {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        return resultVO;
    }
}
