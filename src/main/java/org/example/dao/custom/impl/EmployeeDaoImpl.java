package org.example.dao.custom.impl;

import org.example.dao.custom.EmployeeDao;
import org.example.entity.EmployeeEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public boolean save(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<EmployeeEntity> getAllEmployees() {
        Session session = HibernateUtil.getSession();
        List<EmployeeEntity> employee = null;
        try {
            Transaction transaction = session.beginTransaction();
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class);
            employee = query.getResultList();
            transaction.commit();
            session.close();
        } catch (Exception ignored) {
        }
        return employee;
    }

    @Override
    public EmployeeEntity findEmployeeById(String id) {
        Session session = HibernateUtil.getSession();
        EmployeeEntity employee = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            employee = session.get(EmployeeEntity.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }
    @Override
    public EmployeeEntity findEmployeeByEmail(String email) {
        Session session = HibernateUtil.getSession();
        EmployeeEntity employee = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity WHERE email = :email", EmployeeEntity.class);
            query.setParameter("email", email);
            employee = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = session.get(EmployeeEntity.class, id);
            if (employee != null) {
                session.delete(employee);
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
    public boolean updateEmployee(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity existingEmployee = session.get(EmployeeEntity.class, entity.getId());

            if (existingEmployee != null) {
                existingEmployee.setEmpTitle(entity.getEmpTitle());
                existingEmployee.setEmpName(entity.getEmpName());
                existingEmployee.setDob(entity.getDob());
                existingEmployee.setSalary(entity.getSalary());
                existingEmployee.setAddress(entity.getAddress());
                existingEmployee.setRole(entity.getRole());
                existingEmployee.setEmail(entity.getEmail());
                existingEmployee.setPassword(entity.getPassword());

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
