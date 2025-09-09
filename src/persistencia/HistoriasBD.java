package persistencia;

import modelo.Fecha;

public class HistoriasBD {
	private ServicioBD cn;
	
	public HistoriasBD() {
		cn=new ServicioBD();
	}
	 //capa de persistencia no sabe del modelo, los campos son de la BD
	public boolean alta( 
			Fecha fechaEnvio,
			int idUsuario,
			String correo,
			String titulosYDias) {
		
		
		
		String sql = "INSERT INTO historias (fechaEnvio,idUsuario,correo,titulosYDias) VALUES (?,?,?,?)";		
		return cn.ejecutarUpdate(sql, fechaEnvio.toSqlDate(), idUsuario, correo, titulosYDias);
		
	}
	
}
