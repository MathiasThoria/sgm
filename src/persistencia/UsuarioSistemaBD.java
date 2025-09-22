package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* UsuariosSistemaBD se encarga de preparar Querys y Update de la tabla usuariosistema, y ejecutarlas, usando la conexion
 * ofrecida por ServicioBD. Se opta por repetir codigo para simplificar lectura.
 * */
public class UsuarioSistemaBD {
	
	private ServicioBD cn;
		
	public UsuarioSistemaBD() {	
			cn=new ServicioBD(); 
	}
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

}

