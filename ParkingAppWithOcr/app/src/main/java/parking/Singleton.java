package parking;

/**
 * Created by Ricardas on 2016-11-07.
 */

public class Singleton {
    private static Singleton instance = null;

    private String zone;
    private Integer hour;
    private Integer minute;
    private String licesePlate;

    private double latitude;
    private double longitude;

    private Singleton(){
    }

    public static Singleton getInstance(){
        if(instance == null)
        {
            instance = new Singleton();
        }
        return instance;
    }

    public void setZone(String zone){
        this.zone = zone;
    }
    public  void setTime(Integer hour, Integer minute){
        this.hour = hour;
        this.minute = minute;
    }
    public  void setLicesePlate(String value){
        licesePlate = value;
    }



    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public Integer getMinutes(){
        return this.minute;
    }
    public  String getTime(){
        if (hour != null && minute != null){
            if (minute < 10)
                return ("" + hour + ":0" + minute);
            else
                return  ("" + hour + ":" + minute);
        }
        return "";
    }
    public String getLicesePlate(){
        return this.licesePlate;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getZone(){
        return this.zone;
    }

    public Integer getHours(){
        return this.hour;
    }
}