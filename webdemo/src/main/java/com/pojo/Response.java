package com.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @author AlbertXe
 * @date 2019-11-22 15:48
 */
@Data
@ToString
public class Response {
    private String code;
    private String msg;

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
