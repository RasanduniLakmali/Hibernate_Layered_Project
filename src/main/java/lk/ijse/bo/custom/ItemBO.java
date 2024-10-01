package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemDTO itemDTO) throws SQLException,ClassNotFoundException;
    public List<ItemDTO> getAllItems() throws SQLException;
    public boolean deleteItem(String itemCode);
    public  ItemDTO searchItem(String itemCode) throws SQLException;
}
