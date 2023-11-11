package com.uth.hh.views.campus;

import java.util.List;

import com.uth.hh.data.Campus;

public interface CampusViewModel {

	void mostrarCampusEnGrid(List<Campus> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);

}
