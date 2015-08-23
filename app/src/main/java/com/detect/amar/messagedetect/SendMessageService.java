package com.detect.amar.messagedetect;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by SAM on 2015/8/21.
 */
public interface SendMessageService {

    //@POST("/test/add.php")
    @POST("/test/add.jsp")
    Observable<Response> sendMessage(@Body Message message);

    @POST("/test/add.php")
    //@POST("/test/add.jsp")
    void sendMessage(@QueryMap Map<String,String> message, Callback<Response> callback);

    @GET("/test/ask.jsp")
    void getHtml(Callback<String> html);
}
