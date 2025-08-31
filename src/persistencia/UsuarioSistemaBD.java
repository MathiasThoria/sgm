package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Connection no deberia estar aca. Todo lo que tenga q ver con la conexion en si deberia estar en ConexionBD
//Estaria bueno tener un metodo en ConexionBD que sea ejecutarSQL y se le tire un String nomas
// Si queremos usar PreparedStatment, el supuesto ejecutarSQL quedaria complicado de parametros, por ahora dejarlos asi
public class UsuarioSistemaBD {
	
	private ConexionBD cn;
	
	
	public UsuarioSistemaBD() {	
			cn=new ConexionBD(); //Constructor no conecta. Llamara conectar()
	}
	
	
/*
 * PEDIR AUTORIZACION
 * Responsabilidad de conexion y ejecucion de consultas queda a cargo de ConexionBD.
 * UsuarioSistemaBD solo arma Sql	
 */
	
	public String alta(int id, String perfil, String contraseña) {
		String retorno = "";		
				
		
		String sql = "INSERT INTO usuariosistema VALUES (?,?,?)";		
		cn.ejecutarUpdate(sql, id, perfil, contraseña);
		
		return retorno;
	}
	
	public String baja(int id) {
		String retorno = "";		
				
		String sql = "DELETE FROM usuariosistema WHERE id = ?";
		cn.ejecutarUpdate(sql, id);
				
		return retorno;
	}
	
	public String modificar(int id, String perfil, String contraseña) {
		String retorno = "";
			
		
		String sql = "UPDATE usuariosistema SET perfil = ?, contrasenia = ? WHERE id = ?";
		cn.ejecutarUpdate(sql, id,perfil, contraseña);	
		return retorno;
	}
	
	public String mostrar() {
		String sql = "SELECT * FROM usuariosistema";		
		return cn.ejecutarQuery(sql);		
	}
	
	
	
	
	
	
	/*
	public String alta(int id, String perfil, String contraseña) {
		String retorno = "";
		
		cn.conectar();		
		
		String sql = "INSERT INTO usuariosistema VALUES (?,?,?)";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setInt(1, id);
			statement.setString(2, perfil);
			statement.setString(3, contraseña);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de alta con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		
		cn.cerrarConexion();
		return retorno;
	}
	
	public String baja(int id) {
		String retorno = "";
		cn.conectar();
				
		String sql = "DELETE FROM usuariosistema WHERE id = ?";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setInt(1, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de baja con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		
		
		cn.cerrarConexion();
		return retorno;
	}
	
	public String modificar(int id, String perfil, String contraseña) {
		String retorno = "";
		cn.conectar();
		
		
		String sql = "UPDATE usuariosistema SET perfil = ?, contrasenia = ? WHERE id = ?";
		try (PreparedStatement statement = cn.getConexion().prepareStatement(sql)){
			statement.setString(1, perfil);
			statement.setString(2, contraseña);
			statement.setInt(3, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema modificado con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}		
		
		cn.cerrarConexion();
		return retorno;
	}




*/

}

