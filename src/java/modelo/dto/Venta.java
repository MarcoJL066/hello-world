
package modelo.dto;

import java.sql.Timestamp;

public class Venta {
    
    //Atributos 
    private int idVenta;
    private String cliente;
    private Timestamp fecha;
    
    //Constructores
    public Venta(){
    }
    
    public Venta( int idVenta, String cliente, Timestamp fecha){
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fecha = fecha;
    }//Fin del constructor por parametros

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", cliente=" + cliente + ", fecha=" + fecha + '}';
    }
}
