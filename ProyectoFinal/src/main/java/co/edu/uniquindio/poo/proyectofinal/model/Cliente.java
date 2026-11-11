package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.*;

public class Cliente {
    private final String  id;
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
        return Collections.unmodifiableList(beneficiosActivos) ;
    }



    public List<RegistroPuntos> getHistorialPuntos() {
        return Collections.unmodifiableList(historialPuntos) ;
    }
    // mano faltan estos metodos
    // sumarPuntos(int cantidad, RegistroPuntos registro)

    //restarPuntos(int cantidad, RegistroPuntos registro)

    //ecibirNotificacion(Notificacion notificacion)

    //programarTransaccion(TransaccionProgramada transaccion)

    // activarBeneficio(Beneficio beneficio)



}
