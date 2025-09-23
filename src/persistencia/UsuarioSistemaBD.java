package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* UsuariosSistemaBD se encarga de preparar Querys y Update de la tabla usuariosistema, y ejecutarlas, usando la conexion
 * ofrecida por ServicioBD. Se opta por repetir codigo para simplificar lectura.
 * */
public class UsuarioSistemaBD {
	
	private ServicioBD cn;
		
	public UsuarioSistemaBD() {	
			cn=new ServicioBD(); 
	}
	
	public String obtener() {
        String sql = "SELECT * FROM usuariosistema";
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
	
	public boolean verificarPass(int id, String contraseña) {
		boolean res=false;
		String sql = "SELECT * FROM usuariosistema WHERE id = ? AND contrasenia = ?";
		cn.conectar();
		try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
		    ps.setInt(1, id);      
		    ps.setString(2, contraseña);
		    try (ResultSet rs = ps.executeQuery()) {
		        if (rs.next()) 
		            res=true;		        
		    }catch(SQLException e) {
				System.out.println(e);
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		cn.cerrarConexion();
		return res;
	}
	
	public String obtenerPerfil(int id) {
		String res="";
		String sql = "SELECT perfil FROM usuariosistema WHERE id = ?";
		cn.conectar();
		try (PreparedStatement ps = cn.getConexion().prepareStatement(sql)) {
		    ps.setInt(1, id);
		    try (ResultSet rs = ps.executeQuery()) {
		    	if (rs.next())
		    		res=rs.getString("perfil");		        
		    }catch(SQLException e) {
				System.out.println(e);
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		cn.cerrarConexion();
		return res;
	}
}

