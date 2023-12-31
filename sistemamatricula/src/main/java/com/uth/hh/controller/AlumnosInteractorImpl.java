package com.uth.hh.controller;

import java.io.IOException;

import com.uth.hh.data.Alumno;
import com.uth.hh.data.AlumnosResponse;
import com.uth.hh.data.CampusResponse;
import com.uth.hh.model.DatabaseRepositoryImpl;
import com.uth.hh.views.alumnos.AlumnosViewModel;

public class AlumnosInteractorImpl implements AlumnosInteractor {

	private DatabaseRepositoryImpl modelo;
	private AlumnosViewModel vista;
	
	public AlumnosInteractorImpl(AlumnosViewModel view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	
	@Override
	public void consultarAlumnos() {
		try {
			AlumnosResponse respuesta = this.modelo.consultarAlumnos();
			if(respuesta == null || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay campus que mostrar");
			}else {
				this.vista.mostrarAlumnosEnGrid(respuesta.getItems());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crearAlumno(Alumno nuevo) {
		try {
			boolean creado = this.modelo.crearAlumno(nuevo);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Alumno creado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al crear el alumno");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarAlumno(Alumno existente) {
		try {
			boolean creado = this.modelo.actualizarAlumno(existente);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Alumno modificado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al crear el modificado");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarAlumno(String numerocuenta) {
		try {
			boolean creado = this.modelo.eliminarAlumno(numerocuenta);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Alumno borrado exitosamente!");
			}else {
				this.vista.mostrarMensajeError("Hubo un problema al borrar el alumno");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void consultarCampus() {
		try {
			CampusResponse respuesta = this.modelo.consultarCampus();
			if(respuesta != null && respuesta.getItems() != null) {
				this.vista.llenarComboboxCampus(respuesta.getItems());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
