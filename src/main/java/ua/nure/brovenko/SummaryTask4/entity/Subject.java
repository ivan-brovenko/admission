package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Subject object
 * @author Ivan Brovenko
 */
public class Subject extends Entity {

    /**
     * Subject name
     */
    private String name;

    /**
     * Mark
     */
    private int mark;

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for mark
     * @return mark
     */
    public int getMark() {
        return mark;
    }

    /**
     * Setter for mark
     * @param mark mark
     */
    public void setMark(int mark) {
        this.mark = mark;
    }
}
