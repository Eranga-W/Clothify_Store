package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ItemEntity{
    @Id
    private String id;
    private String description;
    private String size;
    private Double unitPrice;
    private Integer qty;
}
