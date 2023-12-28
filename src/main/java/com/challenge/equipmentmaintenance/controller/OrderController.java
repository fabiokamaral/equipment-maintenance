package com.challenge.equipmentmaintenance.controller;

import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.requests.HistoryPostRequestBody;
import com.challenge.equipmentmaintenance.requests.OrderPostRequestBody;
import com.challenge.equipmentmaintenance.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(
            summary = "Lista todas as Ordens de Serviço paginadas",
            description = "A quantidade padrão é 20, use o parâmetro size para alterar o padrão.",
            tags = {"Ordem de Serviço"}
    )
    public ResponseEntity<Page<Order>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(orderService.listAll(pageable));
    }

    @GetMapping(path = "/{orderId}")
    @Operation(
            summary = "Busca uma Ordem de Serviço pelo Id",
            tags = {"Ordem de Serviço"}
    )
    public ResponseEntity<Order> findByOrderId(@Schema(description = "Id da Ordem de Serviço") @PathVariable long orderId) {
        return ResponseEntity.ok(orderService.findByIdOrThrowBadRequestException(orderId));
    }

    @GetMapping(path = "/find-by-registration")
    @Operation(
            summary = "Busca uma Ordem de Serviço pela matrícula do responsável",
            tags = {"Ordem de Serviço"}
    )
    public ResponseEntity<List<Order>> findByRegistration(@Schema(description = "Matricula do responsável pela Ordem de Serviço") @RequestParam String registration) {
        return ResponseEntity.ok(orderService.findByResponsibleRegistration(registration));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Realiza o cadastro de uma Ordem de Serviço",
            tags = {"Ordem de Serviço"}
    )
    public ResponseEntity<Order> save(@ParameterObject @RequestBody @Valid OrderPostRequestBody orderPostRequestBody) {
        return new ResponseEntity<>(orderService.save(orderPostRequestBody), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/history")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Adiciona um registro de acompanhamento em uma Ordem de Serviço",
            tags = {"Ordem de Serviço"}
    )
    public ResponseEntity<Order> addHistory(@ParameterObject @RequestBody @Valid HistoryPostRequestBody historyPostRequestBody) {
        return new ResponseEntity<>(orderService.addHistory(historyPostRequestBody), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/start/{orderId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Permite que o responsável inicie uma Ordem de Serviço",
            tags = {"Ordem de Serviço"},
            parameters = @Parameter(name = "orderId", description = "Id da Ordem de Serviço")
    )
    public ResponseEntity<Void> startOrder(@PathVariable long orderId) {
        orderService.startOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/stop/{orderId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Permite que o responsável finalize uma Ordem de Serviço",
            tags = {"Ordem de Serviço"},
            parameters = @Parameter(name = "orderId", description = "Id da Ordem de Serviço")
    )
    public ResponseEntity<Void> stopOrder(@PathVariable long orderId) {
        orderService.stopOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Permite que uma Ordem de Serviço seja apagada",
            tags = {"Ordem de Serviço"},
            parameters = @Parameter(name = "orderId", description = "Id da Ordem de Serviço")
    )
    public ResponseEntity<Void> delete(@PathVariable long orderId) {
        orderService.delete(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
