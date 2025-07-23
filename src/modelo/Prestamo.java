package modelo;

import java.time.LocalDate;

import persistencia.XlsParser;

public class Prestamo {
	private Fecha fechaPrestamo;      // aff_pret_date
	private Fecha fechaDevolucion;    // aff_pret_retour
	private boolean retraso;              // retard

	//Datos Usuario


	private String cotaEjemplar;          // expl_cote
	private String codigoBarrasEjemplar;  // expl_cb
	private int idFichaBibliografica;     // expl_notice
	private int idBulletin;               // expl_bulletin
	private int idNotice;                 // idnot

	private String tituloObra;            // tit
	private String tipoDocumento;         // tdoc_libelle
	private boolean prestamoCorto;        // short_loan_flag
	
	
	public Prestamo(Fecha fechaPrestamo, 
			Fecha fechaDevolucion, 
			boolean diasRetraso,
			String cotaEjemplar, 
			String codigoBarrasEjemplar, 
			int idFichaBibliografica, 
			int idBulletin, 
			int idNotice,
			String tituloObra, 
			String tipoDocumento, 
			boolean prestamoCorto) {
		
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.retraso = diasRetraso;

		this.cotaEjemplar = cotaEjemplar;
		this.codigoBarrasEjemplar = codigoBarrasEjemplar;
		this.idFichaBibliografica = idFichaBibliografica;
		this.idBulletin = idBulletin;
		this.idNotice = idNotice;
		this.tituloObra = tituloObra;
		this.tipoDocumento = tipoDocumento;
		this.prestamoCorto = prestamoCorto;
	}


		
	
	public Fecha getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Fecha fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public boolean getDiasRetraso() {
		return retraso;
	}
	public void setDiasRetraso(boolean retraso) {
		this.retraso = retraso;
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
	    return "Prestamo[" +
	            "fechaPrestamo=" + fechaPrestamo +
	            ", fechaDevolucion=" + fechaDevolucion +
	            ", diasRetraso=" + retraso +
	            ", cotaEjemplar='" + cotaEjemplar + '\'' +
	            ", codigoBarrasEjemplar='" + codigoBarrasEjemplar + '\'' +
	            ", idFichaBibliografica=" + idFichaBibliografica +
	            ", idBulletin=" + idBulletin +
	            ", idNotice=" + idNotice +
	            ", tituloObra='" + tituloObra + '\'' +
	            ", tipoDocumento='" + tipoDocumento + '\'' +
	            ", prestamoCorto=" + prestamoCorto +
	            ']';
	}

}
