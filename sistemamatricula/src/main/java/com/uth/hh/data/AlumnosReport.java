package com.uth.hh.data;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AlumnosReport implements JRDataSource {

	private List<Alumno> alumnos;
	private int counter = -1;
	private int maxCounter = 0;
	
	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
		this.maxCounter = this.alumnos.size()-1;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxCounter() {
		return maxCounter;
	}

	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}

	@Override
	public boolean next() throws JRException {
		if(counter < maxCounter) {
			counter++;
			return true;
		}
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if("cuenta".equals(jrField.getName())) {
			return alumnos.get(counter).getNumerocuenta();
		}else if("nombre".equals(jrField.getName())) {
			return alumnos.get(counter).getNombre();
		}else if("apellido".equals(jrField.getName())) {
			return alumnos.get(counter).getApellido();
		}else if("campus".equals(jrField.getName())) {
			return alumnos.get(counter).getNombrecampus();
		}
		
		return "";
	}
}
