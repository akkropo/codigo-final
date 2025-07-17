package com.ecomarket;

import com.ecomarket.model.Pago;
import com.ecomarket.repository.PagoRepository;
import com.ecomarket.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoServiceTest {

    private PagoRepository pagoRepository;
    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        pagoRepository = Mockito.mock(PagoRepository.class);
        pagoService = new PagoService(pagoRepository);
    }

    @Test
    void testGuardarPago() {
        Pago pago = new Pago();
        pago.setPedidoId(1L);
        pago.setMonto(19990.0); // CORREGIDO: Double con decimal
        pago.setMetodoPago("Transferencia");
        pago.setEstado("Pagado");
        pago.setFechaPago(LocalDate.of(2025, 6, 25)); // CORREGIDO: LocalDate

        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago guardado = pagoService.guardarPago(pago);

        assertNotNull(guardado);
        assertEquals(19990.0, guardado.getMonto());
        assertEquals("Pagado", guardado.getEstado());
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    void testObtenerPagoPorId_Existe() {
        Pago pago = new Pago();
        pago.setId(1L);

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Optional<Pago> resultado = pagoService.obtenerPagoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testObtenerPagoPorId_NoExiste() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Pago> resultado = pagoService.obtenerPagoPorId(1L);

        assertFalse(resultado.isPresent());
    }
}
