package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {
    private String id;
    private String orderId;
    private String itemCode;
    private Double unitPrice;
    private Integer qty;
    private Double total;
}
