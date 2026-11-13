package co.edu.uniquindio.poo.proyectofinal.model;

public class Deposito extends Transaccion {

    public Deposito(String id, double monto, String descripcion,int puntosGenerados,EstadoTransaccion estado) {
        super(id, monto, descripcion,puntosGenerados,estado);
    }

    @Override
    public String getTipo() {
        return "Depósito";
    }
    @Override
    public int calcularPuntosGenerados() {
        return (int) (getMonto() / 100) * 1;
    }
}