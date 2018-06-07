package com.example.routersever.message;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 15:03.
 * Function :
 */
public class RouterOpenResponse {

    private boolean isSuccess = false;

    private String errorMsg;

    public static RouterOpenResponse builder() {
        return new RouterOpenResponse();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public RouterOpenResponse setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public RouterOpenResponse setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
