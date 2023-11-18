package com.uth.hh.model;

import java.io.IOException;

import com.uth.hh.data.Alumno;
import com.uth.hh.data.AlumnosResponse;
import com.uth.hh.data.Campus;
import com.uth.hh.data.CampusResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DatabaseRepositoryImpl {
	
	private static DatabaseRepositoryImpl INSTANCE;
	private DatabaseClient client;
	
	private DatabaseRepositoryImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}

	public static DatabaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DatabaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DatabaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	public CampusResponse consultarCampus() throws IOException {
		Call<CampusResponse> call = client.getDatabase().consultarCampus();
		Response<CampusResponse> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearCampus(Campus nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabase().crearCampus(nuevo);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
	
	public boolean actualizarCampus(Campus existente) throws IOException {
		Call<ResponseBody> call = client.getDatabase().actualizarCampus(existente);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
	
	public boolean eliminarCampus(Integer id) throws IOException {
		Call<ResponseBody> call = client.getDatabase().eliminarCampus(id);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
	
	
	public AlumnosResponse consultarAlumnos() throws IOException {
		Call<AlumnosResponse> call = client.getDatabase().consultarAlumnos();
		Response<AlumnosResponse> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearAlumno(Alumno nuevo) throws IOException {
		Call<ResponseBody> call = client.getDatabase().crearAlumno(nuevo);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
	
	public boolean actualizarAlumno(Alumno existente) throws IOException {
		Call<ResponseBody> call = client.getDatabase().actualizarAlumno(existente);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
	
	public boolean eliminarAlumno(String numerocuenta) throws IOException {
		Call<ResponseBody> call = client.getDatabase().eliminarAlumno(numerocuenta);
		Response<ResponseBody> response = call.execute();//AQUI SE LLAMA A LA BASE DE DATOS
		return response.isSuccessful();
	}
}
