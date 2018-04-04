
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.ProductoDAO;
import modelo.dao.VentaDAO;
import modelo.dto.DetalleVenta;
import modelo.dto.Producto;
import modelo.dto.Venta;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        //El objetivo de crear un controller es hacer un switcheo de peticiones HTTP al metodo adecuado
        String accion  = request.getParameter("accion");
        
        //Switcheamos el valor que contiene "accion"
        if( accion.equals("RegistrarProducto") ){
            this.registrarProducto(request , response);
        }else if( accion.equals("ModificarProducto") ){
            this.modificarProducto(request , response);
        }else if( accion.equals("AnadirCarrito") ){
            this.anadirCarrito(request, response);
        }else if( accion.equals("RegistrarVenta") ){
            this.registrarVenta(request, response);
        }
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private void registrarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
        //Paso 1-> Recuperar la informacion de los formularios
        Producto p = new Producto();
        p.setNombre( request.getParameter("txtNombre") );
        p.setPrecio( Double.parseDouble( request.getParameter("txtPrecio") ) );
        //Paso 2-> Insertar
        boolean respuesta = ProductoDAO.insertaProducto(p);
        if(respuesta == true){
            //Se incerto correctamente; redireccionamos a mensaje
            response.sendRedirect("mensaje.jsp?men=Se registro correctamente el producto");
        }else{
            response.sendRedirect("mensaje.jsp?men=No se registro correctamente el producto");
        }
    }//Fin del metodo 
   
    private void anadirCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //Obtenemos session (Recordar manejo de sesiones y validacion de sesiones)
        HttpSession sesion = request.getSession();
        ArrayList<DetalleVenta> carrito;
        
        //Validar sesion 
        if( sesion.getAttribute("carrito") == null ){
           //-> Si no existe la sesion creamos el carrito de compras
            carrito = new ArrayList<DetalleVenta>();
        }else{
            //Si ya existe, asigno a mi variable carrito lo que ya se encuentre en sesion
            carrito = (ArrayList<DetalleVenta>) sesion.getAttribute("carrito");
        }
        
        //Obtenemos el producto que va añadirse al carrito de ocmpras
        Producto p = ProductoDAO.obtenerProducto( Integer.parseInt( request.getParameter("txtIdProducto") ) );
        //Creamos el detalle para el carrito de compras
        DetalleVenta d = new DetalleVenta();
        
        //Obtenemos los valores del form
        d.setIdProducto( Integer.parseInt( request.getParameter("txtIdProducto") ) );
        d.setProducto(p);
        d.setCantidad( Double.parseDouble( request.getParameter("txtCantidad") ) );
        
        //Aplicamos un descuento  siguiendo las condiciones:
        //Si el valor de un subtotal es mayo a 50 aplicamos un descuento del 5%
        double subTotal = p.getPrecio() * d.getCantidad();
        //Evaluamos el subtotal
        if( subTotal > 50){
            d.setDescuento( subTotal * 0.05 );
            //d.setCantidad( subTotal * (5D / 100D) );
        }else{
            d.setDescuento(0);
        }
        
        //Variable auxiliar que nos indica si tenemos agregado el producto al carrito de compras
        int indice = -1;
        //Recorremso nuestro carrito de compras
        for( int i=0; i < carrito.size();  i++ ){
            
            DetalleVenta det = carrito.get(i);
            //Evaluo
            if( det.getIdProducto() == p.getIdProducto() ) {
                //Si el producto ya esta en el carrito, obtenemos el indice dentro del arreglo para actualizar las cantidades en el carrito
                indice = i;
                break;
            }//Fin de la validacion
            
        }//Fin del for que recorre a carrito
        
        //Validar el registro
        if( indice == -1 ){
            //voy a registrar y agregar
            carrito.add(d);
        }else{
            //Si es diferente de -1   significa que el producto se encuentra en el carrito y vamos a actualizar
            carrito.set(indice, d);
        }
        
        //Actualizar la sesion en el carrito de compras
        sesion.setAttribute("carrito", carrito);
        //Redireccionamos al formulario de finalizar venta
        response.sendRedirect("registrarVenta.jsp");       
    }

    private void modificarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Paso #1 Obtener parametros
        Producto p = new Producto();
        p.setIdProducto( Integer.parseInt( request.getParameter("txtIdProducto") ) );
        p.setNombre( request.getParameter("txtNombre") );
        p.setPrecio( Double.parseDouble(request.getParameter("txtPrecio")) );
        //Paso# 2 Invocar el metodo actualizar producto del DAO
        boolean respuesta = ProductoDAO.actualizarProducto(p);
        //Paso #3 Evaluar la respuesta obtenida
        if( respuesta == true ){
            response.sendRedirect("mensaje.jsp?men=Se actualizo correctamente");
        }else{
            response.sendRedirect("mensaje.jsp?men=No se actualizo correctamente");
        }
        
    }
    
    //
    private void registrarVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        HttpSession sesion = request.getSession();
        Venta v =  new Venta();
        v.setCliente( request.getParameter("txtCliente") );
        ArrayList<DetalleVenta> detalle = (ArrayList<DetalleVenta>) sesion.getAttribute("carrito");
        
        //Ejecuto la peticion
        boolean respuesta = VentaDAO.insertarVenta(v , detalle);
        //Evaluacion
        if( respuesta ==  true ){
            response.sendRedirect("mensaje.jsp?men=Se registro la venta de forma correcta");
        }else{
            response.sendRedirect("mensaje.jsp?men=La venta no se pudo efectuar de manera correcta");
        }
    }

}
