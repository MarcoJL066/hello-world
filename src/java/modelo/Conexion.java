
package modelo;

//Nos sirve para utilizar Stored Procedures
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

//Inicio de clase Conexion
public class Conexion 
{ 
    //Atributos de la clase Conexion
    
    //URL a la cual me conecto de BD
    private static String url = "jdbc:mysql:3306//127.0.0.1/carrito";
    //Usuario
    private static String usuario = "root";
    //password
    private static String password = "1991";
    
    //Metodo para establecer conexion con BD
    public static synchronized Connection getConexion(){
        Connection cn = null;
        
        try{
          //Cargar el driver e indicamos una transaccion sql
          Class.forName("com.mysql.jdbc.Driver");
          //Obtenemos la conxion
          cn = DriverManager.getConnection(url,usuario,password);
        }catch(Exception e){
            System.out.println("EXCPETION: " + e.getMessage());
        }finally{
          return cn;  
        }
    }//Fin de CgetConexion
    
    //Metodo que cierra un procedimiento almacenado
    public static synchronized void cerrarCall( CallableStatement cl ){
        try{ cl.close(); }catch( Exception e ){ System.out.println("Error al cerrar el call" + e.getMessage()); }
    }//Fin del metodo cerrarCall
    
    //Metodo para cerrar un result set
    public static synchronized void cerrarConexion( ResultSet rs ){
        try{ rs.close(); }catch( Exception e ){ System.out.println("Error al cerrar el result" + e.getMessage()); }
    }//Fin de cerrarConexion
    
    //Metodo para cerrar un result set
    public static synchronized void cerrarConexion( Connection cn ){
        try{ cn.close(); }catch( Exception e ){ System.out.println("Error al cerrar el Connection" + e.getMessage()); }
    }//Fin de cerrarConexion
    
    //Deshacer cambios en mi DB
    public static synchronized void deshacerCambios( Connection cn){
         try{ 
             cn.rollback(); 
         }catch( Exception e ){ 
             System.out.println("Error al deshacer cambios" + e.getMessage()); 
         }
    }//Fin de deshacerCambios
    
    
}//Fin de Conexion
