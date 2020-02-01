package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.connection.ConnectionPool;
import ua.nure.brovenko.SummaryTask4.entity.Department;
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
 * Department dao
 * @author Ivan Brovenko
 */
public class DepartmentDAO implements DAO<Department> {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding all departments
     */
    private final static String FIND_ALL_DEPARTMENTS = "SELECT id,name,places,free_places FROM department";

    /**
     * Query for inserting final certificate with certain entrant id
     */
    private final static String INSERT_ATESTAT_NUMBER = "INSERT INTO atestat (atestat_number,entrant_id) VALUES(?,?)";

    /**
     * Query for inserting mark for atestat id and entrant id
     */
    private final static String INSERT_MARK = "INSERT INTO marks (atestat_id,subject_id,mark) VALUES(?,?,?)";

    /**
     * Query for inserting department into admission
     */
    private final static String INSERT_DEPARTMENT_ENTRANT="INSERT INTO department_entrant (department_id,entrant_id,priority,univer_id) VALUES (?,?,?,?)";

    /**
     * Query for updating average mark
     */
    private final static String UPDATE_AVERAGE_MARK = "UPDATE atestat SET average_mark= (SELECT avg(mark)FROM marks WHERE atestat_id=?) WHERE atestat_number=?";

    /**
     * Query for inserting zno results
     */
    private final static String INSERT_ZNO_RESULTS = "INSERT INTO zno_results (entrant_id,subject_id,mark) VALUES (?,?,?)";

    /**
     * Query for inserting final mark
     */
    private final static String INSERT_FINAL_MARK = "INSERT INTO final_mark (entrant_id, final_avg_mark) VALUES (?,(select (select sum(mark) from zno_results where entrant_id=?)+(SELECT average_mark FROM atestat" +
            " WHERE atestat_number=(SELECT atestat_number FROM atestat WHERE entrant_id=?))))";

    /**
     * Query for deleting data for entrant id in case something will go wrong
     */
    private final static String DELETE_ENTRANT_ID_DATA = "DELETE atestat,zno_results,department_entrant,final_mark FROM zno_results INNER JOIN department_entrant\n" +
            "ON zno_results.entrant_id=department_entrant.entrant_id INNER JOIN final_mark ON\n" +
            "department_entrant.entrant_id=final_mark.entrant_id INNER JOIN atestat ON final_mark.entrant_id=atestat.entrant_id AND final_mark.entrant_id=?";

    /**
     * Query for adding a department
     */
    private final static String ADD_DEPARTMENT = "INSERT INTO department (id, name, places, free_places) VALUES (?,?,?,?)";

    /**
     * Query for deleting department by id
     */
    private final static String DELETE_DEPARTMENT_BY_ID = "DELETE FROM department WHERE id=?";

    /**
     * Query for updating a department by id
     */
    private final static String UPDATE_DEPARTMENT_BY_ID = "UPDATE department SET name=?,places=?,free_places=? WHERE id=?";

    /**
     * Query for finding department with certain priority for an entrant
     */
    private final static String GET_DEPARTMENT_WITH_PRIORITY = "SELECT department.id,department.name,department.places,department.free_places FROM department\n" +
            "INNER JOIN department_entrant ON department.id = department_entrant.department_id \n" +
            "WHERE department_entrant.priority=? AND department_entrant.entrant_id=?";

    /**
     * Query for updating free places
     */
    private final static String UPDATE_FREE_PLACES = "UPDATE department SET free_places=?,places=? WHERE id=?";

    /**
     * Query for updating all places for a department
     */
    private final static String UPDATE_PLACES = "UPDATE department SET places=? WHERE id=?";

    /**
     * Query for getting a priority for entrant
     */
    private final static String GET_PRIORITY = "SELECT priority FROM department_entrant WHERE entrant_id=?";

    /**
     * Method for finding all departments
     * @return list of departments
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */

    public List<Department> findAllDepartments() throws DAOTechnicalException, DAOLogicalException {
        Department department = null;
        List<Department> departments = new LinkedList<>();
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps=null;
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
                ps = con.prepareStatement(FIND_ALL_DEPARTMENTS);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    department = createEntity(rs);
                    departments.add(department);
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

        return departments;
    }

