package com.uth.hh.controller;

import com.uth.hh.data.Alumno;

public interface AlumnosInteractor {

	void consultarAlumnos();
	void crearAlumno(Alumno nuevo);
	void actualizarAlumno(Alumno existente);
	void eliminarAlumno(String numerocuenta);
}
