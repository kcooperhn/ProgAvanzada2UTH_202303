package com.uth.hh.model;

import com.uth.hh.data.Campus;
import com.uth.hh.data.CampusResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DatabaseRepository {
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/ingenieria_uth/matriculauth/campus")
	Call<CampusResponse> consultarCampus();
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/ingenieria_uth/matriculauth/campus")
	Call<ResponseBody> crearCampus(@Body Campus nuevo);
}
