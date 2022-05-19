package com.cloudconferenceday.webflux;

import com.cloudconferenceday.webflux.controller.OrderController;
import com.cloudconferenceday.webflux.model.Order;
import com.cloudconferenceday.webflux.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    private WebTestClient webTestClient;

    private final OrderController orderController = new OrderController(orderService);
    private static Order orderMock = OrderMock.order();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient
                .bindToController(new OrderController(orderService))
                .configureClient()
                .baseUrl("/order")
                .filter(documentationConfiguration(restDocumentation))
                .build();

        orderMock = OrderMock.order();
    }



    @Test
    @DisplayName("Deve busca uma ordem pelo id ")
    void shouldFindOrderById() {

        doReturn(Mono.just(orderMock))
                .when(orderService)
                .findOrderById("de37b55d-fad2-4248-946e-04e2963e6580");
        webTestClient.get()
                .uri("/{id}", orderMock.getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document(
                        "find-order-by-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

}