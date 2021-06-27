package com.example.ping02.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAACySkZXo:APA91bGJNzW2JIFGe86lAQxTPnvB4WeYwK7ldOJsfSugOuCtW1Jz6ZNJvmpWf6HuVN-aatv8IX3ry5s6bC1Q6r2FgPV_OKH1ICx8f4_Ea9V8UbkuWeIw2Dw_Sy7qf0MVSGTMtWKglIZb "
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
