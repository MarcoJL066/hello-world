
package modelo.dao;

//Imports
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Conexion;
import modelo.dto.Producto;

public class ProductoDAO 
{
    //Metodo que inserta productos a mi DB
    public static synchronized boolean insertaProducto( Producto varProducto ){
    
        //Crear las variables necesarias para insertarProducto
        Connection cn = null;
        CallableStatement cl = null;
        boolean respuesta = false;
        
        try{
            
            //Nombre del procedimiento almacenado que invoca -> SP Espera dos parametros; colocamos dos ?
            String call = "{CALL insertarProducto(?, ?)}";
            //Obtenemos la conexion
            cn = Conexion.getConexion();
            //Decimos que vamos a crear una transaccion
            cn.setAutoCommit(false);
            //Preparamos la instruccion
            cl = cn.prepareCall(call);
            //Configurar los parametros del call
            cl.setString(1, varProducto.getNombre());
            cl.setDouble(2, varProducto.getPrecio());
            //Ejecutamos la instruccion del sp. Si nos devuele 1es true 
            respuesta = cl.executeUpdate() == 1 ? true : false;
            
            //valido la respuesta
            if(respuesta == true){
                cn.commit();
            }else{
                Conexion.deshacerCambios(cn);
            }            
            //Cierro CL y CN
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);            
            
        }catch(SQLException sqle){
            sqle.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarConexion(cn);
            Conexion.cerrarCall(cl);        
        }catch(Exception e){
            e.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);           
        }        
        
        return respuesta;
    }//Fin del metodo insertaProducto
    
    //Metodo para actualizar un prodcuto
    public static synchronized boolean actualizarProducto(Producto varProducto){       
        //Variables necesarias
        Connection cn = null;
        CallableStatement cl = null;
        boolean respuesta = false;
        
        try{
            String call = "{CALL actualizarProducto(?,?,?)}";            
            cn = Conexion.getConexion();
            cn.setAutoCommit(false);
            cl = cn.prepareCall(call);
            
            cl.setInt(1, varProducto.getIdProducto());
            cl.setString(2, varProducto.getNombre());
            cl.setDouble(3 , varProducto.getPrecio());
            
            respuesta = cl.executeUpdate() == 1 ? true : false;
            if( respuesta == true ){
                cn.commit();
            }else{
                Conexion.deshacerCambios(cn);
            }            
            //Cierro conexiones
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);            
                    
        }catch(SQLException sqle){
            sqle.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
            
        }catch(Exception e){
            e.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        
        return respuesta;
    }//Fin de actualizarProducto
    
    //Listar productos
    public static synchronized ArrayList<Producto> obtenerProductos(){
    
        //Variables necesarias
        ArrayList<Producto> lista = new ArrayList<Producto>();
        Connection cn = null;
        CallableStatement cl = null;
        ResultSet rs = null;
        
        try{
            String call = "CALL obtenerProductos()";
            cn = Conexion.getConexion();
            cl = cn.prepareCall(call);
            
            //La instruccion almacena en un result set
            rs = cl.executeQuery();
            //CVonsultamos si el rs  tiene datos y  asi llenar  mi ArrayList<Producto>
            while(rs.next()){
                Producto p = new Producto();
                //Obtenemos valores de la consulta y llenamos le objeto p
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                
                //Guardamos el objeto p en el array list
                lista.add(p);                
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
            
        }catch(SQLException sqle){        
            sqle.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }catch(Exception e){
            e.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        
        return lista;
    }//Fin de obtenerProductos
    
    //Metodo para obtener un solo producto
    public static synchronized Producto obtenerProducto( int idProducto ){
        Producto p = new Producto();
        Connection cn = null;        
        CallableStatement cl = null;
        ResultSet rs = null;        
        try{
            String call = "CALL obtenerProducto(?)";
            cn = Conexion.getConexion();
            cl = cn.prepareCall(call);
            //Configurar parametros de entrada
            cl.setInt(1, idProducto);            
            //La instruccion almacena en un result set
            rs = cl.executeQuery();
            //CVonsultamos si el rs  tiene datos y  asi llenar  mi ArrayList<Producto>
            while(rs.next()){                
                //Obtenemos valores de la consulta y llenamos le objeto p
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));                            
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
            
        }catch(SQLException sqle){        
            sqle.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }catch(Exception e){
            e.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        
        return p;
    }//Fin de obtenerProducto
    
    //Metodo para eliminar un producto
    public static synchronized boolean eliminarProducto(int idProducto ){
         //Crear las variables necesarias para insertarProducto
        Connection cn = null;
        CallableStatement cl = null;
        boolean respuesta = false;        
        try{            
            //Nombre del procedimiento almacenado que invoca -> SP Espera dos parametros; colocamos dos ?
            String call = "{CALL eliminarProducto(?)}";
            //Obtenemos la conexion
            cn = Conexion.getConexion();
            //Decimos que vamos a crear una transaccion
            cn.setAutoCommit(false);
            //Preparamos la instruccion
            cl = cn.prepareCall(call);
            //Configurar los parametros del call
            cl.setInt(1, idProducto);            
            //Ejecutamos la instruccion del sp. Si nos devuele 1es true 
            respuesta = cl.executeUpdate() == 1 ? true : false;
            
            //valido la respuesta
            if(respuesta == true){
                cn.commit();
            }else{
                Conexion.deshacerCambios(cn);
            }            
            //Cierro CL y CN
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);            
            
        }catch(SQLException sqle){
            sqle.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarConexion(cn);
            Conexion.cerrarCall(cl);        
        }catch(Exception e){
            e.getMessage();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);           
        } 
        return respuesta;
    }
    
    
    
    
}
