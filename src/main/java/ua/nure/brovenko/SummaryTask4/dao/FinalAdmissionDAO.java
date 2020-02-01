package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.connection.ConnectionPool;
import ua.nure.brovenko.SummaryTask4.entity.FinalAdmission;
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
 * Final admission dao
 * @author Ivan Brovenko
 */
public class FinalAdmissionDAO implements DAO<FinalAdmission> {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding all final admissions
     */
    private final static String FIND_FINAL_ADMISSION = "SELECT final_admission.entrant_id,user.email,department.name dname,univer.name uname,department_entrant.priority,final_admission.price\n" +
            "FROM final_admission INNER JOIN user ON user.id=final_admission.entrant_id\n" +
            "INNER JOIN department ON final_admission.department_id = department.id\n" +
            "INNER JOIN department_entrant ON final_admission.entrant_id=department_entrant.entrant_id AND\n" +
            "final_admission.department_id=department_entrant.department_id INNER JOIN univer\n" +
            "ON department_entrant.univer_id = univer.id";

    /**
     * Method for finding all final admissions
     * @return list of final admissions
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<FinalAdmission> findAllFinalAdmissions() throws DAOTechnicalException, DAOLogicalException {
        FinalAdmission finalAdmission = null;
        List<FinalAdmission> finalAdmissions = new LinkedList<>();
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
                ps = con.prepareStatement(FIND_FINAL_ADMISSION);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    finalAdmission=createEntity(rs);
                    finalAdmissions.add(finalAdmission);
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

        return finalAdmissions;
    }

    /**
     * Method that creates a final admission object from result set
     * @param set result set
     * @return final admission object
     * @throws SQLException if something goes wrong
     */
    @Override
    public FinalAdmission createEntity(ResultSet set) throws SQLException {
        FinalAdmission finalAdmission = new FinalAdmission();
        finalAdmission.setEntrantId(set.getInt("entrant_id"));
        finalAdmission.setEmail(set.getString("email"));
        finalAdmission.setDepartmentName(set.getString("dname"));
        finalAdmission.setUneiveName(set.getString("uname"));
        finalAdmission.setPriority(set.getInt("priority"));
        finalAdmission.setPrice(set.getString("price"));

        return finalAdmission;
    }
}
