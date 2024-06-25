package org.example.dao;

import org.example.dao.custom.ItemDao;
import org.example.dao.custom.impl.EmployeeDaoImpl;
import org.example.dao.custom.impl.ItemDaoImpl;
import org.example.dao.custom.impl.OrderDaoImpl;
import org.example.util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance!=null?instance:(instance=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case EMPLOYEE:return (T)new EmployeeDaoImpl();
            case ITEM:return (T)new ItemDaoImpl();
            case ORDER:return (T)new OrderDaoImpl();
        }
        return null;
    }
}
