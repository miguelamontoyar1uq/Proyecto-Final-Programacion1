package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.*;

public class MonederoVirtual {
    private final String nombre;
    private  Map<String, Cliente> listaClientes;
    private  List<Transaccion> listaTransacciones;
    private  List<TransaccionProgramada> listaTransaccionesProgramadas;

    public MonederoVirtual(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del monedero virtual no puede estar vacío.");
        }
        this.nombre = nombre;
        listaClientes = new HashMap<>();
        listaTransacciones = new ArrayList<>();
        listaTransaccionesProgramadas = new ArrayList<>();
    }


    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (listaClientes.containsKey(cliente.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con la identificación " + cliente.getId());
        }
        this.listaClientes.put(cliente.getId(), cliente);
    }

    public Optional<Cliente> buscarCliente(String id) {
        return Optional.ofNullable(listaClientes.get(id));
    }

    public void registrarTransaccionGlobal(Transaccion transaccion) {
        if (transaccion != null) {
            this.listaTransacciones.add(transaccion);
        }
    }

    public void agregarTransaccionProgramada(TransaccionProgramada transaccion) {
        if (transaccion != null) {
            this.listaTransaccionesProgramadas.add(transaccion);
        }
    }


    public String getNombre() {
        return nombre;
    }

    public Map<String, Cliente> getListaClientes() {
        return Collections.unmodifiableMap(listaClientes);
    }

    public List<Transaccion> getListaTransacciones() {
        return Collections.unmodifiableList(listaTransacciones);
    }

    public List<TransaccionProgramada> getListaTransaccionesProgramadas() {
        return Collections.unmodifiableList(listaTransaccionesProgramadas);
    }
}