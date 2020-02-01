package ua.nure.brovenko.SummaryTask4.dao;

import ua.nure.brovenko.SummaryTask4.exception.ApplicationException;
import ua.nure.brovenko.SummaryTask4.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


public class ConnectionPool {

    private final static int POOL_SIZE = 10;
    private final static int MAX_TIME = 2;
    private final static String URL = "jdbc:mysql://localhost:3306/admission?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "rootroot";

    private static ConnectionPool instance;

    private BlockingQueue<Connection> connections = new LinkedBlockingDeque<>(POOL_SIZE);
    private Logger logger = Logger.getRootLogger();


    private ConnectionPool() {
        initConnection();
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return connections.poll(MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Can't poll the connection!", e);
            throw new ApplicationException("Can't poll the connection!", e);
        }
    }

    public void release(Connection connection) {
        try {
            connections.put(connection);
            logger.info("Connection " + connection + "  released");
            logger.info("There are(is) " + (connections.size() - connections.remainingCapacity()) + " connection(s) in the pool.");
        } catch (InterruptedException e) {
            logger.error("Can't release the connection");
            throw new ApplicationException("Can't release the connection", e);
        }
    }

    private void initConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connections.add(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can't init the connection pool");
            throw new ApplicationException("Can't init connection pool", e);
        }
    }
}
