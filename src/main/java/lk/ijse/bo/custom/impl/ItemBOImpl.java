package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQty()));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQty()));
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        List<Item> items = itemDAO.getAll();
        List<ItemDTO> itemList = new ArrayList<>();

        for (Item item : items){
            ItemDTO itemDTO = new ItemDTO(item.getItemCode(),item.getDescription(),item.getUnitPrice(),item.getQty());
            itemList.add(itemDTO);
        }
        return itemList;
    }

    @Override
    public boolean deleteItem(int itemCode) {
        return itemDAO.delete(itemCode);
    }
}
