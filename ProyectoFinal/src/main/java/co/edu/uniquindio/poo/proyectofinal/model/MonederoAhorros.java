package co.edu.uniquindio.poo.proyectofinal.model;

public class MonederoAhorros extends Monedero {
    private final double limiteRetiro;

    public MonederoAhorros(String numeroCuenta, double saldoInicial, double limiteRetiro) {
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
        return "Ahorros";
    }

    public double getLimiteRetiro() {
        return limiteRetiro;
    }
}