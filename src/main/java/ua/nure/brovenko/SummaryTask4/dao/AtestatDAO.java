package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.entity.Atestat;
import ua.nure.brovenko.SummaryTask4.exception.ConnectionPoolException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Final certificate dao
 * @author Ivan Brovenko
 */
public class AtestatDAO implements DAO<Atestat>{

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding final certificate for entrant by entrant id
     */
    private final static String FIND_ATESTAT_FOR_USER = "SELECT atestat_number,average_mark,scan FROM atestat WHERE entrant_id=?";

    /**
     * Query for updating (inserting) certificate image
     */
    private final static String INSERT_SCAN = "UPDATE atestat SET scan=? WHERE entrant_id=?";

    /**
     * Query for finding all ceritficate scan names
     */
    private final static String FIND_ALL_SCAN_NAMES = "SELECT scan FROM atestat";

    /**
     * Method for finding final certificate for entrant by entrant id
     * @param userId entrant id
     * @return atestat object
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public Atestat getAtestatByEntrantId(int userId) throws DAOTechnicalException, DAOLogicalException {
        Atestat atestat = null;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(CONNECTION_POOL_EX);
        }
        connection = connectionPool.getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(FIND_ATESTAT_FOR_USER);
                int k=1;
                statement.setInt(k++,userId);
                resultSet = statement.executeQuery();
                connection.commit();

                if (resultSet.next()) {
                    atestat = createEntity(resultSet);
                } else {
                    throw new DAOLogicalException(ENTITY_WAS_NOT_FOUND);
                }

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            } finally {
                if (null != statement) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e);
                    }
                }
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                connectionPool.release(connection);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return atestat;
    }

    /**
     * Method for updating (inserting) certificate image
     * @param scan image name
     * @param entrantId entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void insertScan(String scan,int entrantId) throws DAOTechnicalException, DAOLogicalException {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(CONNECTION_POOL_EX);
        }
        connection = connectionPool.getConnection();

        if(scan!=null) {
            if (connection != null) {
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(INSERT_SCAN);
                    int k = 1;
                    statement.setString(k++, scan);
                    statement.setInt(k++, entrantId);
                    statement.executeUpdate();
                    connection.commit();

                } catch (SQLException e) {
                    try {
                        connection.rollback();
                    } catch (SQLException e1) {
                        throw new DAOTechnicalException(NO_ROLLBACK);
                    }
                    throw new DAOLogicalException(e);
                } finally {
                    if (null != statement) {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            throw new DAOTechnicalException(e);
                        }
                    }
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e.getMessage(), e);
                    }
                    connectionPool.release(connection);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        }else {
            throw new DAOLogicalException(NULL_PARAMETER);
        }
    }

    public List<String> findAllScans() throws DAOTechnicalException, DAOLogicalException {
        List<String> scans = new LinkedList<>();
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(CONNECTION_POOL_EX);
        }
        connection = connectionPool.getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(FIND_ALL_SCAN_NAMES);
                resultSet = statement.executeQuery();
                connection.commit();

                while (resultSet.next()) {
                    String scan=resultSet.getString("scan");
                    scans.add(scan);
                }

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            } finally {
                if (null != statement) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e);
                    }
                }
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                connectionPool.release(connection);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return scans;
    }
    /**
     * Method that creates atestat object from result set
     * @param set result set
     * @return atestat object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Atestat createEntity(ResultSet set) throws SQLException {
        Atestat atestat = new Atestat();
        atestat.setId(set.getInt("atestat_number"));
        atestat.setAvgMark(set.getInt("average_mark"));
        atestat.setImage(set.getString("scan"));

        return atestat;
    }
}
