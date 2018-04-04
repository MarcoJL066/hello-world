package modelo.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Conexion;
import modelo.dto.DetalleVenta;

public class DetalleVentaDAO
{
	//Metodo utilizado para insertar un registro en la tabla intermedia
	public static synchronized boolean  insertarDetalleVenta( DetalleVenta varDetalle , Connection cn ){
		//Paso 1 -> Declaracion de variables
		CallableStatement cl = null;
		boolean respuesta = false;
		//Paso 2 -> Procesamiento de transaccion
		try{
			//Definimos el nombre del procedimiento almacenado que vamos a llamar
			String call = "{ CALL insertarDetalleVenta(?,?,?,?) }";
			//Preparamos la instruccion del sp
			cl = cn.prepareCall(call);
			//Configuramos los parametros
			cl.setInt(1 , varDetalle.getIdVenta());
			cl.setInt(2 , varDetalle.getIdProducto());
			cl.setDouble(3 , varDetalle.getCantidad());
			cl.setDouble(4 , varDetalle.getDescuento());
			//Evaluamos la respuesta
			respuesta = cl.executeUpdate() == 1 ? true : false;
			//Cerramos el callable
			Conexion.cerrarCall(cl);

		}catch(SQLException sqle){
			sqle.printStackTrace();
			Conexion.cerrarCall(cl);
		}catch(Exception e){
			e.printStackTrace();
			Conexion.cerrarCall(cl);
		}//Fin del cuerpo del try catch

		return respuesta;

	}//Fin del metodo detalle venta

}//Fin de clase DetalleVentaDAO