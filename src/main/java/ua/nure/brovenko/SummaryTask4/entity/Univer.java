package ua.nure.brovenko.SummaryTask4.entity;

/**
 * University object
 * @author Ivan Brovenko
 */
public class Univer extends Entity {

    /**
     * University name
     */
    private String name;

    /**
     * City name
     */
    private String cityName;

    /**
     * Getter for university name
     * @return university name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for university name
     * @param name university name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for city name
     * @return city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Setter for city name
     * @param cityName city name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
