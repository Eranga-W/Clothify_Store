package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderEntity {
    @Id
    private String orderId;
    private Date orderDate;
    private String employee;
    private Double netTotal;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailEntity> orderDetailList;
}
