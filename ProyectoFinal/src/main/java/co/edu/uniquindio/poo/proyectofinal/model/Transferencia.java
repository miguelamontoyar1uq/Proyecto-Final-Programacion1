package co.edu.uniquindio.poo.proyectofinal.model;

public class Transferencia extends Transaccion {
    private final String cuentaDestino;

    public Transferencia(String id, double monto, String descripcion, String cuentaDestino) {
        super(id, monto, descripcion);
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public String getTipo() {
        return "Transferencia";
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }
    @Override
    public int calcularPuntosGenerados() {
        return (int) (getMonto() / 100) * 3;
    }
}