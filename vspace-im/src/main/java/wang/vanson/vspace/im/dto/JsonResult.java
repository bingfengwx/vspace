package wang.vanson.vspace.im.dto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 1798247727907048983L;
    private String code;
    private String msg;
    private Object data;

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * 返回成功
     * @param data 数据
     * @return JsonResult
     */
    public static JsonResult success(Object data) {
        JsonResult jr = new JsonResult(ReturnCode.SUCCESS.code, ReturnCode.SUCCESS.desc);
        jr.setData(data);
        return jr;
    }

    /**
     * 返回成功
     * @return JsonResult
     */
    public static JsonResult success() {
        return new JsonResult(ReturnCode.SUCCESS.code, ReturnCode.SUCCESS.desc);
    }

    /**
     * 系统默认异常
     * @return JsonResult
     */
    public static JsonResult error() {
        return new JsonResult(ReturnCode.ERROR.code, ReturnCode.ERROR.desc);
    }

    /**
     * 自定义异常返回
     * @param code 异常代码
     * @param msg 错误提示
     * @return JsonResult
     */
    public static JsonResult error(String code, String msg) {
        return new JsonResult(code, msg);
    }

    /**
     * 返回系统预定义异常
     * @param returnCode 错误码
     * @return JsonResult
     */
    public static JsonResult error(ReturnCode returnCode) {
        return new JsonResult(returnCode.getCode(), returnCode.getDesc());
    }

    /**
     * 返回错误提示
     * @param msg 错误提示
     * @return JsonResult
     */
    public static JsonResult error(String msg) {
        return new JsonResult(ReturnCode.ERROR_TIPS.getCode(), msg);
    }

    public enum ReturnCode {
        NOT_LOGIN("401","未登录"),
        LOGIN_ERROR("444", "登录异常"),
        SUCCESS ("200","成功"),
        ERROR ("500","未知异常，请稍后再试！"),
        NO_ACCESSING ("403","禁止访问"),
        NOT_FOUND ("404","页面未找到"),
        ERROR_TIPS ("001", "错误提示");
        @Getter
        private String code;
        @Getter
        private String desc;

        ReturnCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
