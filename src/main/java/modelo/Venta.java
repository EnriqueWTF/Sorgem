package modelo;

public class Venta {
    private int id;
    private int clienteId;
    private String nombreCliente; // Para mostrar en la tabla (no se guarda directo en tabla ventas)
    private String fecha;
    private double total;

    public Venta() {}

    public Venta(int clienteId, double total) {
        this.clienteId = clienteId;
        this.total = total;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}