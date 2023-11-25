package com.uth.hh.views.alumnos;

import java.util.List;

import com.uth.hh.data.Alumno;
import com.uth.hh.data.Campus;

public interface AlumnosViewModel {

	void mostrarAlumnosEnGrid(List<Alumno> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
	void llenarComboboxCampus(List<Campus> items);
}
