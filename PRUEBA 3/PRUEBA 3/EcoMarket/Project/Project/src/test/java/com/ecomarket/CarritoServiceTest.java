package com.ecomarket;

import com.ecomarket.model.Carrito;
import com.ecomarket.repository.CarritoRepository;
import com.ecomarket.service.CarritoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

public class CarritoServiceTest {

    private CarritoRepository repository;
    private CarritoService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarritoRepository.class);
        service = new CarritoService(repository);
    }

    @Test
    void testGuardarCarrito() {
        Carrito obj = new Carrito();
        when(repository.save(any(Carrito.class))).thenReturn(obj);

        Carrito guardado = service.guardarCarrito(obj);

        assertNotNull(guardado);
        verify(repository, times(1)).save(obj);
    }

    @Test
    void testBuscarPorId_Existe() {
        Carrito obj = new Carrito();
        obj.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(obj));

        Optional<Carrito> resultado = service.obtenerCarritoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testBuscarPorId_NoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Carrito> resultado = service.obtenerCarritoPorId(1L);

        assertFalse(resultado.isPresent());
    }
}
