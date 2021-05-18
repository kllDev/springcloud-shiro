package com.kll.springcloud.model;

import lombok.Data;

/**
 * @author liangliang.ke
 * @version 1.0
 * @program springcloud-shiro
 * @description
 * @date 2021-05-14 21:16
 */
@Data
public class ResultModel {
    private Integer code;

    private String msg;

    private String orderId;

    public static ResultModel success(String msg,String id) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setMsg(msg);
        resultModel.setOrderId(id);
        return resultModel;
    }

    public static ResultModel success(String msg) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(200);
        resultModel.setMsg(msg);
        return resultModel;
    }

    public static ResultModel fail(String msg) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(300);
        resultModel.setMsg(msg);
        return resultModel;
    }
}