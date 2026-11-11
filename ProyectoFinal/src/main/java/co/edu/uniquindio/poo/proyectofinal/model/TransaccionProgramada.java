package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDateTime;

public class TransaccionProgramada implements Comparable<TransaccionProgramada> {
    private final String id;
    private final String tipo; // "Depósito" o "Transferencia"
    private final double monto;
    private final String descripcion;
    private final String numeroCuentaOrigen;
    private final String cuentaDestino; // solo se usa en transferencias
    private final Frecuencia frecuencia;
    private LocalDateTime fechaEjecucion;
    private boolean activa;

    public TransaccionProgramada(String id, String tipo, double monto, String descripcion,
                                 String numeroCuentaOrigen, String cuentaDestino,
                                 Frecuencia frecuencia, LocalDateTime fechaEjecucion) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (fechaEjecucion == null || fechaEjecucion.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de ejecución debe ser futura.");
        }
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.frecuencia = frecuencia;
        this.fechaEjecucion = fechaEjecucion;
        this.activa = true;
    }

    // Se usa después de ejecutarla: mueve la fecha a la siguiente vez
    public void reprogramar() {
        switch (frecuencia) {
            case DIARIA -> fechaEjecucion = fechaEjecucion.plusDays(1);
            case SEMANAL -> fechaEjecucion = fechaEjecucion.plusWeeks(1);
            case MENSUAL -> fechaEjecucion = fechaEjecucion.plusMonths(1);
            case UNICA -> activa = false; // no se repite, queda desactivada
        }
    }

    public boolean estaPendiente() {
        return activa && !fechaEjecucion.isAfter(LocalDateTime.now());
    }

    // Para ordenar por fecha de ejecución (la más próxima primero)
    @Override
    public int compareTo(TransaccionProgramada otra) {
        return this.fechaEjecucion.compareTo(otra.fechaEjecucion);
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getDescripcion() { return descripcion; }
    public String getNumeroCuentaOrigen() { return numeroCuentaOrigen; }
    public String getCuentaDestino() { return cuentaDestino; }
    public Frecuencia getFrecuencia() { return frecuencia; }
    public LocalDateTime getFechaEjecucion() { return fechaEjecucion; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}