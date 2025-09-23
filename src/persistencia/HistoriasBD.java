package persistencia;

import modelo.Fecha;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/* HistoriasBD se encarga de preparar Querys y Update de la tabla historias, y ejecutarlas, usando la conexion
 * ofrecida por ServicioBD. Se opta por repetir codigo para simplificar lectura.
 * */
public class HistoriasBD {
	private ServicioBD cn;
	
	public HistoriasBD() {
		cn=new ServicioBD();
	}	
	// id es autonumerico en BD
	public boolean alta(Fecha fechaEnvio, int idUsuario, String correo, String titulosYDias) {
        String sql = "INSERT INTO historias (fechaEnvio,idUsuario,correo,titulosYDias) VALUES (?,?,?,?)";		
        boolean ok = false;
        
        cn.conectar();
        
        try {
            PreparedStatement statement = cn.getConexion().prepareStatement(sql);
            statement.setDate(1, fechaEnvio.toSqlDate());
            statement.setInt(2, idUsuario);
            statement.setString(3, correo);
            statement.setString(4, titulosYDias);
            
            ok = statement.executeUpdate() > 0;
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en alta: " + e.getMessage());
        }
        
        cn.cerrarConexion();
        return ok;
    }
	
	public String obtener() {
        String sql = "SELECT * FROM historias";
        String resultado = "";
        
        cn.conectar();
        
        try {
            Statement statement = cn.getConexion().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int columnas = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado += rs.getString(i);
                    
                    if (i < columnas) {
                        resultado += "|";
                    }
                }
                resultado += "\n";
            }
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en obtener: " + e.getMessage());
            resultado = null;
        }
        
        cn.cerrarConexion();
        return resultado;
    }
	
	/*
	 * Devuelve mensajes posteriores a un id
	 * */
	public String obtenerMayorDeId(int id) {
        String sql = "SELECT * FROM historias WHERE id > ?";
        String resultado = "";
        
        cn.conectar();
        
        try {
            PreparedStatement statement = cn.getConexion().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            int columnas = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    resultado += rs.getString(i);
                    if (i < columnas) {
                        resultado += ", ";
                    }
                }
                resultado += "\n";
            }
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en obtenerMayorDeId: " + e.getMessage());
            resultado = null;
        }
        
        cn.cerrarConexion();
        return resultado;
    }
    
    public boolean borrarTodo() {
        String sql = "DELETE FROM historias";
        boolean ok = false;
        
        cn.conectar();
        
        try {
            Statement statement = cn.getConexion().createStatement();
            ok = statement.executeUpdate(sql) >= 0; // DELETE puede afectar 0 filas y ser exitoso
            statement.executeUpdate("ALTER TABLE historias AUTO_INCREMENT = 1"); // para resetear autoincrement
            statement.close();
            
        } catch (SQLException e) {
            System.out.println("Error en borrarTodo: " + e.getMessage());
        }
        
        cn.cerrarConexion();
        return ok;
    }


	public int obtenerUltimoId() {
		String sql= "SELECT MAX(id) FROM historias;";
		boolean ok = false;        
        cn.conectar();
        int resultado=0;
        
        try {
            Statement statement = cn.getConexion().createStatement();            
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                resultado = rs.getInt(1); 
            }
            
            rs.close();
            statement.close();
            cn.cerrarConexion();
            
        } catch (SQLException e) {
            System.out.println("Error en obtenerMayorDeId: " + e.getMessage());            
        }
        
        cn.cerrarConexion();        
		return resultado; 
	}
	
	public boolean baja(int id) {
	    String sql = "DELETE FROM historias WHERE id = ?";
	    boolean ok = false;

	    cn.conectar();

	    try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
	        ps.setInt(1, id);
	        int filas = ps.executeUpdate();
	        ok = (filas > 0);
	    } catch (SQLException e) {
	        System.out.println("Error en baja: " + e.getMessage());
	    }

	    cn.cerrarConexion();
	    return ok;
	}
	
	public boolean existeId(int id) {
	    String sql = "SELECT 1 FROM historias WHERE id = ? LIMIT 1";
	    boolean existe = false;

	    cn.conectar();

	    try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                existe = true;
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en existeId: " + e.getMessage());
	    }

	    cn.cerrarConexion();
	    return existe;
	}


}
