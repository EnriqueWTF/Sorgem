
package modelo;

public class Venta {
    private int id;
    private int anio;
    private int mes;
    private double precioUnitario;
    private int promocion; // 0 = No, 1 = Sí
    private int cantidadVendida;

    // Constructor vacío
    public Venta() {
    }

    // Constructor completo (sin ID, porque es autoincremental)
    public Venta(int anio, int mes, double precioUnitario, int promocion, int cantidadVendida) {
        this.anio = anio;
        this.mes = mes;
        this.precioUnitario = precioUnitario;
        this.promocion = promocion;
        this.cantidadVendida = cantidadVendida;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getPromocion() {
        return promocion;
    }

    public void setPromocion(int promocion) {
        this.promocion = promocion;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
}
