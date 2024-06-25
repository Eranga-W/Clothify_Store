package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemDao extends CrudDao<ItemEntity> {

    List<ItemEntity> getAllItems();

    ItemEntity findItemById(String id);

    boolean updateItem(ItemEntity items);
}
