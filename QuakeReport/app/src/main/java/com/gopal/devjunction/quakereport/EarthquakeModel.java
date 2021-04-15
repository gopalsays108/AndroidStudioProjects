package com.gopal.devjunction.quakereport;

public class EarthquakeModel {

    /**
     * Magnitude of the EarthQuake
     */
    double magn;

    /**
     * Place of the EarthQuake
     */
    String place;

    /**
     * date of the EarthQuake
     */
    String date;

    /**
     * direction of EarthQuake
     */
    String direction;

    /**
     * String Url
     */
    String url;

    /**
     * Constructs a new {@link EarthquakeModel} object.
     *
     * @param magn is the magnitude (size) of the earthquake
     * @param place is the location where the earthquake happened
     * @param date is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url is the website URL to find more details about the earthquake
     */
    public EarthquakeModel(double magn, String place, String date, String direction, String url) {
        this.magn = magn;
        this.place = place;
        this.date = date;
        this.direction = direction;
        this.url = url;
    }

    /**
     * @return the magnitude of the earthquake
     */
    public double getMagn() {
        return magn;
    }

    public void setMagn(double magn) {
        this.magn = magn;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
