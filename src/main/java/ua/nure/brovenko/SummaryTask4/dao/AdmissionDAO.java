package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.connection.ConnectionPool;
import ua.nure.brovenko.SummaryTask4.entity.Admission;
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
 * Admission dao
 * @author Ivan Brovenko
 */
public class AdmissionDAO implements DAO<Admission> {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding all admissions for entrants who submitted at least one application
     */
    private static final String FIND_ALL_ENTRANTS = "SELECT user.id,user.email,final_mark.final_avg_mark FROM user\n" +
            "  INNER JOIN final_mark ON user.id = final_mark.entrant_id WHERE user.role='reg_entrant'";

    /**
     * Query for counting applications for certain entrant
     */
    private static final String COUNT_APPLICATIONS = "SELECT DISTINCT count(entrant_id) count FROM department_entrant WHERE entrant_id=?";

    /**
     * Query for inserting entrant into final admission
     */
    private static final String ADD_TO_FINAL_ADMISSION = "INSERT INTO final_admission (entrant_id, department_id,price) VALUES\n" +
            "  (?,(SELECT DISTINCT  department_entrant.department_id FROM department_entrant WHERE  priority=? AND entrant_id=?),?)";


    private static final String FIND_BEST_5_ENTRANTS = "SELECT user.id,user.email,final_mark.final_avg_mark FROM user INNER JOIN final_mark ON user.id = final_mark.entrant_id\n" +
            "AND user.role = 'reg_entrant' OR user.role = 'admis' ORDER BY final_avg_mark DESC LIMIT 5";

    /**
     * Method that returns list of entrants with necessary info
     * @return list with admissions
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<Admission> findAllEntrants() throws DAOTechnicalException, DAOLogicalException {
        List<Admission> admissions = new ArrayList<>();
        Admission admission = null;
        ConnectionPool cp=null;
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet countSet = null;
        PreparedStatement count = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con=cp.getConnection();

        if(con!=null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(FIND_ALL_ENTRANTS);
                rs=ps.executeQuery();
                con.commit();

                while(rs.next()){
                    admission = createEntity(rs);
                    count = con.prepareStatement(COUNT_APPLICATIONS);
                    count.setInt(1,admission.getId());
                    countSet = count.executeQuery();
                    con.commit();

                    if(countSet.next()){
                        admission.setApplications(countSet.getInt("count"));
                    }
                    admissions.add(admission);
                }

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }

                throw new DAOLogicalException(e);
            }finally {
                if(ps != null && count != null){
                    try {
                        ps.close();
                        count.close();
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

        return admissions;
    }

    /**
     * Method for inserting entrants in final admission
     * @param entrantId entrant id
     * @param priority department priority
     * @param price education form
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void addToFinalAdmission(int entrantId,int priority,String price) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con=cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(ADD_TO_FINAL_ADMISSION);
                int k = 1;
                ps.setInt(k++, entrantId);
                ps.setInt(k++, priority);
                ps.setInt(k++, entrantId);
                ps.setString(k++,price);
                ps.executeUpdate();
                con.commit();
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

    public List<Admission> findBest5Entrants() throws DAOTechnicalException, DAOLogicalException {
        List<Admission> admissions = new ArrayList<>();
        Admission admission = null;
        ConnectionPool cp=null;
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con=cp.getConnection();

        if(con!=null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(FIND_BEST_5_ENTRANTS);
                rs=ps.executeQuery();
                con.commit();
                while (rs.next()){
                    admission = createEntity(rs);
                    admissions.add(admission);
                }

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }

                throw new DAOLogicalException(e);
            }finally {
                if(ps != null ){
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

        return admissions;
    }

    /**
     * Method that creates admission object from result set
     * @param set result set
     * @return admission object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Admission createEntity(ResultSet set) throws SQLException {
        Admission admission = new Admission();
        admission.setId(set.getInt("id"));
        admission.setEmail(set.getString("email"));
        admission.setFinalMark(set.getInt("final_avg_mark"));

        return admission;
    }
}
