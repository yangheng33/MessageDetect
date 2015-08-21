package com.detect.amar.messagedetect;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by SAM on 2015/8/21.
 */
public interface SendMessageService {

    @POST("/test/add.php")
    Observable<Response> sendMessage(@Body Message message);

    @POST("/test/add.php")
    void sendMessage(@Body Message message, Callback<Response> callback);
}
