package org.example.dao.custom.impl;

import org.example.dao.custom.OrderDao;
import org.example.entity.OrderEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public boolean save(OrderEntity order) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(order);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<OrderEntity> getAllOrders() {
        Session session = HibernateUtil.getSession();
        List<OrderEntity> order = null;
        try {
            Transaction transaction = session.beginTransaction();
            Query<OrderEntity> query = session.createQuery("FROM OrderEntity", OrderEntity.class);
            order = query.getResultList();
            transaction.commit();
            session.close();
        } catch (Exception ignored) {
        }
        return order;
    }
    public long countAllOrders() {
        List<OrderEntity> orders = getAllOrders();
        return orders != null ? orders.size() : 0;
    }

    @Override
    public OrderEntity findOrderById(String id) {
        Session session = HibernateUtil.getSession();
        OrderEntity order = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            order = session.get(OrderEntity.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return order;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            OrderEntity order = session.get(OrderEntity.class, id);
            if (order != null) {
                session.delete(order);
                transaction.commit();
                return true;
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                return false;
            }
        } catch (Exception ignored) {
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateOrder(OrderEntity order){
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            OrderEntity existingOrder = session.get(OrderEntity.class, order.getOrderId());

            if (existingOrder != null) {
                existingOrder.setOrderId(order.getOrderId());
                existingOrder.setOrderDate(order.getOrderDate());
                existingOrder.setEmployee(order.getEmployee());
                existingOrder.setNetTotal(order.getNetTotal());
                existingOrder.setOrderDetailList(order.getOrderDetailList());
                transaction.commit();
                return true;
            } else {

                if (transaction != null) {
                    transaction.rollback();
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}
