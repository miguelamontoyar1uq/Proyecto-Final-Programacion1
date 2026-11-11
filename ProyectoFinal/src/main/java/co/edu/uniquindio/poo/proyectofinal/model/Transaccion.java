package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDateTime;

public abstract class Transaccion {
    private final String id;
    private final LocalDateTime fecha;
    private final double monto;
    private final String descripcion;

    public Transaccion(String id, double monto, String descripcion) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.descripcion = descripcion;
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
    }