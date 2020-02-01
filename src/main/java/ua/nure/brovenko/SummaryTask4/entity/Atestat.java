package ua.nure.brovenko.SummaryTask4.entity;

/**
 * Atestat object
 * @author Ivan Brovenko
 */
public class Atestat extends Entity{

    /**
     * Average mark
     */
    private int avgMark;

    private String image;

    /**
     * Getter for average mark
     * @return average mark int
     */
    public int getAvgMark() {
        return avgMark;
    }

    /**
     * Setter for average mark
     * @param avgMark average mark
     */
    public void setAvgMark(int avgMark) {
        this.avgMark = avgMark;
    }

    /**
     * Getter for atestat scan
     * @return atestat scan name string
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for atestat name
     * @param image atestat scaner name
     */
    public void setImage(String image) {
        this.image = image;
    }
}
