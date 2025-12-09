package modelo;

public class PrediccionIA {
    private int mejorMes;
    private int cantidadEstimada;
    private String mensaje;
    private String fechaCalculo;

    public PrediccionIA() {}

    public PrediccionIA(int mejorMes, int cantidadEstimada, String mensaje, String fechaCalculo) {
        this.mejorMes = mejorMes;
        this.cantidadEstimada = cantidadEstimada;
        this.mensaje = mensaje;
        this.fechaCalculo = fechaCalculo;
    }

    // Getters y Setters
    public int getMejorMes() { return mejorMes; }
    public void setMejorMes(int mejorMes) { this.mejorMes = mejorMes; }

    public int getCantidadEstimada() { return cantidadEstimada; }
    public void setCantidadEstimada(int cantidadEstimada) { this.cantidadEstimada = cantidadEstimada; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getFechaCalculo() { return fechaCalculo; }
    public void setFechaCalculo(String fechaCalculo) { this.fechaCalculo = fechaCalculo; }
}
