package com.ecomarket.service;

import com.ecomarket.model.Carrito;
import com.ecomarket.model.CarritoItem;
import com.ecomarket.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarritoService {

    private final CarritoRepository carritoRepository;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Optional<Carrito> obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id);
    }

    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> getCarritoByClienteId(Long clienteId) {
        return carritoRepository.findByClienteId(clienteId);
    }

    public Carrito crearCarrito(Carrito carrito) {
        if (carrito.getItems() != null && !carrito.getItems().isEmpty()) {
            for (CarritoItem item : carrito.getItems()) {
                if (item != null) {
                    item.setCarrito(carrito);
                }
            }
        }
        return carritoRepository.save(carrito);
    }

    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    public Carrito actualizarCarrito(Long id, Carrito carritoDetails) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + id));

        carrito.setClienteId(carritoDetails.getClienteId());
        carrito.setItems(carritoDetails.getItems());

        if (carrito.getItems() != null && !carrito.getItems().isEmpty()) {
            for (CarritoItem item : carrito.getItems()) {
                if (item != null) {
                    item.setCarrito(carrito);
                }
            }
        }

        return carritoRepository.save(carrito);
    }

    public Optional<Carrito> getCarritoById(Long id) {
        return carritoRepository.findById(id);
    }
}
