package com.ecomarket.service;

import com.ecomarket.model.Pago;
import com.ecomarket.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoService {

    private final PagoRepository pagoRepository;

    @Autowired
    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> getPagoById(Long id) {
        return pagoRepository.findById(id);
    }

    public List<Pago> getPagosByPedidoId(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }

    public Pago createPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Pago updatePago(Long id, Pago pagoDetails) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
        pago.setPedidoId(pagoDetails.getPedidoId());
        pago.setMonto(pagoDetails.getMonto());
        pago.setMetodoPago(pagoDetails.getMetodoPago());
        pago.setEstado(pagoDetails.getEstado());
        pago.setFechaPago(pagoDetails.getFechaPago());
        return pagoRepository.save(pago);
    }

    public void deletePago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
        pagoRepository.delete(pago);
    }

    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }
}
