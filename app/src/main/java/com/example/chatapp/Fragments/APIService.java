package com.example.chatapp.Fragments;

import com.example.chatapp.Notifications.MyResponse;
import com.example.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

@Headers(
        {
                "Content-Type:application/json",
                "Authorization:Key=AAAAGYuYKQk:APA91bGQrQhzszuiI2Efz2ZFS6_S5uD-aJI7Q9b7gxINUTksWcum75QGLN8y_NV_5-TO6SNRg2zvJarZjNFQv6eQqgfbLjCHf7YUSabOn9C43EVjlIHFIAlmEv0vcUwO61Iq8LnhhNBY"
        }
)
@POST("fcm/send")
    Call<MyResponse>SendNotification(@Body Sender body);
}

