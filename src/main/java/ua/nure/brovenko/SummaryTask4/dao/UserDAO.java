package ua.nure.brovenko.SummaryTask4.dao;


import ua.nure.brovenko.SummaryTask4.entity.Entrant;
import ua.nure.brovenko.SummaryTask4.entity.User;
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
 * User dao
 * @author Ivan Brovenko
 */
public class UserDAO implements DAO<User> {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Query for finding user by login and password
     */
    private static final String FIND_BY_LOGIN_PASSWORD = "SELECT user.id, user.email, user.password, user.role " +
            "FROM user  WHERE user.email = ? AND user.password = ?";

    /**
     * Query for inserting new entrant
     */
    private static final String INSERT_ENTRANT = "INSERT INTO user VALUES(default,?,?,DEFAULT ,?,?,?,?,?,?)";

    /**
     * Query for changing entrant role to reg_entrant
     */
    private static final String UPDATE_ROLE = "UPDATE user SET role='reg_entrant' WHERE id=?";

    /**
     * Query for changing entrant role to admis
     */
    private static final String SET_ADMISSION_ROLE = "UPDATE user SET role='admis' WHERE id=?";

    /**
     * Query for blocking entrant
     */
    private static final String BLOCK_USER = "UPDATE user SET role='blocked' WHERE id=?";

    /**
     * Query for finding usrt by id
     */
    private static final String FIND_USER_INFO_BY_ID = "SELECT user.first_name,user.last_name,user.patronymic,user.city," +
            "user.region,user.school FROM user WHERE user.id=?";

    /**
     * Query for finding all users
     */
    private static final String FIND_ALL_USERS = "SELECT id,email,password,role FROM user";

    /**
     * Query for finding user by id
     */
    private static final String FIND_USER_BY_ID = "SELECT id,email,password,role FROM user WHERE id=?";

    /**
     * Query for finding blocked users
     */
    private static final String FIND_BLOCKED_USERS = "SELECT id,email,password,role FROM user" +
            " WHERE role='blocked'";

    /**
     * Query for unblocking users
     */
    private static final String UNBLOCK_USER = "UPDATE user SET role='reg_entrant' WHERE id=?";

    /**
     * Query for finding entrants' emails with any role
     */
    private static final String FIND_ENTRANTS_ANY_ROLE = "SELECT user.email FROM user WHERE user.role='entrant' OR user.role='reg_entrant' OR user.role='admis'";

    /**
     * Query for updating entrant password in casw he forgot it
     */
    private static final String UPDATE_ENTRANT_PASSWORD_BY_EMAIL = "UPDATE user SET user.password=? WHERE user.email=?";

