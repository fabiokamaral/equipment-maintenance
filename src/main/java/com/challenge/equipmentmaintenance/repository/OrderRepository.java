package com.challenge.equipmentmaintenance.repository;

import com.challenge.equipmentmaintenance.model.Order;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByOrderId(Long orderId);

    List<Order> findByResponsibleRegistration(String registration);

}
