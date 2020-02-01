package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface dao that contains constants
 * for different types of exception
 * @param <T> object that extends entity
 * @author Ivan Brovenko
 */
public interface DAO<T extends Entity> {

    /**
     * Message that occurs when we can't rollback
     */
    String NO_ROLLBACK = "Can't rollback operation";

    /**
     * Message that occurs if there's an exception in a connection pool
     */
    String CONNECTION_POOL_EX = "Something wrong with your connection pool";

    /**
     * Message that occurs when there's no connection
     */
    String NO_CONNECTION = "Unable to get connection with database";

    /**
     * Message that occurs when entity can't be found
     */
    String ENTITY_WAS_NOT_FOUND = "Entity wasn't found";

    /**
     * Message that occurs if one of the parameters is null
     */
    String NULL_PARAMETER = "One of the parameters is null";

    /**
     * Method for creating entities
     * @param set result set
     * @return entity object
     * @throws SQLException if something goes wrong
     */
    T createEntity(ResultSet set) throws SQLException;
}
