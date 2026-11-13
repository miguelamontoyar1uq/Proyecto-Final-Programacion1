package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.*;

public class Cliente {
    private final String id;
    private String nombre;
    private String documento;
    private String telefono;
    private String correo;
    private Map<String, Monedero> listaMonederos;
    private List<Notificacion> notificaciones;
    private int puntosAcumulados;
    private RangoCliente rango;
    private List<CatalogoBeneficio> beneficiosActivos;
    private List<RegistroPuntos> historialPuntos;

    public Cliente(String id, String nombre, String documento,String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.documento=documento;
        this.telefono = telefono;
        this.correo = correo;
        this.listaMonederos = new HashMap<>();
        this.notificaciones = new ArrayList<>();
        this.beneficiosActivos = new ArrayList<>();
        this.historialPuntos = new ArrayList<>();
        this.puntosAcumulados = 0;
        this.rango = RangoCliente.BRONCE; // MANOOOO pues supongo que todos los
        // cleintes emoezian en bronce no?
    }

    public void agregarMonedero(String nombreMonedero, Monedero monedero) {
        this.listaMonederos.put(nombreMonedero, monedero);
    }

    private void actualizarRango() {

        if (puntosAcumulados <= 500) {
            this.rango = RangoCliente.BRONCE;
        } else if (puntosAcumulados <= 1000) {
            this.rango = RangoCliente.PLATA;
        } else if (puntosAcumulados <= 5000) {
            this.rango = RangoCliente.ORO;
        } else {
            this.rango = RangoCliente.PLATINO;
        }

    }

    public String getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Map<String, Monedero> getListaMonederos() {
        return Collections.unmodifiableMap(listaMonederos);
    }

    public List<TransaccionProgramada> getListaTransaccionesProgramadas() {
        return Collections.unmodifiableList(listaTransaccionesProgramadas);
    }

    public List<Notificacion> getNotificaciones() {
        return Collections.unmodifiableList(notificaciones);
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public RangoCliente getRango() {
        return rango;
    }

    public List<CatalogoBeneficio> getBeneficiosActivos() {
        return Collections.unmodifiableList(beneficiosActivos);
    }

    public List<RegistroPuntos> getHistorialPuntos() {
        return Collections.unmodifiableList(historialPuntos);
    }

    public void sumarPuntos(int cantidad, RegistroPuntos registro) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de puntos debe ser mayor a cero.");
        }
        this.puntosAcumulados += cantidad;
        this.historialPuntos.add(registro);
        actualizarRango();
    }

    public void restarPuntos(int cantidad, RegistroPuntos registro) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de puntos debe ser mayor a cero.");
        }
        if (cantidad > puntosAcumulados) {
            throw new IllegalArgumentException("El cliente no tiene puntos suficientes.");
        }
        this.puntosAcumulados -= cantidad;
        this.historialPuntos.add(registro);
        actualizarRango();
    }

    public void acumularPuntos(Transaccion transaccion) {
        int puntos = transaccion.calcularPuntosGenerados();
        if (puntos > 0) {
            sumarPuntos(puntos, new RegistroPuntos(puntos, "GANADOS", "Puntos por " + transaccion.getTipo()));
        }
    }

    public void recibirNotificacion(Notificacion notificacion) {
        if (notificacion == null) {
            throw new IllegalArgumentException("La notificación no puede ser nula.");
        }
        this.notificaciones.add(notificacion);
    }

    public void programarTransaccion(TransaccionProgramada transaccion) {
        if (transaccion == null) {
            throw new IllegalArgumentException("La transacción programada no puede ser nula.");
        }
        this.listaTransaccionesProgramadas.add(transaccion);
    }

    public void activarBeneficio(CatalogoBeneficio beneficio) {
        if (beneficio == null) {
            throw new IllegalArgumentException("El beneficio no puede ser nulo.");
        }
        this.beneficiosActivos.add(beneficio);
    }

    // eso es parte del algoritmo de deteccionn de movimientos que pueden ser fraudolentos
    public List<Transaccion> obtenerTransaccionesAtipicas() {
        // esta parte es para unir los movimientos de cada monedero
        List<Transaccion> historialTransacciones = listaMonederos.values().stream()
                .flatMap(monedero -> monedero.getHistorialTransacciones().stream())
                .toList();

        if (historialTransacciones.size() < 2) {
            return List.of();
        }
        //promedio
        double promedio =
                historialTransacciones.stream().mapToDouble(Transaccion::getMonto).average().getAsDouble();
        // desviacion
        double sumaCuadrados = 0;
        for (Transaccion t : historialTransacciones) {
            sumaCuadrados += Math.pow(t.getMonto() - promedio, 2);
        }
        double desviacion = Math.sqrt(sumaCuadrados / historialTransacciones.size());
        //umbral
        double umbral = promedio + (2 * desviacion);
        // sospechosas
        return historialTransacciones.stream().filter(t -> t.getMonto() > umbral).toList();
    }

}