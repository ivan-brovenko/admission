package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.entity.Subject;
import ua.nure.brovenko.SummaryTask4.exception.ConnectionPoolException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Zno subject dao
 * @author Ivan Brovenko
 */
public class ZnoSubjectDAO implements DAO<Subject> {

    /**
     * Logger
     */
    private final Logger  logger = Logger.getRootLogger();

    /**
     * Query for finding zno subjects for an entrant
     */
    private final static String SELECT_ZNO_SUBJECT = "SELECT subject.name,zno_results.mark FROM zno_results INNER JOIN subject\n" +
            "ON zno_results.subject_id = subject.id WHERE zno_results.entrant_id=?";

    /**
     * Method for finding zno subjects for an entrant
     * @param userId entrant id
     * @return list of zno subjects
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<Subject> findZnoSubjectsByUserId(int userId) throws DAOTechnicalException, DAOLogicalException {
        List<Subject> subjects = new ArrayList<>();
        Subject subject = null;
        ConnectionPool connectionPool = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(CONNECTION_POOL_EX);
        }
         connection = connectionPool.getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(SELECT_ZNO_SUBJECT);
                int k=1;
                statement.setInt(k++,userId);
                resultSet = statement.executeQuery();
                connection.commit();

                while (resultSet.next()) {
                    subject = createEntity(resultSet);
                    subjects.add(subject);
                }

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(),e);
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
        return subjects;
    }

    /**
     * Method that creates a subject object from result set
     * @param set result set
     * @return subject object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Subject createEntity(ResultSet set) throws SQLException {
        Subject subject = new Subject();
        subject.setName(set.getString("name"));
        subject.setMark(set.getInt("mark"));

        return subject;
    }
}
