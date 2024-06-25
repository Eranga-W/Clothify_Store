package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemBo extends SuperBo{
    boolean saveItem(Item dto);
    boolean deleteById(String id);

    public List<ItemEntity> getAllItems();

    ItemEntity findItemById(String id);

    boolean updateItem(ItemEntity item);
}
