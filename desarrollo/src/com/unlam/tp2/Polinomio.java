package com.unlam.tp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Polinomio {
	private int grado;
	private double[] coeficientes;

	// La posicion 0 del arreglo de coeficientes contiene el coeficiente de grado n
	// y la posicion n contiene al termino independiente.
	public Polinomio(double[] coeficientes) {
		this.coeficientes = coeficientes;
		this.grado = coeficientes.length - 1;
	}

	public Polinomio(String path) {
		try {
			File file = new File(path);
			Scanner scan = new Scanner(file);
			grado = scan.nextInt();
			coeficientes = new double[grado + 1];
			for (int i = 0; i < coeficientes.length; i++) {
				coeficientes[i] = scan.nextDouble();
			}

			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo levantar el archivo");
		}
	}

	/*
	 * Complejidad computacional: O(n^2)
	 */
	public double evaluarMSucesivas(double x) {
		double res = 0;
		for (int i = 0; i <= this.grado; i++) {
			double aux = 1;
			for (int j = 0; j <= this.grado - i - 1; j++) {
				aux *= x;
			}

			res += this.coeficientes[i] * aux;
		}
		return res;
	}

	/*
	 * Complejidad computacional: O(n^2)
	 */
	public double evaluarRecursiva(double x) {
		double res = 0;
		for (int i = 0; i <= this.grado; i++)
			res += this.coeficientes[i] * potenciaRecursiva(x, this.grado - i);

		return res;
	}

	// Complejidad O(n!)
	private double potenciaRecursiva(double x, int exp) {
		if (exp == 0)
			return 1;

		return x * potenciaRecursiva(x, exp - 1);
	}

	/*
	 * Complejidad computacional: O(n*log(n))
	 */
	public double evaluarRecursivaPar(double x) {
		double res = 0;
		for (int i = 0; i <= this.grado; i++)
			res += this.coeficientes[i] * potenciaRecursivaPar(x, this.grado - i);

		return res;
	}

	private double potenciaRecursivaPar(double x, int exp) {
		if (exp == 0)
			return 1;

		if (exp % 2 == 0)
			return potenciaRecursivaPar(x * x, exp / 2);
		else
			return x * potenciaRecursivaPar(x, exp - 1);
	}

	/*
	 * Complejidad computacional: O(n*log(n))
	 */
	public double evaluarProgDinamica(double x) {
		double res = 0;
		double[] arr = new double[this.coeficientes.length];

		// Inicializo el array en 1, para que pueda hacer el producto
		arr[0] = 1;
		for (int i = 1; i <= this.grado; i++) {
			arr[i] = x * arr[i - 1];
		}

		// Ahora ya tengo las potencias, paso a calcular mi resultado
		for (int i = 0; i <= this.grado; i++) {
			res += arr[i] * this.coeficientes[this.grado - i];
		}

		return res;
	}

	/*
	 * Complejidad computacional: O(n)
	 */
	public double evaluarMejorada(double x) {
		double res = this.coeficientes[this.grado];
		double potencia = x;
		for (int i = this.grado - 1; i >= 0; i--) {
			res += potencia * this.coeficientes[i];
			potencia *= x;
		}
		return res;
	}

	/*
	 * Complejidad computacional: O(n*log(n))
	 */
	public double evaluarPow(double x) {
		double res = 0;
		for (int i = 0; i <= this.grado; i++) {
			res += this.coeficientes[i] * Math.pow(x, this.grado - i);
		}

		return res;
	}

	// Complejidad computacional O(n)
	public double evaluarHorner(double x) {
		double resultado = 0;

		for (int i = 0; i <= this.grado; i++) {
			resultado = resultado * x + this.coeficientes[i];
		}
		return resultado;
	}

	public int getGrado() {
		return grado;
	}

	public double[] getCoeficientes() {
		return coeficientes;
	}

}
