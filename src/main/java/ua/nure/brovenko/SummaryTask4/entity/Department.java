package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Department object
 * @author Ivan Brovenko
 */
public class Department extends Entity {

    /**
     * Department name
     */
    private String name;

    /**
     * All places quantity
     */
    private int allPlaces;

    /**
     * Free places quantity
     */
    private int freePlaces;

    /**
     * University that the department belongs to
     */
    private String univerName;

    /**
     * Getter for department name
     * @return department name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for department name
     * @param name department name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for all places
     * @return all places quantity
     */
    public int getAllPlaces() {
        return allPlaces;
    }

    /**
     * Setter for all places
     * @param allPlaces all places quantity
     */
    public void setAllPlaces(int allPlaces) {
        this.allPlaces = allPlaces;
    }

    /**
     * Getter for free places
     * @return free places quantity
     */
    public int getFreePlaces() {
        return freePlaces;
    }

    /**
     * Setter for free places
     * @param freePlaces free places quantity
     */
    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    /**
     * Getter for university name
     * @return university name
     */
    public String getUniverName() {
        return univerName;
    }

    /**
     * Setter for university naem
     * @param univerName university name
     */
    public void setUniverName(String univerName) {
        this.univerName = univerName;
    }

    /**
     * Method equals
     * @param o object parameter
     * @return boolean value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (allPlaces != that.allPlaces) return false;
        if (freePlaces != that.freePlaces) return false;
        return name.equals(that.name);
    }

    /**
     * Method hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
