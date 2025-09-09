package persistencia;


public class HistoriasBD {
	private ServicioBD cn;
	
	public HistoriasBD() {
		cn=new ServicioBD();
	}
	 //capa de persistencia no sabe del modelo, los campos son de la BD
	public void alta(int id, 
			String fechaEnvio,
			int idUsuario,
			String correo,
			String titulosYDias) {
		
		String retorno = "";						
		
		String sql = "INSERT INTO historias VALUES (?,?,?)";		
		cn.ejecutarUpdate(sql, id, fechaEnvio, idUsuario, correo, titulosYDias);
		
				
	}
	
}
