package com.ecomarket.controller;

import com.ecomarket.model.Carrito;
import com.ecomarket.service.CarritoService;
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
@RequestMapping("/api/carrito")
@Tag(name = "Carritos", description = "Operaciones relacionadas con carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Obtener todos los carritos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> getAllCarritos() {
        List<Carrito> lista = carritoService.getAllCarritos();
        List<EntityModel<Carrito>> recursos = lista.stream()
                .map(e -> EntityModel.of(e,
                        linkTo(methodOn(CarritoController.class).getCarritoById(e.getId())).withSelfRel(),
                        linkTo(methodOn(CarritoController.class).getAllCarritos()).withRel("all")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(recursos));
    }

    @Operation(summary = "Obtener un carrito por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Carrito>> getCarritoById(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id)
                .map(e -> EntityModel.of(e,
                        linkTo(methodOn(CarritoController.class).getCarritoById(id)).withSelfRel(),
                        linkTo(methodOn(CarritoController.class).getAllCarritos()).withRel("all")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo carrito")
    @PostMapping
    public ResponseEntity<?> crearCarrito(@RequestBody Carrito carrito) {
        try {
            Carrito nuevo = carritoService.crearCarrito(carrito);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el carrito: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar carrito por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar carrito por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        try {
            Carrito actualizado = carritoService.actualizarCarrito(id, carrito);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el carrito: " + e.getMessage());
        }
    }
}
