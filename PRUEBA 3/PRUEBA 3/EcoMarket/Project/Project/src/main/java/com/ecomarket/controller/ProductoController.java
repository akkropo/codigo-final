package com.ecomarket.controller;

import com.ecomarket.model.Producto;
import com.ecomarket.service.ProductoService;
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
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener todos los productos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
        List<Producto> lista = productoService.getAllProductos();
        List<EntityModel<Producto>> recursos = lista.stream()
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(ProductoController.class).getProductoById(e.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("all")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(recursos));
    }

    @Operation(summary = "Obtener un producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id)
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(ProductoController.class).getProductoById(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("all")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.createProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }
    @Operation(summary = "Eliminar producto por ID")
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
    productoService.deleteProducto(id);
    return ResponseEntity.noContent().build();
}
@Operation(summary = "Actualizar producto por ID")
@PutMapping("/{id}")
public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
    Producto actualizado = productoService.updateProducto(id, producto);
    return ResponseEntity.ok(actualizado);
}


}