    /**
     * Method for finding user by login and password
     * @param login user's login
     * @param password user's password
     * @return user
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public User findByLoginAndPassword(String login, String password) throws DAOLogicalException, DAOTechnicalException{
        User user = null;
        ConnectionPool connectionPool = null;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;

        if(login != null && password != null){
            try{
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e){
                logger.error(CONNECTION_POOL_EX);
            }
            connection = connectionPool.getConnection();

            if (connection != null) {
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(FIND_BY_LOGIN_PASSWORD);
                    statement.setString(1, login);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    connection.commit();

                    if(resultSet.next()){
                        user = createEntity(resultSet);
                    } else{
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
            } else{
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        }
        return user;
    }

    /**
     * Method for inserting new entrant
     * @param entrant entrant object
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void insertEntrant(Entrant entrant) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(INSERT_ENTRANT);
                int k = 1;
                ps.setString(k++, entrant.getEmail());
                ps.setString(k++, entrant.getPassword());
                ps.setString(k++, entrant.getFirstName());
                ps.setString(k++, entrant.getLastName());
                ps.setString(k++, entrant.getPatronymic());
                ps.setString(k++, entrant.getCity());
                ps.setString(k++, entrant.getRegion());
                ps.setString(k++, entrant.getSchool());
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
                        throw new DAOTechnicalException(e.getMessage());
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
     * Method for changing entrant role to reg_entrant
     * @param id entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void updateEntrantRole(int id) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(UPDATE_ROLE);
                int k = 1;
                ps.setInt(k++, id);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(), e);

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
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                cp.release(con);
            }
        }else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Method for changing entrant role to admis
     * @param id entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void setAdmissionRole(int id) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(SET_ADMISSION_ROLE);
                ps.setInt(1, id);
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

    /**
     * Method for blocking entrant
     * @param id entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void blockEntrant(int id) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(BLOCK_USER);
                ps.setInt(1, id);
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

    /**
     * Method for finding usrt by id
     * @param entrantId entrant id
     * @return entrant object
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public Entrant findInfoForEntrantById(int entrantId) throws DAOTechnicalException, DAOLogicalException {
        Entrant entrant = null;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            logger.error(NO_CONNECTION);
        }
        connection = connectionPool.getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(FIND_USER_INFO_BY_ID);
                int k=1;
                statement.setInt(k++,entrantId);
                resultSet = statement.executeQuery();
                connection.commit();

                if(resultSet.next()){
                    entrant = createEntrant(resultSet);
                } else{
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
                        throw new DAOTechnicalException(e.getMessage(),e);
                    }
                }
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e.getMessage(),e);
                }
                connectionPool.release(connection);
            }
        } else{
            throw new DAOTechnicalException(NO_CONNECTION);
        }

        return entrant;
    }

    /**
     * Method for finding all users
     * @return list of all users
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<User> findAllUsers() throws DAOTechnicalException, DAOLogicalException {
        List<User> users = new LinkedList<>();
        User user = null;
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
                ps = con.prepareStatement(FIND_ALL_USERS);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    user = createEntity(rs);
                    users.add(user);
                }

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    throw new DAOTechnicalException(NO_ROLLBACK);
                }
                throw new DAOLogicalException(e.getMessage(),e);
            }finally {
                if(ps!=null){
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
        return users;
    }

    /**
     * Method for finding user by id
     * @param entrantId entrant id
     * @return user object
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public User findUserById(int entrantId) throws DAOTechnicalException, DAOLogicalException {
        User user = null;
        ConnectionPool connectionPool = null;
        PreparedStatement statement = null;
        Connection connection = null;
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
                    statement = connection.prepareStatement(FIND_USER_BY_ID);
                    statement.setInt(1,entrantId);
                    resultSet = statement.executeQuery();
                    connection.commit();

                    if(resultSet.next()){
                        user = createEntity(resultSet);
                    } else{
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
            } else{
                throw new DAOTechnicalException(NO_CONNECTION);
            }

        return user;
    }

    /**
     * Method for showing blocked users
     * @return list of blokced users
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<User> findBlockedUsers() throws DAOTechnicalException, DAOLogicalException {
        List<User> users = new LinkedList<>();
        User user = null;
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
                ps = con.prepareStatement(FIND_BLOCKED_USERS);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    user = createEntity(rs);
                    users.add(user);
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
        return users;
    }

    /**
     * Method for unblocking users
     * @param entrantId entrant id
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void unblockUserById(int entrantId) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(UNBLOCK_USER);
                ps.setInt(1, entrantId);
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

    /**
     * Method for finding entrants' emails with any role
     * @return list of users' emails
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public List<String> findEntrantsEmailsAnyRole() throws DAOTechnicalException, DAOLogicalException {
        List<String> emails = new LinkedList<>();
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
                ps = con.prepareStatement(FIND_ENTRANTS_ANY_ROLE);
                rs = ps.executeQuery();
                con.commit();

                while(rs.next()){
                    String email = rs.getString("email");
                    emails.add(email);
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
        return emails;
    }

    /**
     * Method for updating entrant password in casw he forgot it
     * @param password password
     * @param email emaid
     * @throws DAOTechnicalException if something goes wrong with technical part
     * @throws DAOLogicalException if something goes wrong with data
     */
    public void updateEntrantPassword(String password,String email) throws DAOTechnicalException, DAOLogicalException {
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
                ps = con.prepareStatement(UPDATE_ENTRANT_PASSWORD_BY_EMAIL);
                int k=1;
                ps.setString(k++, password);
                ps.setString(k++,email);
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

    /**
     * Method that creates a user object from result set
     * @param set result set
     * @return user object
     * @throws SQLException if something goes wrong
     */
    @Override
    public User createEntity(ResultSet set) throws SQLException {
        User user = new User();
        user.setId(set.getInt("id"));
        user.setEmail(set.getString("email"));
        user.setPassword(set.getString("password"));
        user.setRole(set.getString("role"));

        return user;
    }

    /**
     * Method that creates an entrant object from result set
     * @param set result set
     * @return entrant object
     * @throws SQLException if something goes wrong
     */
    public Entrant createEntrant(ResultSet set) throws SQLException {
        Entrant entrant = new Entrant();
        entrant.setFirstName(set.getString("first_name"));
        entrant.setLastName(set.getString("last_name"));
        entrant.setPatronymic(set.getString("patronymic"));
        entrant.setCity(set.getString("city"));
        entrant.setRegion(set.getString("region"));
        entrant.setSchool(set.getString("school"));

        return entrant;
    }

}
