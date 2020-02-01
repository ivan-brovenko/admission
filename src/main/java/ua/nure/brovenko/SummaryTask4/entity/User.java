package ua.nure.brovenko.SummaryTask4.entity;

/**
 * User object
 * @author Ivan Brovenko
 */
public class User extends Entity{

    /**
     * User's email
     */
    private String email;

    /**
     * User's password
     */
    private String password;

    /**
     * User's role
     */
    private String role;

    /**
     * Getter for email
     * @return email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email email string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for role
     * @return user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role
     * @param role user's role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Method equals
     * @param o object parameter
     * @return boolean value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    /**
     * Method hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    /**
     * Method toString
     * @return string
     */
    @Override
    public String toString() {
        return "User:"+email;
    }
}
