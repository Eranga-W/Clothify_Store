package org.example.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemsInventory {
    private String id;
    private String description;
    private String size;
    private Double unitPrice;
    private Integer qty;
}
