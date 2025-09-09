package modelo;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Fecha {
	private int dia;
	private int mes;
	private int anio;
		
	public Fecha(int dia, int mes, int anio) {		
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}	
	public Fecha() {
		
	}
	public Fecha(Fecha f) {
		this(f.dia,f.mes,f.anio);
	}
	public Fecha(String f) {
		String[] arrf = new String[3]; 
		arrf=f.split("/");
		dia=Integer.parseInt(arrf[0]);
		mes=Integer.parseInt(arrf[1]);
		anio=Integer.parseInt(arrf[2]);
		
	}
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String toString() {
		return dia + "/" + mes + "/" + anio;
	}
    public Date toSqlDate() {
        LocalDate local = LocalDate.of(anio, mes, dia);
        return Date.valueOf(local);
    }
	public long diferencia(Fecha otra) {
		LocalDate f1 = LocalDate.of(this.anio, this.mes, this.dia);		
		LocalDate f2 = LocalDate.of(otra.anio, otra.mes, otra.dia);		
		return ChronoUnit.DAYS.between(f2,f1);
	}
	
	


}
