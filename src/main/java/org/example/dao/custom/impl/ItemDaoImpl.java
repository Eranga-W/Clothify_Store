package org.example.dao.custom.impl;

import org.example.dao.custom.ItemDao;
import org.example.entity.ItemEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public boolean save(ItemEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<ItemEntity> getAllItems() {
        Session session = HibernateUtil.getSession();
        List<ItemEntity> item = null;
        try {
            Transaction transaction = session.beginTransaction();
            Query<ItemEntity> query = session.createQuery("FROM ItemEntity", ItemEntity.class);
            item = query.getResultList();
            transaction.commit();
            session.close();
        } catch (Exception ignored) {
        }
        return item;
    }

    @Override
    public ItemEntity findItemById(String id) {
        Session session = HibernateUtil.getSession();
        ItemEntity item = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            item = session.get(ItemEntity.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            ItemEntity item = session.get(ItemEntity.class, id);
            if (item != null) {
                session.delete(item);
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
    public boolean updateItem(ItemEntity items) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            ItemEntity existingItem = session.get(ItemEntity.class, items.getId());

            if (existingItem != null) {
                existingItem.setId(items.getId());
                existingItem.setDescription(items.getDescription());
                existingItem.setSize(items.getSize());
                existingItem.setUnitPrice(items.getUnitPrice());
                existingItem.setQty(items.getQty());

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
