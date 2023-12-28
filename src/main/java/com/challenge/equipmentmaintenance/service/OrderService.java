package com.challenge.equipmentmaintenance.service;

import com.challenge.equipmentmaintenance.exceptions.BadRequestException;
import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.repository.OrderRepository;
import com.challenge.equipmentmaintenance.requests.HistoryPostRequestBody;
import com.challenge.equipmentmaintenance.requests.OrderPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.challenge.equipmentmaintenance.model.Status.FINISHED;
import static com.challenge.equipmentmaintenance.model.Status.PROCESSING;
import static com.challenge.equipmentmaintenance.model.Status.WAITING;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DatabaseSequenceGeneratorService databaseSequenceGeneratorService;

    public Page<Order> listAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findByIdOrThrowBadRequestException(long orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BadRequestException("Order not found"));
    }

    public List<Order> findByResponsibleRegistration(String registration) {
        return orderRepository.findByResponsibleRegistration(registration);
    }

    public Order save(OrderPostRequestBody orderPostRequestBody) {
        Order order = orderPostRequestBody.toModel();
        order.setOrderId(databaseSequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
        order.setStatus(WAITING);
        return orderRepository.save(order);
    }

    public Order addHistory(HistoryPostRequestBody historyPostRequestBody) {
        Order order = findByIdOrThrowBadRequestException(historyPostRequestBody.getOrderId());
        if (PROCESSING.equals(order.getStatus())) {
            order.addHistory(historyPostRequestBody.toModel());
            return orderRepository.save(order);
        }

        throw new BadRequestException("Cannot add history to order. Actual status " + order.getStatus());
    }

    public void startOrder(long orderId) {
        Order order = findByIdOrThrowBadRequestException(orderId);
        if (WAITING.equals(order.getStatus())) {
            order.setStatus(PROCESSING);
            orderRepository.save(order);
            return;
        }

        throw new BadRequestException("Cannot start order. Actual status " + order.getStatus());
    }

    public void stopOrder(long orderId) {
        Order order = findByIdOrThrowBadRequestException(orderId);
        if (FINISHED.equals(order.getStatus())) {
            throw new BadRequestException("Cannot stop order. Actual status " + order.getStatus());
        }

        order.setStatus(FINISHED);
        orderRepository.save(order);
    }

    public void delete(long orderId) {
        orderRepository.delete(findByIdOrThrowBadRequestException(orderId));
    }
}
