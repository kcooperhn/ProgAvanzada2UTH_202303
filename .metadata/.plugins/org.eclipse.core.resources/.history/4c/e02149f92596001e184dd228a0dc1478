package com.uth.hn.service;

import java.io.File;

import org.springframework.util.ResourceUtils;

public class ReportGenerator {

	//INDICA DONDE ESTÁ GUARDADO EL REPORTE EN PDF FINAL
	private String reportPath;
	
	//SIRVE PARA GENERAR Y LLENAR EL REPORTE EN PDF
	public boolean generarReportePDF() {
		
	}
	
	//GENERA UNA UBICACIÓN EN MI MAQUINA DONDE VA A GUARDAR EL ARCHIVO GENERAADO
	private String generarUbicacionReporte() {
		String path = null;
		try {
			//SE CREA COMO TEMPORAL PARA QUE EL SO LO BORRE CUANDO CONSIDERE NECESARIO
			//NO NECESITO ESPECIFICAR LA CARPETA EXACTA, EL SO DECIDE
			path = File.createTempFile("temp", ".pdf").getAbsolutePath();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	//OBTIENE LA UBICACIÓN DEL ARCHIVO .JASPERR
	private String obtenerUbicacionReporte(String filename) {
		String path = null;
		try {
			path = ResourceUtils.getFile("classpath:"+filename).getAbsolutePath();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	//NOS PERMITE CONSULTAR LA UBICACIÓN DEL REPORTE Y VERLO EN PANTALLA
	public String getReportPath() {
		return reportPath;
	}
	
	
}
