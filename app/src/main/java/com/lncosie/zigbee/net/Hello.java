package com.lncosie.zigbee.net;

import retrofit.Call;
import retrofit.http.GET;

public interface Hello {
    @GET("/webservices/qqOnlineWebService.asmx/qqCheckOnline?qqCode=165587344")
    Call<String> sayHello();
}
