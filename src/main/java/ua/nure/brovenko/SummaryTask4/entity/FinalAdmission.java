package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Final admission object
 * @author Ivan Brovenko
 */
public class FinalAdmission extends Entity {

    /**
     * Entrant id
     */
    private int entrantId;

    /**
     * Entrant email
     */
    private String email;

    /**
     * University name
     */
    private String uneiveName;

    /**
     * Department name
     */
    private String departmentName;

    /**
     * Education form
     */
    private String price;

    /**
     * Priority
     */
    private int priority;

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
     * Getter for entrant id
     * @return entrant id
     */
    public int getEntrantId() {
        return entrantId;
    }

    /**
     * Setter for entrant id
     * @param entrantId entrant id
     */
    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    /**
     * Getter for university name
     * @return university name string
     */
    public String getUneiveName() {
        return uneiveName;
    }

    /**
     * Setter for university name
     * @param uneiveName university naem string
     */
    public void setUneiveName(String uneiveName) {
        this.uneiveName = uneiveName;
    }

    /**
     * Getter for departmetn name
     * @return department name string
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Setter for department name
     * @param departmentName department naem string
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Getter for price
     * @return price string
     */
    public String getPrice() {
        return price;
    }

    /**
     * Setter for price
     * @param price price string
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Getter for priority
     * @return priority int
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Setter for priority
     * @param priority priority int
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
