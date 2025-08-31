package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
yo estoy re afin a hacerla Static o Singleton. Peeero....
cambie para que la clase se instancie
cambie para que la conexion sea un atruibuto (y se ddevuelva para PreparedStatement)
estaria bueno un metodo para ejecutar SQL. Entiendo que usar Statement es inseguro, 
habria que ver como hacerlo con PreparedStatement
*/

public class ConexionBD {
	private final String Controlador = "com.mysql.cj.jdbc.Driver";
	private final String Url = "jdbc:mysql://localhost:3306/sgm";
	private final String User = "root"; 
	private final String Pass = "root"; 
	private Connection conexion;
	
	/*
	static {
		try {
			Class.forName(Controlador);
		} catch(ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
		}
	} 
	//Este bloque carga el controlador una única vez(Static).
	*/
	
	public ConexionBD() {
		conexion=null;
	}
	
	public Connection conectar() {		
		try {
			conexion = DriverManager.getConnection(Url,User,Pass);
			System.out.println("Conexion establecida.");
		} catch(SQLException e) {
			System.out.println("Error al establecer la conexion.");
		}
		return conexion;
	}

	public void cerrarConexion() {
		try {
			if(conexion!= null) {
				conexion.close();
			}
		} catch (Exception e2) {
			System.out.println("Error no se pudo cerrar la conexión.");
		}
	}
	public Connection getConexion() {
		return conexion;
	}
	
	
	//pedir autorizacion
	
	
	
	
	public String ejecutarUpdate(String sql, Object... params) {
	    String retorno = "";
	    conectar();
	    
	    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
	        for (int i = 0; i < params.length; i++) {
	            statement.setObject(i + 1, params[i]);
	        }
	        statement.executeUpdate();
	        retorno = "Operación realizada con éxito";
	    } catch (SQLException e) {
	        retorno = e.getMessage();
	    }
	    
	    cerrarConexion();
	    return retorno;
	}
	
	
	/*
	 * Si devuelve resultSet hay que cerrar conexion despues de consumirla
	 * Si devuleve String hay que parsear afuera
	 * */
	public String ejecutarQuery(String sql, Object... params) {
	    String resultado = "";
		conectar();
	    
	    try {
	        PreparedStatement statement = getConexion().prepareStatement(sql);
	        for (int i = 0; i < params.length; i++) {
	            statement.setObject(i + 1, params[i]);
	        }
	        
	        
	        ResultSet rs = statement.executeQuery();
	        int columnas = rs.getMetaData().getColumnCount();
	        
	        while (rs.next()) {
	            for (int i = 1; i <= columnas; i++) {
	            	System.out.println(rs.getString(i));
	                resultado+=rs.getString(i);
	                if (i < columnas) {
	                    resultado+=", ";
	                }
	            }
	            resultado+="\n";
	        }
	        rs.close();    
	        
	        
	        return resultado;
	        //return statement.executeQuery(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}


}
