package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;

import java.util.List;

public interface OrderBo extends SuperBo{
    public boolean saveOrder(Order dto) ;
    boolean deleteById(String id);

    public List<OrderEntity> getAllOrders();
    public long countAllOrders();

    public OrderEntity findOrderById(String id);

    public boolean updateOrder(OrderEntity order);
}
