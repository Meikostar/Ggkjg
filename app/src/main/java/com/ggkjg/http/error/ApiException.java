package com.ggkjg.http.error;


import android.text.TextUtils;

import com.ggkjg.http.request.BaseRequestModel;
import retrofit2.HttpException;

/**
 * Created by uqduiba on 17/11/03.
 */
public class ApiException extends RuntimeException {

    public static final int WRONG_0 = 0; //成功
    public static final int WRONG_200 = 200; //成功
    public static final int WRONG_500 = 500; //程序出现错误

    private static int code;
    private  String errorMsg;
    private BaseRequestModel httpRequest;
    private static ApiException instance = null;

    public ApiException(BaseRequestModel httpRequest, int resultCode, String rawErrorMsg) {
        this.httpRequest = httpRequest;
        this.code = resultCode;
        this.errorMsg = rawErrorMsg;
    }

    public ApiException() {
    }

    public static ApiException getInstance() {
        if (instance == null) {
            instance = new ApiException();
        }
        return instance;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        if (getCode() == WRONG_0 || getCode() == WRONG_200 ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 开发者视角的api错误消息内容
     *
     * @return
     */
    public  String getErrorMsg() {
      return errorMsg;
//        return getApiExceptionMessage(code);
    }

    public BaseRequestModel getHttpRequest() {
        return httpRequest;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private  String getApiExceptionMessage(int code) {
        String message;
        switch (code) {
            case WRONG_500:
                message = "程序出现错误";
                break;
            default:
                message = errorMsg;
                break;
        }
        return message;
    }

   public static String getShowToast(Throwable throwable){
        String message = "";
        if(throwable != null && throwable instanceof  ApiException){
            message = ((ApiException)throwable).getErrorMsg();
        }
        if(TextUtils.isEmpty(message)){
            message = "";
        }
        return  message;
   }
}

