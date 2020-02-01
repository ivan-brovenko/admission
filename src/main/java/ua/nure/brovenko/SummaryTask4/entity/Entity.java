package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Entity abstract class
 * @author Ivan Brovenko
 */
public abstract class Entity {

    /**
     * Entity id
     */
    private int id;

    /**
     * Getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }
}
