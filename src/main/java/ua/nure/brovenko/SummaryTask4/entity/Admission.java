package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Admission object
 * @author Ivan Brovenko
 */
public class Admission extends Entity {

    /**
     * Entrant's email
     */
    private String email;

    /**
     * Entrant's final mark
     */
    private int finalMark;

    /**
     * Application quantity
     */
    private int applications;

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
     * Getter for final mark
     * @return final mark int
     */
    public int getFinalMark() {
        return finalMark;
    }

    /**
     * Setter for final mark
     * @param finalMark final mark int
     */
    public void setFinalMark(int finalMark) {
        this.finalMark = finalMark;
    }

    /**
     * Getter for applications quantity
     * @return applications quantity
     */
    public int getApplications() {
        return applications;
    }

    /**
     * Setter for applications quantity
     * @param applications applications quantity
     */
    public void setApplications(int applications) {
        this.applications = applications;
    }

}
