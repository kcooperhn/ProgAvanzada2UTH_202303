package com.uth.hh.model;

import java.io.IOException;

import com.uth.hh.data.CampusResponse;

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
	
}