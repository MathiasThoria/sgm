package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioSistemaBD {
	
	public String Alta(int id, String perfil, String contraseña) {
		String retorno = "";
		ConexionBD conex = new ConexionBD();
		Connection cn = conex.conectar();
		String sql = "INSERT INTO usuariosistema VALUES (?,?,?)";
		try (PreparedStatement statement = cn.prepareStatement(sql)){
			statement.setInt(1, id);
			statement.setString(2, perfil);
			statement.setString(2, contraseña);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de alta con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		conex.cerrarConexion(cn);
		return retorno;
	}
	
	public String Baja(int id) {
		String retorno = "";
		ConexionBD conex = new ConexionBD();
		Connection cn = conex.conectar();
		String sql = "DELETE FROM usuariosistema WHERE id = ?";
		try (PreparedStatement statement = cn.prepareStatement(sql)){
			statement.setInt(1, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema dado de baja con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		conex.cerrarConexion(cn);
		return retorno;
	}
	
	public String Modificar(int id, String perfil, String contraseña) {
		String retorno = "";
		ConexionBD conex = new ConexionBD();
		Connection cn = conex.conectar();
		String sql = "UPDATE usuariosistema SET perfil = ?, contrasenia = ? WHERE id = ?";
		try (PreparedStatement statement = cn.prepareStatement(sql)){
			statement.setString(1, perfil);
			statement.setString(2, contraseña);
			statement.setInt(3, id);
			statement.executeUpdate();
			retorno = "Usuario del sistema modificado con éxito";
		} catch (SQLException e) {
			retorno = e.getMessage();
		}
		conex.cerrarConexion(cn);
		return retorno;
	}
}
