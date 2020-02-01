package ua.nure.brovenko.SummaryTask4.connection;


import java.sql.Connection;

/**
 * Connection pool interface
 * @param <T> connection object
 * @author Ivan Brovenko
 */
public interface Pool<T extends Connection> {

    /**
     * Retrieves one object from the pool
     * @return a connection object
     */
    T getConnection();

    /**
     * Releases(returns) object to the pool
     * @param t connection that should be released
     */
    void release(T t);
}
