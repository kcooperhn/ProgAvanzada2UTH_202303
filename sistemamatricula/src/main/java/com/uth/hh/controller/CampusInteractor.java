package com.uth.hh.controller;

import com.uth.hh.data.Campus;

public interface CampusInteractor {

	void consultarCampus();
	void crearCampus(Campus nuevo);
	void actualizarCampus(Campus existente);
	void eliminarCampus(Integer id);
}
