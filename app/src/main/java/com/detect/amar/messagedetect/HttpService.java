package com.detect.amar.messagedetect;

import com.detect.amar.messagedetect.model.CheckResponse;
import com.detect.amar.messagedetect.model.StdResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by SAM on 2015/8/21.
 */
public interface HttpService {

    @POST("/test/add.jsp")
    Observable<StdResponse> sendMessage(@Body Message message);

    @GET("/test/ask.jsp")
    void getHtml(Callback<String> html);

    @POST("/receivesms.ashx")
    void sendMessage(@QueryMap Map<String,String> message, Callback<StdResponse> callback);

    @POST("/setsimcard.ashx")
    void sendSetting(@QueryMap Map<String,String> message,Callback<StdResponse> callback);

    @POST("/checkstatus.ashx")
    void getSimCardStatus(@QueryMap Map<String,String> message,Callback<StdResponse<CheckResponse>> callback);
}
