package com.ecomarket.controller;

import com.ecomarket.model.Precio;
import com.ecomarket.service.PrecioService;
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
@RequestMapping("/api/precios")
@Tag(name = "Precios", description = "Operaciones relacionadas con precios")
public class PrecioController {

    @Autowired
    private PrecioService precioService;

    @Operation(summary = "Obtener todos los precios")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Precio>>> getAllPrecios() {
        List<Precio> lista = precioService.getAllPrecios();
        List<EntityModel<Precio>> recursos = lista.stream()
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(PrecioController.class).getPrecioById(e.getId())).withSelfRel(),
                linkTo(methodOn(PrecioController.class).getAllPrecios()).withRel("all")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(recursos));
    }

    @Operation(summary = "Obtener un precio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Precio>> getPrecioById(@PathVariable Long id) {
        return precioService.getPrecioById(id)
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(PrecioController.class).getPrecioById(id)).withSelfRel(),
                linkTo(methodOn(PrecioController.class).getAllPrecios()).withRel("all")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo precio")
    @PostMapping
    public ResponseEntity<Precio> createPrecio(@RequestBody Precio precio) {
        Precio nuevo = precioService.createPrecio(precio);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Eliminar precio por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrecio(@PathVariable Long id) {
    precioService.deletePrecio(id);
    return ResponseEntity.noContent().build();
}
@Operation(summary = "Actualizar precio por ID")
@PutMapping("/{id}")
public ResponseEntity<Precio> updatePrecio(@PathVariable Long id, @RequestBody Precio precio) {
    Precio actualizado = precioService.updatePrecio(id, precio);
    return ResponseEntity.ok(actualizado);
}


}