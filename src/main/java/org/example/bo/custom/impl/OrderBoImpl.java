package org.example.bo.custom.impl;

import org.example.bo.custom.ItemBo;
import org.example.bo.custom.OrderBo;
import org.example.dao.DaoFactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.OrderDao;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class OrderBoImpl implements OrderBo {

    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    @Override
    public boolean saveOrder(Order dto) {
        return orderDao.save(new ModelMapper().map(dto, OrderEntity.class));
    }

    @Override
    public boolean deleteById(String id) {
        return orderDao.delete(id);
    }

    public List<OrderEntity> getAllOrders(){
        return orderDao.getAllOrders();
    }
    public long countAllOrders(){
        return orderDao.countAllOrders();
    }

    @Override
    public OrderEntity findOrderById(String id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public boolean updateOrder(OrderEntity order) {
        return orderDao.updateOrder(order);
    }
}
