package com.ecomarket;

import com.ecomarket.model.Usuario;
import com.ecomarket.repository.UsuarioRepository;
import com.ecomarket.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("Juan");
        usuario.setPassword("123");
        usuario.setEmail("juan@mail.com");
        usuario.setRol("CLIENTE");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario guardado = usuarioService.createUsuario(usuario); 

        assertNotNull(guardado);
        assertEquals("Juan", guardado.getUsername());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testBuscarPorId_Existe() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.getUsuarioById(1L); 

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void testBuscarPorId_NoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.getUsuarioById(1L); 

        assertFalse(resultado.isPresent());
    }
}
