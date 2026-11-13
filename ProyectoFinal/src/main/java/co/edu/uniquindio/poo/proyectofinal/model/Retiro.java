package co.edu.uniquindio.poo.proyectofinal.model;

public class Retiro extends Transaccion {

    private double cargoRetiro;

    public Retiro(String id, double monto, String descripcion,int puntosGenerados,EstadoTransaccion estado,double cargoRetiro) {
        super(id, monto, descripcion,puntosGenerados,estado);
        this.cargoRetiro=cargoRetiro;
    }

    @Override
    public String getTipo() {
        return "Retiro";
    }
    @Override
    public int calcularPuntosGenerados() {
        return (int) (getMonto() / 100) * 2;
    }
}