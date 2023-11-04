package com.uth.hh.model;

import com.uth.hh.data.CampusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DatabaseRepository {
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/ingenieria_uth/matriculauth/campus")
	Call<CampusResponse> consultarCampus();
}