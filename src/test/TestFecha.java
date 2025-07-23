package test;

import modelo.Fecha;

public class TestFecha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fecha f1 = new Fecha(12,12,1912);
		Fecha f2 = new Fecha(20,12,1911);
		System.out.println(f1);
		System.out.println(f1.diferencia(f2));
		Fecha s=new Fecha("12/1/1923");
		System.out.println(s);
	}

}
