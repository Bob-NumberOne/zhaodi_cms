package com.zhaodi.bean;

/**
 * @version 1.0
 * @Describe 统一的 JSON 结构中属性包括数据、状态码、提示信息
 * @Author WangBo
 * @Date 2020/4/5 16:11
 */
public class JsonResult<T>{
    private T data;
    private String code;
    private String msg;

    /**
     * 若没有数据返回，默认状态码为 1，提示信息为“操作成功！”
     */
    public JsonResult() {
        this.code = "1";
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为 1，默认提示信息为“操作成功！”
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = "1";
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为1，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = "1";
        this.msg = msg;
    }

    /**
     * 有数据返回，状态码为自定义，人为指定提示信息
     * @param data
     * @param code
     * @param msg
     */
    public JsonResult(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
