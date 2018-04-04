package modelo.dto;
public class Producto 
{
    //Atributos
    private int idProducto;
    private String nombre;
    private double precio;    
    
    //Constructores
    public Producto(){}
    public Producto(int idProducto, String nombre, double precio){
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
    }//Fin de constructor por parametros
    
    //Metodos set y get
    public int getIdProducto(){ return this.idProducto; }
    public String getNombre(){ return this.nombre; }
    public double getPrecio(){ return this.precio; }
    
    //Metodos set
    public void setIdProducto( int idProducto ){ this.idProducto = idProducto; }
    public void setNombre( String nombre ){ this.nombre = nombre; }
    public void setPrecio( double precio){ this.precio = precio; } 
    
    //Metodo toString
    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
}
