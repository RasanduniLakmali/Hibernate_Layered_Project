package lk.ijse.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
     public boolean save(T entity) throws SQLException,ClassNotFoundException;
     public List<T> getAll() throws SQLException;

     public boolean update(T dto);
     public boolean delete(String id);

}
