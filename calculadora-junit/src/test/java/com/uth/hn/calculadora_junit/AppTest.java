package com.uth.hn.calculadora_junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
   
	@Before
	public void metodoTipoSetup() {
		System.out.println("Ejecutando metodo tipo setup antes de cada test...");
		
	}
	
	@BeforeClass
	public static void metodoTipoSetupClass() {
		System.out.println("Ejecutando metodo tipo setup class antes de todos los test...");
		App aplicacion = new App();
		aplicacion.main(null);
	}
	
	@Test
	public void pruebaSuma1() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertTrue(App.sumar(1,1) == 2);
		//assertEquals(App.sumar(1,1) , 2);
	}
	
	@Test
	public void pruebaSuma2() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(0,0) , 0, 0.01);
	}
	
	
	@Test
	public void pruebaSuma3() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(10,0) , 10, 0.0001);
	}
	
	@Test
	public void pruebaSuma4() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(0.5,0.5) , 1.0, 0.0001);
	}
	
	
	@Test
	public void pruebaSuma5() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(-1,-1) , -2, 0.001);
	}
	
	@Test
	public void pruebaSuma6() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(100.5,0.5) , 101.0, 0.00000001);
	}
	
	@Test
	public void pruebaSuma7() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.sumar(3,3) , 6, 0.001);
	}
	
	@Test
	public void pruebaResta1() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.restar(3,3) , 0, 0.001);
	}
	
	@Test
	public void pruebaResta2() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.restar(0,0) , 0, 0.001);
	}
	
	
	@Test
	public void pruebaResta3() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.restar(2,1) , 1, 0.001);
	}
	
	@Test
	public void pruebaResta4() {
		//ESTA ES UNA PRUEBA UNITARIA, ADENTRO DE ELLA VAN LOS ASSERT (AFIRMACIONES)
		assertEquals(App.restar(4,6) , -2, 0.001);
	}
	
	
	@After
	public void metodoTearDown() {
		System.out.println("Ejecutando metodo tipo tear down después de cada test...");
	}
	
	@AfterClass
	public static void metodoTearDownClass() {
		System.out.println("Ejecutando metodo tipo tear down después de todos los test...");
	}
}
