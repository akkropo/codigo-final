package com.ecomarket;


import com.ecomarket.model.Precio;
import com.ecomarket.repository.PrecioRepository;
import com.ecomarket.service.PrecioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrecioServiceTest {

    private PrecioRepository precioRepository;
    private PrecioService precioService;

    @BeforeEach
    void setUp() {
        precioRepository = Mockito.mock(PrecioRepository.class);
        precioService = new PrecioService(precioRepository);
    }

    @Test
    void testGuardarPrecio() {
    Precio precio = new Precio();
    precio.setProductoId(1L);
    precio.setPrecioBase(1000.0); // ✅ Double
    precio.setPrecioPromocion(800.0); // ✅ Double
    precio.setDescripcionPromocion("Oferta lanzamiento");
    precio.setFechaInicio(LocalDate.of(2025, 6, 1));
    precio.setFechaFin(LocalDate.of(2025, 6, 30));

    when(precioRepository.save(any(Precio.class))).thenReturn(precio);

    Precio guardado = precioService.guardarPrecio(precio);

    assertNotNull(guardado);
    assertEquals("Oferta lanzamiento", guardado.getDescripcionPromocion());
    verify(precioRepository, times(1)).save(precio);
}


    @Test
    void testObtenerPrecioPorId_Existe() {
        Precio precio = new Precio();
        precio.setId(1L);

        when(precioRepository.findById(1L)).thenReturn(Optional.of(precio));

        Optional<Precio> resultado = precioService.obtenerPrecioPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testObtenerPrecioPorId_NoExiste() {
        when(precioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Precio> resultado = precioService.obtenerPrecioPorId(1L);

        assertFalse(resultado.isPresent());
    }
}
