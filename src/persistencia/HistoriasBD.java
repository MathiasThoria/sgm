package persistencia;

import modelo.Fecha;

public class HistoriasBD {
	private ServicioBD cn;
	
	public HistoriasBD() {
		cn=new ServicioBD();
	}	
	// id es autonumerico en BD
	public boolean alta( 
			Fecha fechaEnvio,
			int idUsuario,
			String correo,
			String titulosYDias) {		
		String sql = "INSERT INTO historias (fechaEnvio,idUsuario,correo,titulosYDias) VALUES (?,?,?,?)";		
		return cn.ejecutarUpdate(sql, fechaEnvio.toSqlDate(), idUsuario, correo, titulosYDias);
		
	}
	public String obtener() {
		String sql = "SELECT * FROM historias";		
		return cn.ejecutarQuery(sql);
	}
	/*
	 * Devuelve mensajes posteriores a un id
	 * */
	public String obtenerMayorDeId(int id) {
		String sql = "SELECT * FROM historias WHERE id > ?";		
		return cn.ejecutarQuery(sql,id);
	}
	public boolean borrarTodo() {
		String sql = "DELETE FROM historias;";
		return cn.ejecutarUpdate(sql);
	}
	public int obtenerUltimoId() {
		String sql= "SELECT MAX(id) FROM historias;";		
		return Integer.parseInt(cn.ejecutarQuery(sql).trim()); 
	}
}
