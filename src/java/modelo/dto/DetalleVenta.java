
package modelo.dto;

public class DetalleVenta {
    
    //Atributos
    private int idVenta;
    private int idProducto;
    private double cantidad;
    private double descuento;
    private Producto producto;
    private Venta venta;
    
    //Constructores
    public DetalleVenta(){}
    public DetalleVenta( int idVenta, int idProducto, double cantidad, double descuento, Producto producto, Venta venta ){
       this.idProducto = idProducto;
       this.idVenta = idVenta;
       this.cantidad = cantidad;
       this.descuento = descuento;
       this.producto = producto;
       this.venta = venta;
    }//Fin del constructor por parametros
    
    //Setter  y Getters

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }    
    
    //ToString
    @Override
    public String toString() {
        return "DetalleVenta{" + "idVenta=" + idVenta + ", idProducto=" + idProducto + ", cantidad=" + cantidad + ", descuento=" + descuento + ", producto=" + producto + ", venta=" + venta + '}';
    }
}
