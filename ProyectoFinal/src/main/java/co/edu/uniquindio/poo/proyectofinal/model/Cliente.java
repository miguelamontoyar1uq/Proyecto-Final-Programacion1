package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.*;

public class Cliente {
    private final String id;
    private String nombre;
    private String telefono;
    private String correo;
    private Map<String, Monedero> listaMonederos;
    private List<TransaccionProgramada> listaTransaccionesProgramadas;
    private List<Notificacion> notificaciones;
    private int puntosAcumulados;
    private RangoCliente rango;
    private List<Beneficio> beneficiosActivos;
    private List<RegistroPuntos> historialPuntos;

    public Cliente(String id, String nombre, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;

        this.listaMonederos = new HashMap<>();
        this.listaTransaccionesProgramadas = new ArrayList<>();
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

    public List<Beneficio> getBeneficiosActivos() {
        return Collections.unmodifiableList(beneficiosActivos);
    }

    public List<RegistroPuntos> getHistorialPuntos() {
        return Collections.unmodifiableList(historialPuntos);
    }
    // mano faltan estos metodos
    // sumarPuntos(int cantidad, RegistroPuntos registro)

    //restarPuntos(int cantidad, RegistroPuntos registro)

    //ecibirNotificacion(Notificacion notificacion)

    //programarTransaccion(TransaccionProgramada transaccion)

    // activarBeneficio(Beneficio beneficio)


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
        double promedio=
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
        historialTransacciones.stream().filter(t -> t.getMonto() > umbral).toList();
    }

}