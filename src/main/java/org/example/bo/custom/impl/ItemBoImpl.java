package org.example.bo.custom.impl;

import org.example.bo.custom.ItemBo;
import org.example.dao.DaoFactory;
import org.example.dao.custom.ItemDao;
import org.example.dto.Item;
import org.example.entity.ItemEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public boolean saveItem(Item dto) {
        return itemDao.save(new ModelMapper().map(dto, ItemEntity.class));
    }

    @Override
    public boolean deleteById(String id) {
        return itemDao.delete(id);
    }

    public List<ItemEntity> getAllItems(){
        return itemDao.getAllItems();
    }

    @Override
    public ItemEntity findItemById(String id) {
        return itemDao.findItemById(id);
    }

    @Override
    public boolean updateItem(ItemEntity item) {
        return itemDao.updateItem(item);
    }
}
