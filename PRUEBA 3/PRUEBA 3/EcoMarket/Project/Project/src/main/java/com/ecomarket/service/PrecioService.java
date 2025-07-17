package com.ecomarket.service;

import com.ecomarket.model.Precio;
import com.ecomarket.repository.PrecioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrecioService {

    private final PrecioRepository precioRepository;

    @Autowired
    public PrecioService(PrecioRepository precioRepository) {
        this.precioRepository = precioRepository;
    }

    public List<Precio> getAllPrecios() {
        return precioRepository.findAll();
    }

    public Optional<Precio> getPrecioById(Long id) {
        return precioRepository.findById(id);
    }

    public List<Precio> getPreciosByProductoId(Long productoId) {
        return precioRepository.findByProductoId(productoId);
    }

    public Precio createPrecio(Precio precio) {
        return precioRepository.save(precio);
    }

    public Precio updatePrecio(Long id, Precio precioDetails) {
        Precio precio = precioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Precio no encontrado con id: " + id));
        precio.setProductoId(precioDetails.getProductoId());
        precio.setPrecioBase(precioDetails.getPrecioBase());
        precio.setPrecioPromocion(precioDetails.getPrecioPromocion());
        precio.setDescripcionPromocion(precioDetails.getDescripcionPromocion());
        precio.setFechaInicio(precioDetails.getFechaInicio());
        precio.setFechaFin(precioDetails.getFechaFin());
        return precioRepository.save(precio);
    }

    public void deletePrecio(Long id) {
        Precio precio = precioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Precio no encontrado con id: " + id));
        precioRepository.delete(precio);
    }

    public Precio guardarPrecio(Precio precio) {
        return precioRepository.save(precio);
    }

    public Optional<Precio> obtenerPrecioPorId(long id) {
        return precioRepository.findById(id);
    }
}
