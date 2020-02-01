package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Application object
 * @author Ivan Brovenko
 */
public class Application extends Entity {

    /**
     * University name
     */
    private String univerName;

    /**
     * Department name
     */
    private String departmentName;

    /**
     * Univer city
     */
    private String univerCity;

    /**
     * Priority
     */
    private int priority;

    /**
     * Education form
     */
    private String price;

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
     * Getter for university name
     * @return university name string
     */
    public String getUniverName() {
        return univerName;
    }

    /**
     * Setter for university name
     * @param univerName university naem string
     */
    public void setUniverName(String univerName) {
        this.univerName = univerName;
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

    /**
     * Getter for priority
     * @return univer city name
     */
    public String getUniverCity() {
        return univerCity;
    }

    /**
     * Setter for priority
     * @param univerCity univer city name
     */
    public void setUniverCity(String univerCity) {
        this.univerCity = univerCity;
    }
}
