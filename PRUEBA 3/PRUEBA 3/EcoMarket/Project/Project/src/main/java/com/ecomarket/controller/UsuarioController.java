package com.ecomarket.controller;

import com.ecomarket.model.Usuario;
import com.ecomarket.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        List<Usuario> lista = usuarioService.getAllUsuarios();
        List<EntityModel<Usuario>> recursos = lista.stream()
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(e.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("all")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(recursos));
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("all")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existsByUsername(usuario.getUsername()) ||
            usuarioService.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @Operation(summary = "Eliminar usuario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Actualizar usuario por ID")
@PutMapping("/{id}")
public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    Usuario actualizado = usuarioService.updateUsuario(id, usuario);
    return ResponseEntity.ok(actualizado);
}
}
