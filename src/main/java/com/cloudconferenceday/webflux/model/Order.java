package com.cloudconferenceday.webflux.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "order")
@With
public class Order {

    @Id
    private String uuid;
    @NotBlank
    private String sellerId;
    @NotBlank
    private String customerId;
    @NotBlank
    private Long amount;
}
