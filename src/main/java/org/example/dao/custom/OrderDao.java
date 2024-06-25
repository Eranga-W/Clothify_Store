package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends CrudDao<OrderEntity> {

    List<OrderEntity> getAllOrders();
    public long countAllOrders();

    OrderEntity findOrderById(String id);

    boolean updateOrder(OrderEntity orders);
}
