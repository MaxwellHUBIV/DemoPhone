package com.example.demophone.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST ("auth/login")
    Call<LoginResponse>userLogin(@Body LoginRequest loginRequest);
}
