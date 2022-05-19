package com.cloudconferenceday.webflux.service;


import com.cloudconferenceday.webflux.model.Order;
import com.cloudconferenceday.webflux.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {


    OrderRepository orderRepository;


    public Mono<Order> saveOrder(Order order) {
        log.info("Criando um novo pagamento [{}]", order);
        return orderRepository.save(order);
    }

    public Mono<Order> findOrderById(String id) {
        log.info("Buscando ordem com o id [{}]", id);
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .doOnError(error -> log.error("nao foi encontrada nenhuma ordem com esse id [{}]", id));

    }
}
