package co.edu.uniquindio.poo.proyectofinal.model;

public class MonederoGastos extends Monedero {
    private final double limiteRetiro;

    public MonederoGastos(String numeroCuenta, double saldoInicial, double limiteRetiro) {
        super(numeroCuenta, saldoInicial);
        if (limiteRetiro <= 0) {
            throw new IllegalArgumentException("El límite de retiro debe ser mayor a cero.");
        }
        this.limiteRetiro = limiteRetiro;
    }

    @Override
    public boolean validarLimiteRetiro(double monto) {
        return monto <= limiteRetiro;
    }

    @Override
    public String getTipoMonedero() {
        return "Gastos";
    }

    public double getLimiteRetiro() {
        return limiteRetiro;
    }
}