package com.ecomarket;

import com.ecomarket.model.Producto;
import com.ecomarket.repository.ProductoRepository;
import com.ecomarket.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    private ProductoRepository repository;
    private ProductoService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProductoRepository.class);
        service = new ProductoService(repository);
    }

    @Test
    void testGuardarProducto() {
        Producto obj = new Producto();
        when(repository.save(any(Producto.class))).thenReturn(obj);

        Producto guardado = service.guardarProducto(obj);

        assertNotNull(guardado);
        verify(repository, times(1)).save(obj);
    }

    @Test
    void testBuscarPorId_Existe() {
        Producto obj = new Producto();
        obj.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(obj));

        Optional<Producto> resultado = service.obtenerProductoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testBuscarPorId_NoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = service.obtenerProductoPorId(1L);

        assertFalse(resultado.isPresent());
    }
}
