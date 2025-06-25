package modelo;

import java.time.LocalDate;

import persistencia.Datos;

public class Prestamo {
	private LocalDate fechaPrestamo;      // aff_pret_date
	private LocalDate fechaDevolucion;    // aff_pret_retour
	private int diasRetraso;              // retard

	//Datos Usuario


	private String cotaEjemplar;          // expl_cote
	private String codigoBarrasEjemplar;  // expl_cb
	private int idFichaBibliografica;     // expl_notice
	private int idBulletin;               // expl_bulletin
	private int idNotice;                 // idnot

	private String tituloObra;            // tit
	private String tipoDocumento;         // tdoc_libelle
	private boolean prestamoCorto;        // short_loan_flag
	
	
	public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, int diasRetraso,
			String cotaEjemplar, String codigoBarrasEjemplar, int idFichaBibliografica, int idBulletin, int idNotice,
			String tituloObra, String tipoDocumento, boolean prestamoCorto) {		
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.diasRetraso = diasRetraso;

		this.cotaEjemplar = cotaEjemplar;
		this.codigoBarrasEjemplar = codigoBarrasEjemplar;
		this.idFichaBibliografica = idFichaBibliografica;
		this.idBulletin = idBulletin;
		this.idNotice = idNotice;
		this.tituloObra = tituloObra;
		this.tipoDocumento = tipoDocumento;
		this.prestamoCorto = prestamoCorto;
	}


	//Instancia prestamo desde un String con la fila del xls. campos separados por coma.
	//no iria en controlador????
	public Prestamo(String fila) {
		//System.out.println(XlsParser.getValorFromFilaAtributo(fila, "fechaPrestamo"));
	    this.fechaPrestamo = Datos.parseFecha(Datos.getValorFromFilaAtributo(fila, "fechaPrestamo"));
	    this.fechaDevolucion = Datos.parseFecha(Datos.getValorFromFilaAtributo(fila, "fechaDevolucion"));
	    this.diasRetraso = Datos.parseNumero(Datos.getValorFromFilaAtributo(fila, "diasRetraso"));

	    this.cotaEjemplar = Datos.getValorFromFilaAtributo(fila, "cotaEjemplar");
	    this.codigoBarrasEjemplar = Datos.getValorFromFilaAtributo(fila, "codigoBarrasEjemplar");
	    this.idFichaBibliografica = Datos.parseNumero(Datos.getValorFromFilaAtributo(fila, "idFichaBibliografica"));
	    
	    	    
	    this.idBulletin = Datos.parseNumero(Datos.getValorFromFilaAtributo(fila, "idBulletin"));
	    this.idNotice = Datos.parseNumero(Datos.getValorFromFilaAtributo(fila, "idNotice"));

	    this.tituloObra = Datos.getValorFromFilaAtributo(fila, "tituloObra");
	    this.tipoDocumento = Datos.getValorFromFilaAtributo(fila, "tipoDocumento");
	    this.prestamoCorto = Boolean.parseBoolean(Datos.getValorFromFilaAtributo(fila, "prestamoCorto"));
	}
		
	
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(LocalDate fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public int getDiasRetraso() {
		return diasRetraso;
	}
	public void setDiasRetraso(int diasRetraso) {
		this.diasRetraso = diasRetraso;
	}
	
	public String getCotaEjemplar() {
		return cotaEjemplar;
	}
	public void setCotaEjemplar(String cotaEjemplar) {
		this.cotaEjemplar = cotaEjemplar;
	}
	public String getCodigoBarrasEjemplar() {
		return codigoBarrasEjemplar;
	}
	public void setCodigoBarrasEjemplar(String codigoBarrasEjemplar) {
		this.codigoBarrasEjemplar = codigoBarrasEjemplar;
	}
	public int getIdFichaBibliografica() {
		return idFichaBibliografica;
	}
	public void setIdFichaBibliografica(int idFichaBibliografica) {
		this.idFichaBibliografica = idFichaBibliografica;
	}
	public int getIdBulletin() {
		return idBulletin;
	}
	public void setIdBulletin(int idBulletin) {
		this.idBulletin = idBulletin;
	}
	public int getIdNotice() {
		return idNotice;
	}
	public void setIdNotice(int idNotice) {
		this.idNotice = idNotice;
	}
	public String getTituloObra() {
		return tituloObra;
	}
	public void setTituloObra(String tituloObra) {
		this.tituloObra = tituloObra;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public boolean isPrestamoCorto() {
		return prestamoCorto;
	}
	public void setPrestamoCorto(boolean prestamoCorto) {
		this.prestamoCorto = prestamoCorto;
	}
	@Override
	public String toString() {
	    return "Prestamo{" +
	            "fechaPrestamo=" + fechaPrestamo +
	            ", fechaDevolucion=" + fechaDevolucion +
	            ", diasRetraso=" + diasRetraso +
	            ", cotaEjemplar='" + cotaEjemplar + '\'' +
	            ", codigoBarrasEjemplar='" + codigoBarrasEjemplar + '\'' +
	            ", idFichaBibliografica=" + idFichaBibliografica +
	            ", idBulletin=" + idBulletin +
	            ", idNotice=" + idNotice +
	            ", tituloObra='" + tituloObra + '\'' +
	            ", tipoDocumento='" + tipoDocumento + '\'' +
	            ", prestamoCorto=" + prestamoCorto +
	            '}';
	}

}
