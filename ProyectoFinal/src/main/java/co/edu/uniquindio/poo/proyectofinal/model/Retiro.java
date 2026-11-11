package co.edu.uniquindio.poo.proyectofinal.model;

public class Retiro extends Transaccion {

    public Retiro(String id, double monto, String descripcion) {
        super(id, monto, descripcion);
    }

    @Override
    public String getTipo() {
        return "Retiro";
    }
}