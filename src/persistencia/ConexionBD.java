package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
	private static final String Controlador = "com.mysql.cj.jdbc.Driver";
	private static final String Url = "jdbc:mysql://localhost:3306/sgm";
	private static final String User = "root"; 
	private static final String Pass = "root"; 
	
	static {
		try {
			Class.forName(Controlador);
		} catch(ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
		}
	} 
	//Este bloque carga el controlador una única vez(Static).
	
	public Connection conectar() {
		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(Url,User,Pass);
			System.out.println("Conexion establecida.");
		} catch(SQLException e) {
			System.out.println("Error al establecer la conexion.");
		}
		return conexion;
	}

	public void cerrarConexion(Connection cn) {
		try {
			if(cn!= null) {
				cn.close();
			}
		} catch (Exception e2) {
			System.out.println("Error no se pudo cerrar la conexión.");
		}
	}
}
