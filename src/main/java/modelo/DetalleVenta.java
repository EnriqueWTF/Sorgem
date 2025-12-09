package modelo;

public class DetalleVenta {
    private int productoId;
    private String nombreProducto; // Solo para mostrar en la tabla
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleVenta(int productoId, String nombreProducto, int cantidad, double precioUnitario) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    // Getters
    public int getProductoId() { return productoId; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
}
