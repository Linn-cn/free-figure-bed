package edu.changda.linn.figurebed.util;

/**
 * 响应结果生成工具
 *
 * @author Linn-cn
 * @date 2020/8/31
 */
public class ResultUtils {

    /**
     * 操作成功
     */
    public static final Boolean TRUE = Boolean.TRUE;

    /**
     * 操作失败
     */
    public static final Boolean FALSE = Boolean.FALSE;

    private ResultUtils() {
    }

    /**
     * 支持全部自定义返回结果集
     *
     * @author Linn-cn
     * @date 2020/9/29
     */
    public static <T> Result<T> of(boolean flag, String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(flag)
                .setCode(code)
                .setMessage(msg)
                .setData(data);
        return result;
    }

    /**
     * 自定义data <p>
     * 需要传递data进行解析的情况下，也即操作成功，所以{@link Result#success}默认为 TRUE
     *
     * @author Linn-cn
     * @date 2020/8/31
     */
    public static <T> Result<T> ofData(CodeStatusEnum code, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(TRUE)
                .setCode(code.getErrorCode())
                .setMessage(code.getMessage())
                .setData(data);
        return result;
    }

    /**
     * 自定义提示消息 <p>
     * 需要解析 msg 的情况下，也即操作失败，所以{@link Result#success}默认为 FALSE
     *
     * @author Linn-cn
     * @date 2020/8/31
     */
    public static Result<String> ofMsg(String code, String msg) {
        Result<String> result = new Result<>();
        result.setSuccess(FALSE)
                .setCode(code)
                .setMessage(msg);
        return result;
    }

    /**
     * 自定义提示消息 <p>
     * 需要解析 msg 的情况下，也即操作失败，所以{@link Result#success}默认为 FALSE
     *
     * @author Linn-cn
     * @date 2020/8/31
     */
    public static Result<String> ofMsg(CodeStatusEnum code, String msg) {
        Result<String> result = new Result<>();
        result.setSuccess(FALSE)
                .setCode(code.getErrorCode())
                .setMessage(msg);
        return result;
    }

    /**
     * 根据传入的常量返回对应 result <p>
     * 不传 flag 的情况下，{@link Result#success}默认为 FALSE，也即操作失败
     * 传 flag 则反之
     *
     * @author Linn-cn
     * @date 2020/8/31
     */
    public static <T> Result<T> ofCode(CodeStatusEnum code, Boolean... flag) {
        Result<T> result = new Result<>();
        result.setSuccess(flag.length < 1 ? FALSE : TRUE)
                .setCode(code.getErrorCode())
                .setMessage(code.getMessage());
        return result;
    }

    /**
     * 返回ok的固定写法
     *
     * @author Linn-cn
     * @date 2020/10/4
     */
    public static <T> Result<T> ofOk(T... data) {
        if (data.length < 1){
            return ofCode(CodeStatusEnum.OK, TRUE);
        }
        if (data.length == 1){
            return ofData(CodeStatusEnum.OK, data[0]);
        }
        throw new IllegalArgumentException("data最多只能传一个");
    }

    public enum CodeStatusEnum {
        OK("00000", "成功"),
        SERVER_ERROR("B0001", "系统执行出错"),
        SYSTEM_BUSINESS("B0002","系统繁忙"),
        SYSTEM_TIMEOUT("B0100","系统超时"),
        CLIENT_ERROR("A0001", "客户端错误"),
        LOGIN_EXPIRED("A0230", "用户登录已过期"),
        BAD_PARAMETER("A0400", "请求参数有误"),
        ACCESS_EXCEPTION("A0300", "访问权限异常"),
        ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
        SIGN_FALSE("A0341","RSA签名错误"),
        ALREADY_EXIST("A0500", "已存在,请勿重复操作"),
        REMOTE_ERROR("C0001", "调用第三方服务出错");

        private final String errorCode;
        private final String message;

        CodeStatusEnum(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getMessage() {
            return message;
        }
    }
}

