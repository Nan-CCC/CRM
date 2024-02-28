package com.example.enterprisecrm.common.result;

public class ResultUtil {
    /**
     * 成功且带数据
     **/
    public static <T> Result<T> success(T object) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     *成功，带数据，带msg
     */
    public static <T> Result<T> success(String msg,T object) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 成功但不带数据
     **/
    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }

    /**
     * 成功 带消息
     **/
    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 失败
     **/
    public static Result error(Object object) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMsg());
        result.setData(object);
        return result;
    }


    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    /**
     * 失败,带消息
     **/
    public static Result error(String msg,Object object) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 失败,啥也不带
     **/
    public static Result error() {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMsg());
        result.setData(null);
        return result;
    }
}
