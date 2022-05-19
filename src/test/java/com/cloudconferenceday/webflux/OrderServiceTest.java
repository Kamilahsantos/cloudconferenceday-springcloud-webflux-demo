package com.cloudconferenceday.webflux;

import com.cloudconferenceday.webflux.model.Order;
import com.cloudconferenceday.webflux.repository.OrderRepository;
import com.cloudconferenceday.webflux.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;


    private final Order orderMock = OrderMock.order();

    @BeforeEach
    public void setUp() {

        BDDMockito.when(orderRepository.save(OrderMock.order()))
                .thenReturn(Mono.just(orderMock));


        BDDMockito.when(orderRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(orderMock));

    }

    @Test
    @DisplayName("Deve criar uma ordem")
    public void shouldCreateAnOrderSuccessfully() {
        Order orderToBeSaved = OrderMock.order();

        StepVerifier.create(orderService.saveOrder(orderToBeSaved))
                .expectSubscription()
                .expectNext(orderToBeSaved)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve buscar e retornar uma ordem pelo id ")
    public void shouldFindByIdAndReturnAnOrderSuccessfully() {
        StepVerifier.create(orderService.findOrderById("de37b55d-fad2-4248-946e-04e2963e6580"))
                .expectSubscription()
                .expectNext(orderMock)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deve retornar exception quando não encontrar uma ordem")
    public void ShouldReturnExceptionWhenOrderNotFound() {
        BDDMockito.when(orderRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(orderService.findOrderById("ordem invalda-123"))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }
}