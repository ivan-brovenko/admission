package ua.nure.brovenko.SummaryTask4.dao;

import ua.nure.brovenko.SummaryTask4.entity.Application;
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
 * Application dao
 * @author Ivan Brovenko
 */
public class ApplicationDAO implements DAO<Application> {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query that finds all the departments user chose
     */
    private final static String FIND_DEPARTMENTS_FOR_USER = "SELECT DISTINCT department_entrant.id,univer.name univer_name," +
            "department.name department_name,univer.city univer_city,department_entrant.priority\n" +
            "FROM department_entrant INNER JOIN department ON department_entrant.department_id = department.id\n" +
            "INNER JOIN univer ON department_entrant.univer_id = univer.id WHERE department_entrant.entrant_id=?";

    /**
     * Query that returns single final application
     * only for entrants with role admis
     */
    private final static String FIND_FINAL_APPLICATION = "SELECT final_admission.department_id id," +
            "department.name department_name, univer.name univer_name,univer.city univer_city," +
            "department_entrant.priority\n" +
            "FROM department_entrant INNER JOIN final_admission\n" +
            "ON final_admission.department_id=department_entrant.department_id " +
            " AND final_admission.entrant_id=department_entrant.entrant_id\n" +
            "INNER JOIN department ON department_entrant.department_id = department.id\n" +
            "INNER JOIN univer ON department_entrant.univer_id = univer.id\n" +
            "WHERE final_admission.entrant_id=?;";

    /**
     * Query for getting education fom for entrant
     */
    private final static String GET_EDUCATION_FORM = "SELECT final_admission.price FROM final_admission WHERE entrant_id=?";

    /**
     * Method that returns applications with all the departments entrant chose
     * @param userId entrant id
     * @return list of applications with chosen departments
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<Application> findDepartmentsForUser(int userId) throws DAOTechnicalException, DAOLogicalException {
        List<Application> applications = new ArrayList<>();
        Application application = null;
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con!=null) {

            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(FIND_DEPARTMENTS_FOR_USER);
                ps.setInt(1, userId);
                rs = ps.executeQuery();
                con.commit();

                while (rs.next()) {
                    application = createEntity(rs);
                    applications.add(application);
                }

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(),e);

            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e);
                    }
                }

                try {
                    con.setAutoCommit(false);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                cp.release(con);
            }

        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return applications;
    }

    /**
     * Method that returns single final application
     * only for users with role admis
     * @param entrantId entrant id
     * @return final application
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public Application findFinalApplication(int entrantId) throws DAOTechnicalException, DAOLogicalException {
        Application application = null;
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
                statement = connection.prepareStatement(FIND_FINAL_APPLICATION);
                statement.setInt(1,entrantId);
                resultSet = statement.executeQuery();
                connection.commit();

                if (resultSet.next()) {
                    application = createEntity(resultSet);
                } else {
                    throw new DAOLogicalException(ENTITY_WAS_NOT_FOUND);
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

        return application;
    }

    /**
     * Method for getting education fom for entrant
     * @param entrantId entrant id
     * @return education form
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public String getEducationForm(int entrantId) throws DAOTechnicalException, DAOLogicalException {
        String educationForm = null;
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
                statement = connection.prepareStatement(GET_EDUCATION_FORM);
                statement.setInt(1,entrantId);
                resultSet = statement.executeQuery();
                connection.commit();

                if (resultSet.next()) {
                    educationForm = resultSet.getString("price");
                } else {
                    throw new DAOLogicalException(ENTITY_WAS_NOT_FOUND);
                }

            } catch (SQLException e) {
                throw new DAOTechnicalException(e);
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
                    throw new DAOTechnicalException(e.getMessage());
                }
                connectionPool.release(connection);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return educationForm;
    }

    /**
     * Method that creates application object from result set
     * @param set result set
     * @return application object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Application createEntity(ResultSet set) throws SQLException {
        Application application = new Application();
        application.setId(set.getInt("id"));
        application.setUniverName(set.getString("univer_name"));
        application.setDepartmentName(set.getString("department_name"));
        application.setUniverCity(set.getString("univer_city"));
        application.setPriority(set.getInt("priority"));

        return application;
    }
}
