package com.uth.hh.controller;

import java.io.IOException;

import com.uth.hh.data.Campus;
import com.uth.hh.data.CampusResponse;
import com.uth.hh.model.DatabaseRepositoryImpl;
import com.uth.hh.views.campus.CampusViewModel;

public class CampusInteractorImpl implements CampusInteractor {

	private DatabaseRepositoryImpl modelo;
	private CampusViewModel vista;
	
	public CampusInteractorImpl(CampusViewModel view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}

	@Override
	public void consultarCampus() {
		try {
			CampusResponse respuesta = this.modelo.consultarCampus();
			if(respuesta == null || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay campus que mostrar");
			}else {
				this.vista.mostrarCampusEnGrid(respuesta.getItems());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearCampus(Campus nuevo) {
		try {
			boolean creado = this.modelo.crearCampus(nuevo);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Campus creado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al crear el campus");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarCampus(Campus existente) {
		try {
			boolean creado = this.modelo.actualizarCampus(existente);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Campus modificado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al modificar el campus");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void eliminarCampus(Integer id) {
		try {
			boolean eliminado = this.modelo.eliminarCampus(id);
			if(eliminado == true) {
				this.vista.mostrarMensajeExito("Campus borrado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al borrar el campus");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
