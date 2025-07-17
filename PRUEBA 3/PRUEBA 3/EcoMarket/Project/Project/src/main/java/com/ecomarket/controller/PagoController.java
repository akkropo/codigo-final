package com.ecomarket.controller;

import com.ecomarket.model.Pago;
import com.ecomarket.service.PagoService;
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
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Obtener todos los pagos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getAllPagos() {
        List<Pago> lista = pagoService.getAllPagos();
        List<EntityModel<Pago>> recursos = lista.stream()
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(PagoController.class).getPagoById(e.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).getAllPagos()).withRel("all")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(recursos));
    }

    @Operation(summary = "Obtener un pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        return pagoService.getPagoById(id)
            .map(e -> EntityModel.of(e,
                linkTo(methodOn(PagoController.class).getPagoById(id)).withSelfRel(),
                linkTo(methodOn(PagoController.class).getAllPagos()).withRel("all")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo pago")
    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.createPago(pago);
        return ResponseEntity.ok(nuevoPago);
    }
    @Operation(summary = "Eliminar pago por ID")
@DeleteMapping("/{id}")
public ResponseEntity<Void> deletePago(@PathVariable Long id) {
    pagoService.deletePago(id);
    return ResponseEntity.noContent().build();
}
@Operation(summary = "Actualizar pago por ID")
@PutMapping("/{id}")
public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
    Pago actualizado = pagoService.updatePago(id, pago);
    return ResponseEntity.ok(actualizado);
}


}