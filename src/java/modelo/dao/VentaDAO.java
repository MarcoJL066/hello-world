package modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import modelo.Conexion;
import modelo.dto.DetalleVenta;
import modelo.dto.Producto;
import modelo.dto.Venta;

public class VentaDAO {
    //Metodo para insertarVenta

    public static synchronized boolean insertarVenta(Venta varVenta, ArrayList<DetalleVenta> detalle) {
        //Paso 1-> Variables
        Connection cn = null;
        CallableStatement cl = null;
        boolean respuesta = false;

        //Paso 2->
        try {
            String call = "{CALL insertarVenta(?,?)}";
            //Obtenemos la conexion
            cn = Conexion.getConexion();
            cn.setAutoCommit(false);
            //preparamos la instruccion
            cl = cn.prepareCall(call);
            //Configurar parametros del cl
            //el id es autogenerable , el sp es de tipo out
            cl.registerOutParameter(1, Types.INTEGER);
            //Configuramos los demas parametros de forma ç habitual
            cl.setString(2, varVenta.getCliente());
            
            respuesta = cl.executeUpdate() == 1 ? true : false;
            
            //Codigo que se genero debido a la insercion  "idVenta"
            varVenta.setIdVenta( cl.getInt(1) );
            
            //validamos la respuesta
            if( respuesta == true){
                for( DetalleVenta det : detalle ){
                    //Establecer aal detalle el codigo que genero  prodeucto de la venta
                    det.setIdVenta(varVenta.getIdVenta());
                    
                    //Insertamos el detale y pasamos  conexion como parametro
                    respuesta = DetalleVentaDAO.insertarDetalleVenta(det, cn);
                    if(respuesta == false){ break; }
                }
                if( respuesta == true ){
                    cn.commit();
                }else{
                    Conexion.deshacerCambios(cn);
                }
            }else{
                 Conexion.deshacerCambios(cn);
            }
            
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
            

        } catch (SQLException sql) {
            sql.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (Exception e) {
            e.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }

        return respuesta;

    }//Fin de metodo insertarVenta
    
    //Metodo para taer toda las ventas del sistema
    public static synchronized ArrayList<DetalleVenta> obtenerVentas(){
        
        //Arreglo que contiene los registros
        ArrayList<DetalleVenta> lista = new ArrayList<DetalleVenta>();
        Connection cn = null;
        CallableStatement cl = null;
        ResultSet rs = null;
        
        try{
            
            String call = "{CALL listarVentas()}";
            cn = Conexion.getConexion();
            cl = cn.prepareCall(call);
            rs = cl.executeQuery();
            
            while( rs.next() ){
                Venta ven = new Venta();
                Producto pro = new Producto();
                DetalleVenta det = new DetalleVenta();
                
                //Obtener informacion  para venta
                ven.setIdVenta( rs.getInt("idventa") );
                ven.setCliente(rs.getString("cliente"));
                ven.setFecha(rs.getTimestamp("fecha"));
                
                pro.setIdProducto( rs.getInt("idproducto") );
                pro.setNombre( rs.getString("nombre") );
                pro.setPrecio( rs.getDouble("precio") );
                
                det.setCantidad( rs.getDouble("cantidad") );
                det.setDescuento(rs.getDouble("descuento"));
                det.setVenta( ven );
                det.setProducto(pro);
                lista.add(det);
                
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(rs);
            Conexion.cerrarConexion(cn);
        
        }catch(SQLException sqle){
            sqle.printStackTrace();            
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }catch(Exception e){
            e.printStackTrace();
            
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        
        return lista;
    }

}
