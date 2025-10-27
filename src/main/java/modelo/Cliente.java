package modelo;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    // Constructor vacío
    public Cliente() {}

    // Constructor sin ID (para crear nuevos)
    public Cliente(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        // Esto es útil si alguna vez quieres mostrar Clientes en un JComboBox
        return nombre;
    }
}