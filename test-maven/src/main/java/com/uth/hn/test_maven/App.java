package com.uth.hn.test_maven;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        System.out.println( "Calculadora de Aumentos" );
        System.out.println( "Programación Avanzada II" );
        int porcentajeAumento = pedirNumeroTeclado("Ingrese el porcentaje de aumento del salario a calcular:");
        double salarioActual = pedirDecimalTeclado("Ingrese el salario actual:");
        
        double valorAumento = porcentajeAumento / 100.0d;
        valorAumento = valorAumento * salarioActual;
        double nuevoSalario = valorAumento + salarioActual;
        
        System.out.println("El salario actual es de L"+salarioActual);
        System.out.println("Con un aumento del "+porcentajeAumento+"%");
        System.out.println("El nuevo salario será de L"+nuevoSalario);
        System.out.println("El aumento fue de L"+valorAumento);
    }
    
    private static int pedirNumeroTeclado(String mensaje) {
    	Scanner teclado = new Scanner(System.in);
    	System.out.println(mensaje);
    	return teclado.nextInt();
    }
    
    private static double pedirDecimalTeclado(String mensaje) {
    	Scanner teclado = new Scanner(System.in);
    	System.out.println(mensaje);
    	return teclado.nextDouble();
    }
}
