package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDateTime;

public abstract class Transaccion {
    private final String id;
    private final LocalDateTime fecha;
    private final double monto;
    private final String descripcion;
    private int puntosGenerados;
    private boolean esSospechosa=false;
    private EstadoTransaccion estado;

    public Transaccion(String id, double monto, String descripcion,int puntosGenerados,EstadoTransaccion estado) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.descripcion = descripcion;
        this.puntosGenerados=puntosGenerados;
        this.estado=estado;
    }
    public abstract String getTipo();  // manooooo este metodo esta hecho
    // para no tener que usar tanto intace of

    public String getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntosGenerados() {
        return puntosGenerados;
    }

    public boolean isEsSospechosa() {
        return esSospechosa;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    // esta mierda es para los puntos que genre cada tipo
    public abstract int calcularPuntosGenerados();
    }