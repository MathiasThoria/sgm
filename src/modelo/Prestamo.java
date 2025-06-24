package modelo;

import java.time.LocalDate;

import persistencia.XlsParser;


public class Prestamo {
	private LocalDate fechaPrestamo;      // aff_pret_date
	private LocalDate fechaDevolucion;    // aff_pret_retour
	private int diasRetraso;              // retard

	private int idUsuario;                // id_empr
	private String apellidoUsuario;       // empr_nom
	private String nombreUsuario;         // empr_prenom
	private String emailUsuario;          // empr_mail
	private String codigoBarrasUsuario;  // empr_cb

	private String cotaEjemplar;          // expl_cote
	private String codigoBarrasEjemplar;  // expl_cb
	private int idFichaBibliografica;     // expl_notice
	private int idBulletin;               // expl_bulletin
	private int idNotice;                 // idnot

	private String tituloObra;            // tit
	private String tipoDocumento;         // tdoc_libelle
	private boolean prestamoCorto;        // short_loan_flag
	
	
	public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, int diasRetraso, int idUsuario,
			String apellidoUsuario, String nombreUsuario, String emailUsuario, String codigoBarrasUsuario,
			String cotaEjemplar, String codigoBarrasEjemplar, int idFichaBibliografica, int idBulletin, int idNotice,
			String tituloObra, String tipoDocumento, boolean prestamoCorto) {		
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.diasRetraso = diasRetraso;
		this.idUsuario = idUsuario;
		this.apellidoUsuario = apellidoUsuario;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		this.codigoBarrasUsuario = codigoBarrasUsuario;
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
	    this.fechaPrestamo = XlsParser.parseFecha(XlsParser.getValorFromFilaAtributo(fila, "fechaPrestamo"));
	    this.fechaDevolucion = XlsParser.parseFecha(XlsParser.getValorFromFilaAtributo(fila, "fechaDevolucion"));
	    this.diasRetraso = Integer.parseInt(XlsParser.getValorFromFilaAtributo(fila, "diasRetraso"));

	    this.idUsuario = Integer.parseInt(XlsParser.getValorFromFilaAtributo(fila, "idUsuario"));
	    this.apellidoUsuario = XlsParser.getValorFromFilaAtributo(fila, "apellidoUsuario");
	    this.nombreUsuario = XlsParser.getValorFromFilaAtributo(fila, "nombreUsuario");
	    this.emailUsuario = XlsParser.getValorFromFilaAtributo(fila, "emailUsuario");
	    this.codigoBarrasUsuario = XlsParser.getValorFromFilaAtributo(fila, "codigoBarrasUsuario");

	    this.cotaEjemplar = XlsParser.getValorFromFilaAtributo(fila, "cotaEjemplar");
	    this.codigoBarrasEjemplar = XlsParser.getValorFromFilaAtributo(fila, "codigoBarrasEjemplar");
	    this.idFichaBibliografica = Integer.parseInt(XlsParser.getValorFromFilaAtributo(fila, "idFichaBibliografica"));
	    this.idBulletin = Integer.parseInt(XlsParser.getValorFromFilaAtributo(fila, "idBulletin"));
	    this.idNotice = Integer.parseInt(XlsParser.getValorFromFilaAtributo(fila, "idNotice"));

	    this.tituloObra = XlsParser.getValorFromFilaAtributo(fila, "tituloObra");
	    this.tipoDocumento = XlsParser.getValorFromFilaAtributo(fila, "tipoDocumento");
	    this.prestamoCorto = Boolean.parseBoolean(XlsParser.getValorFromFilaAtributo(fila, "prestamoCorto"));
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
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getCodigoBarrasUsuario() {
		return codigoBarrasUsuario;
	}
	public void setCodigoBarrasUsuario(String codigoBarrasUsuario) {
		this.codigoBarrasUsuario = codigoBarrasUsuario;
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
	            ", idUsuario=" + idUsuario +
	            ", apellidoUsuario='" + apellidoUsuario + '\'' +
	            ", nombreUsuario='" + nombreUsuario + '\'' +
	            ", emailUsuario='" + emailUsuario + '\'' +
	            ", codigoBarrasUsuario='" + codigoBarrasUsuario + '\'' +
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
