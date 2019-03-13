package com.ztd.mvpstandardpro_as.base;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.ztd.mvpstandardpro_as.exception.ApiException;
import com.ztd.mvpstandardpro_as.utils.LogUtils;
import com.ztd.mvpstandardpro_as.utils.ToastUtil;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.text.ParseException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class BaseObserver<T> implements Observer<BaseHttpResult<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseHttpResult<T> value) {
        onFinish();
        if(value.getStatus()==4){
            T data = value.getData();
            onSuccess(data);
        }else{
            onFail(value.getStatus(),value.getMessage());
            if(value.getMessage()!=null&&!value.getMessage().equals("当前已是最新版本！")) {
                ToastUtil.setToast(value.getMessage());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            String message="";
            if (e instanceof HttpException) {     //   HTTP错误
                message="网络错误";
            } else if (e instanceof ConnectException
                    || e instanceof java.net.UnknownHostException) {   //   连接错误
                message="连接错误";
            } else if (e instanceof InterruptedIOException) {   //  连接超时
                message="连接超时";
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {   //  解析错误
               message="解析错误";
            } else {
               message="未知错误";
            }
            ApiException apiException = new ApiException(message);
            onError(apiException);
            ToastUtil.setToast(message);
            LogUtils.e(TAG, "error:" + e.toString());
        }catch (Exception ex){
            ToastUtil.setToast("运行异常:"+e.getClass().getSimpleName());

        }
        onFinish();
    }

    @Override
    public void onComplete() {
        LogUtils.d(TAG, "onComplete");
    }

    protected abstract void onError(ApiException e);
    protected abstract void onSuccess(T t);
    protected abstract void onFail(int status,String t);
    protected abstract void onFinish();
}

