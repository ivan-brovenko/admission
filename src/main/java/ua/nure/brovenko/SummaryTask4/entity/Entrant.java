package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Entrant object
 * @author Ivan Brovenko
 */
public class Entrant extends User {

    /**
     * Entrant's first name
     */
    private String firstName;

    /**
     * Entrant's last name
     */
    private String lastName;

    /**
     * Entrant's patronymic
     */
    private String patronymic;

    /**
     * Entrant's city
     */
    private String city;

    /**
     * Entrant's region
     */
    private String region;

    /**
     * A school that entrant graduated from
     */
    private String school;

    /**
     * Getter for first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for first name
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for last name
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for patronymic
     * @return patromymic
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Setter for patronymic
     * @param patronymic patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Getter for city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter for region
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Setter for region
     * @param region region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Getter for school
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * Setter for school
     * @param school school
     */
    public void setSchool(String school) {
        this.school = school;
    }

}
