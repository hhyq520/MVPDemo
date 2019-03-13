package com.ztd.mvpstandardpro_as.base;

/**
 * Created by GaoSheng on 2016/10/21.
 * 抽取的一个基类的bean,直接在泛型中传data就行
 */

public class BaseHttpResult<T> {
    private int stauts;
    private String msg;
    private T data;

    public int getStatus() {
        return stauts;
    }

    public void setStatus(int status) {
        this.stauts = status;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "status=" + stauts +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
