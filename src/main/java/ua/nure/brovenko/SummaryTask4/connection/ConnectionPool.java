package ua.nure.brovenko.SummaryTask4.connection;


import ua.nure.brovenko.SummaryTask4.exception.ConnectionPoolException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.DBConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Database connection pool
 * @author Ivan Brovenko
 */
public class ConnectionPool implements Pool<Connection> {

    /**
     * Singleton instace
     */
    private static ConnectionPool instance = null;

    /**
     * Pool size
     */
    private static final int POOL_SIZE = 10;

    /**
     * Maximum waiting time
     */
    private static final int MAX_TIME = 2;

    /**
     * Concurrent fair lock
     */
    private static Lock lock = new ReentrantLock(true);

    /**
     * Connections
     */
    private BlockingQueue<Connection> connections;

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Database configuration manager
     */
    private DBConfigurationManager conf = DBConfigurationManager.INSTANCE;

    /**
     * Connection url for driver
     */
    private final String CONNECTION = conf.getString(DBConfigurationManager.DATABASE_CONNECTION_URL);

    /**
     * Connection username for driver
     */
    private final String USERNAME = conf.getString(DBConfigurationManager.DATABASE_USERNAME);

    /**
     * Connection password for driver
     */
    private final String PASSWORD = conf.getString(DBConfigurationManager.DATABASE_PASSWORD);

    /**
     * Driver path
     */
    private final String DRIVER = conf.getString(DBConfigurationManager.DATABASE_DRIVER_NAME);

    /**
     * Connection pool private constructor
     * @throws ConnectionPoolException if something goes wrong
     */
    private ConnectionPool() throws ConnectionPoolException {
        initConnection();
    }

    /**
     * Get single connection from concurrent queue
     * @return connection to use
     */
    public static ConnectionPool getInstance() throws ConnectionPoolException{
        lock.lock();

        if (null == instance){
            instance = new ConnectionPool();
        }
        lock.unlock();

        return instance;
    }

    /**
     * Create and fill connection pool
     * @throws ConnectionPoolException if something goes wrong exception will be throw
     */
    private void initConnection() throws ConnectionPoolException {
        connections = new LinkedBlockingDeque<>(POOL_SIZE);

        try{
            Class.forName(DRIVER);
            for(int i = 0; i<POOL_SIZE; i++){
                Connection connection = DriverManager.getConnection(CONNECTION,USERNAME,PASSWORD);
                connections.add(connection);
            }
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    /**
     * Get single connection from concurrent queue
     * @return connection to use
     */
    @Override
    public  final Connection getConnection() {
        try {
            Connection connection = connections.poll(MAX_TIME, TimeUnit.SECONDS);
            if(connection != null){
                logger.info("Connection " + connection + " took from connection pool");
            } else {
                logger.error("Couldn't retrieve a connection from pool");
            }
            return connection;
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * Return connection
     * @param connection connection to return to the pool
     */
    @Override
    public void release(Connection connection) {
        if(connection != null){
            try {
                connections.put(connection);
                logger.info("Connection " + connection + "  released");
                logger.info("There are(is) " + (connections.size() - connections.remainingCapacity()) + " connection(s) in the pool.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close all connections
     */
    public void shutDown(){
        Iterator<Connection> iterator = connections.iterator();

        while(iterator.hasNext()){
            Connection connection = iterator.next();

            try {
                
                // close connection
                connection.close();
                
                // remove it to prevent the use of closed connection
                iterator.remove();
            } catch (SQLException e) {
                logger.error("Couldn't close connection: " + e.getMessage());
            }
        }
        
        logger.info("Connection pool is shut down");
    }
}
