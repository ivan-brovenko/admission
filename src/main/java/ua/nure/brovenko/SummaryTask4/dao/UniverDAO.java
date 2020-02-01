package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.entity.Univer;
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
 * University dao
 * @author Ivan Brovenko
 */
public class UniverDAO implements DAO<Univer>{

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding all universities
     */
    private final static String FIND_ALL_UNIVERS = "SELECT id,name,city FROM univer";

    /**
     * Query for inserting uviver for a certain user
     */
    private final static String INSERT_UNIVER_FOR_USER = "INSERT INTO univer_entrant (entrant_id,univer_id,department_id) VALUES(?,?,?)";

    /**
     * Method for finding all universities
     * @return list of univers
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<Univer> findAllUnivers() throws DAOTechnicalException, DAOLogicalException {
        List<Univer> univers = new ArrayList<>();
        Univer univer = null;
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

        if(con != null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(FIND_ALL_UNIVERS);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    univer = createEntity(rs);
                    univers.add(univer);
                }
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(),e);
            }finally {
                if(ps != null){
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e);
                    }
                }
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
        return univers;
    }

    /**
     * Method for inserting uviver for a certain user
     * @param userId entrant id
     * @param univerId university id
     * @param departmentId department id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setUniverForUser(int userId,int univerId,int departmentId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con=cp.getConnection();

        if(con!=null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(INSERT_UNIVER_FOR_USER);
                int k = 1;
                ps.setInt(k++, userId);
                ps.setInt(k++, univerId);
                ps.setInt(k++, departmentId);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e.getMessage(),e);
                    }
                }
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Method that creates a university object from result set
     * @param set result set
     * @return university object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Univer createEntity(ResultSet set) throws SQLException {
        Univer univer = new Univer();
        univer.setId(set.getInt("id"));
        univer.setName(set.getString("name"));
        univer.setCityName(set.getString("city"));

        return univer;
    }
}