    /**
     * Method for inserting final certificate with certain entrant id
     * @param atestatNumber atestat id number
     * @param entrantId entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setAtestatNumber(int atestatNumber,int entrantId) throws DAOTechnicalException, DAOLogicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(INSERT_ATESTAT_NUMBER);
            int k=1;
            ps.setInt(k++,atestatNumber);
            ps.setInt(k++,entrantId);
            ps.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new DAOTechnicalException(NO_ROLLBACK);
            }
            throw new DAOLogicalException(e);
        }finally {

            if(ps != null) {
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
    }

    /**
     * Method for inserting mark for atestat id and entrant id
     * @param atestatId atestat id number
     * @param subjectId subject id
     * @param mark mark
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setMarks(int atestatId,int subjectId,int mark) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(INSERT_MARK);
                int k = 1;
                ps.setInt(k++, atestatId);
                ps.setInt(k++, subjectId);
                ps.setInt(k++, mark);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(e.getMessage(), e);
                }
                throw new DAOLogicalException(e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e.getMessage(), e);
                    }
                }
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(), e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Method for inserting department into admission
     * @param departmentId department id
     * @param entrantId entrant id
     * @param priority priority
     * @param univerId university id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setDepartmentForEntrant(int departmentId,int entrantId,int priority,int univerId) throws DAOLogicalException, DAOTechnicalException {
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
                ps = con.prepareStatement(INSERT_DEPARTMENT_ENTRANT);
                int k = 1;
                ps.setInt(k++, departmentId);
                ps.setInt(k++, entrantId);
                ps.setInt(k++, priority);
                ps.setInt(k++, univerId);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(e.getMessage(), e);
                }
                throw new DAOLogicalException(e);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        throw new DAOTechnicalException(e.getMessage(), e);
                    }
                }
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(), e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Method for updating average mark
     * @param atestatId atestat id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setAverageMark(int atestatId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(UPDATE_AVERAGE_MARK);
                int k = 1;
                ps.setInt(k++, atestatId);
                ps.setInt(k++, atestatId);
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
     * Method for inserting zno results
     * @param entrantId entrant id
     * @param subjectId subject id
     * @param mark mark
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setZnoResultrForEntrant(int entrantId,int subjectId,int mark) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(INSERT_ZNO_RESULTS);
                int k = 1;
                ps.setInt(k++, entrantId);
                ps.setInt(k++, subjectId);
                ps.setInt(k++, mark);
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
                        throw new DAOTechnicalException(e);
                    }
                }
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(), e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Query for inserting final mark
     * @param entrantId entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setFinalMark(int entrantId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(INSERT_FINAL_MARK);
                int k = 1;
                ps.setInt(k++, entrantId);
                ps.setInt(k++, entrantId);
                ps.setInt(k++, entrantId);
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

    /**
     * Method for deleting data for entrant id in case something will go wrong
     * @param entrantId entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void eraseEntrantSubjectData(int entrantId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con=cp.getConnection();

        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(DELETE_ENTRANT_ID_DATA);
                int k = 1;
                ps.setInt(k++, entrantId);
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

    /**
     * Method for adding a department
     * @param id department id
     * @param name department name
     * @param places places
     * @param freePlaces free places
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void addDepartment(int id,String name,int places,int freePlaces) throws DAOTechnicalException, DAOLogicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(ADD_DEPARTMENT);
                int k=1;
                ps.setInt(k++,id);
                ps.setString(k++,name);
                ps.setInt(k++,places);
                ps.setInt(k++,freePlaces);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
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
     * Method for deleting department by id
     * @param id department id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void deleteDepartmentById(int id) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(DELETE_DEPARTMENT_BY_ID);
                int k=1;
                ps.setInt(k++,id);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
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
     * Method for updating a department by id
     * @param id department id
     * @param name department name
     * @param places plces
     * @param freePlaces free places
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void updateDepartmentById(int id,String name,int places,int freePlaces) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null){
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(UPDATE_DEPARTMENT_BY_ID);
                int k=1;
                ps.setString(k++,name);
                ps.setInt(k++,places);
                ps.setInt(k++,freePlaces);
                ps.setInt(k++,id);
                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
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
     * Method for finding department
     * with certain priority for an entrant
     * @param priotity priority
     * @param entrantId entrant id
     * @return department object
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public Department getDepartmentWithPriorityForEntrant(int priotity,int entrantId) throws DAOTechnicalException, DAOLogicalException {
        Department department = null;
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(GET_DEPARTMENT_WITH_PRIORITY);
                int k=1;
                ps.setInt(k++, priotity);
                ps.setInt(k++, entrantId);
                resultSet = ps.executeQuery();
                con.commit();

                if(resultSet.next()){
                    department = createEntity(resultSet);
                } else{
                    throw new DAOLogicalException(ENTITY_WAS_NOT_FOUND);
                }

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(),e);
            } finally {
                if (ps!=null) {
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
        } else{
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return department;
    }

    /**
     * Query for updating free places
     * @param newFreePlaces new free places quantity
     * @param allPlaces all places quatity
     * @param departmentId department id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void updateFreePlaces(int newFreePlaces,int allPlaces,int departmentId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(UPDATE_FREE_PLACES);
                int k=1;
                ps.setInt(k++, newFreePlaces);
                ps.setInt(k++,allPlaces);
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
                        throw new DAOLogicalException(e);
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
     * Query for updating all places
     * @param allPlaces all places quantity
     * @param departmentId department id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void updateAllPlaces(int allPlaces,int departmentId) throws DAOLogicalException, DAOTechnicalException {
        ConnectionPool cp = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            cp = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            logger.error(CONNECTION_POOL_EX);
        }
        con = cp.getConnection();

        if(con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement(UPDATE_PLACES);
                int k=1;
                ps.setInt(k++, allPlaces);
                ps.setInt(k++,departmentId);
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
                        throw new DAOLogicalException(e);
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
     * Method for getting a priorities for entrant
     * @param entrantId entrant id
     * @return list of priorities
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<Integer> getAllPrioritiesForEntrant(int entrantId) throws DAOTechnicalException, DAOLogicalException {
        List<Integer> priorities = new LinkedList<>();
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
                ps = con.prepareStatement(GET_PRIORITY);
                ps.setInt(1,entrantId);
                rs = ps.executeQuery();
                con.commit();
                while (rs.next()){
                    int priority=rs.getInt("priority");
                    priorities.add(priority);
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

        return priorities;
    }

    /**
     * Method that creates a department object from result set
     * @param set result set
     * @return departmetn object
     * @throws SQLException if something goes wrong
     */
    @Override
    public Department createEntity(ResultSet set) throws SQLException {
        Department department = new Department();
        department.setId(set.getInt("id"));
        department.setName(set.getString("name"));
        department.setAllPlaces(set.getInt("places"));
        department.setFreePlaces(set.getInt("free_places"));

        return department;
    }
}
