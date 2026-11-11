package co.edu.uniquindio.poo.proyectofinal.model;

public class Deposito extends Transaccion {

    public Deposito(String id, double monto, String descripcion) {
        super(id, monto, descripcion);
    }

    @Override
    public String getTipo() {
        return "Depósito";
    }
}