package com.uth.hh.model;

import com.uth.hh.data.Alumno;
import com.uth.hh.data.AlumnosResponse;
import com.uth.hh.data.Campus;
import com.uth.hh.data.CampusResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/ingenieria_uth/matriculauth/campus")
	Call<ResponseBody> actualizarCampus(@Body Campus existente);
	
	@Headers({
	    "User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/ingenieria_uth/matriculauth/campus")
	Call<ResponseBody> eliminarCampus(@Query("id") Integer id);
	
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/ingenieria_uth/matriculauth/alumnos")
	Call<AlumnosResponse> consultarAlumnos();
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/ingenieria_uth/matriculauth/alumnos")
	Call<ResponseBody> crearAlumno(@Body Alumno nuevo);
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/ingenieria_uth/matriculauth/alumnos")
	Call<ResponseBody> actualizarAlumno(@Body Alumno existente);
	
	@Headers({
	    "User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/ingenieria_uth/matriculauth/alumnos")
	Call<ResponseBody> eliminarAlumno(@Query("id") String id);
}
