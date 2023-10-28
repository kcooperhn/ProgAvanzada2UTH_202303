package com.uth.hn.calculadora_junit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Calculadora con maven + Junit" );
        
    }

	public static double sumar(double numero1, double numero2) {
		double resultado = numero1+numero2;
		return resultado;
	}

	public static double restar(double numero1, double numero2) {
		return numero1-numero2;
	}
}
