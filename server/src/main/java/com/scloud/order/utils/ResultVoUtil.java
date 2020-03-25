package com.scloud.order.utils;

import com.scloud.order.vo.ResultVo;

public class ResultVoUtil {
    public static ResultVo success(Object o) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(o);
        return  resultVo;
    }
}
