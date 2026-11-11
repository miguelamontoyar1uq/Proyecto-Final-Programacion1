package co.edu.uniquindio.poo.proyectofinal.model;

import co.edu.uniquindio.poo.proyectofinal.exceptions.MonederoInactivoException;
import co.edu.uniquindio.poo.proyectofinal.exceptions.SaldoInsuficienteException;

import java.util.*;

public class MonederoVirtual {
    private final String nombre;
    private Map<String, Cliente> listaClientes;
    private List<Transaccion> listaTransacciones;
    private List<TransaccionProgramada> listaTransaccionesProgramadas;

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

    private Optional<Monedero> buscarMonedero(String numeroCuenta) {
        return listaClientes.values().stream()
                .flatMap(c -> c.getListaMonederos().values().stream())
                .filter(m -> m.getNumeroCuenta().equals(numeroCuenta))
                .findFirst();
    }

    public Transferencia transferir(String idCliente, String cuentaOrigen, String cuentaDestino, double monto)
            throws MonederoInactivoException, SaldoInsuficienteException {

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (cuentaOrigen.equals(cuentaDestino)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma.");
        }

        Cliente cliente = buscarCliente(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("No existe el cliente " + idCliente));

        Monedero origen = cliente.getListaMonederos().values().stream()
                .filter(m -> m.getNumeroCuenta().equals(cuentaOrigen))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("La cuenta origen no pertenece al cliente."));

        Monedero destino = buscarMonedero(cuentaDestino)
                .orElseThrow(() -> new IllegalArgumentException("No existe la cuenta destino " + cuentaDestino));

        // Se revisa antes de mover plata, para no debitar y luego fallar al acreditar
        if (!destino.isActivo()) {
            throw new MonederoInactivoException("El monedero destino " + cuentaDestino + " se encuentra inactivo.");
        }

        origen.debitar(monto);
        destino.acreditar(monto);

        Transferencia transferencia = new Transferencia(UUID.randomUUID().toString(), monto,
                "Transferencia de " + cuentaOrigen + " a " + cuentaDestino, cuentaDestino);

        origen.registrarTransaccion(transferencia);
        destino.registrarTransaccion(transferencia);
        registrarTransaccionGlobal(transferencia);
        cliente.acumularPuntos(transferencia);

        return transferencia;
    }

    public List<Transaccion> ejecutarProgramadas(String idCliente)
            throws MonederoInactivoException, SaldoInsuficienteException {

        Cliente cliente = buscarCliente(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("No existe el cliente " + idCliente));

        // Pendientes y ordenadas por fecha (usa el compareTo de TransaccionProgramada)
        List<TransaccionProgramada> pendientes = cliente.getListaTransaccionesProgramadas().stream()
                .filter(TransaccionProgramada::estaPendiente)
                .sorted()
                .toList();

        List<Transaccion> ejecutadas = new ArrayList<>();

        for (TransaccionProgramada programada : pendientes) {
            Monedero origen = cliente.getListaMonederos().values().stream()
                    .filter(m -> m.getNumeroCuenta().equals(programada.getNumeroCuentaOrigen()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("La cuenta origen no pertenece al cliente."));

            if (programada.getTipo().equals("Depósito")) {
                Deposito deposito = origen.depositar(programada.getMonto());
                registrarTransaccionGlobal(deposito);
                cliente.acumularPuntos(deposito);
                ejecutadas.add(deposito);
            } else {
                ejecutadas.add(transferir(idCliente, programada.getNumeroCuentaOrigen(),
                        programada.getCuentaDestino(), programada.getMonto()));
            }

            programada.reprogramar();
        }

        return ejecutadas;
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