package edu.changda.linn.figurebed.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回结果集
 * 设计逻辑：
 * 1.先判断 Http 状态码是否为 200，如果 200 => (2)，否则提示重试
 * 2.判断 success，success代表此次操作是否成功，如果成功就解析 data，查询业务结果，否则 => (3)
 * 3.根据 code 决定交互动作，需要提示则从 message 字段获取提示
 *
 * @author Linn-cn
 * @date 2020/8/31
 */
@ApiModel(value = "返回结果集")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作是否成功")
    private boolean success;

    @ApiModelProperty(value = "错误代码", example = "00000")
    private String code;

    @ApiModelProperty(value = "提示信息", example = "成功")
    private String message;

    @ApiModelProperty(value = "数据")
    private transient T data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean getSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
