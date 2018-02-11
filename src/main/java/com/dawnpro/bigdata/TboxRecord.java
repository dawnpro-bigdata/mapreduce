package com.dawnpro.bigdata;

import java.sql.Timestamp;
import java.util.Date;

public class TboxRecord implements Comparable{
    private String VIN;
    private Timestamp time;
    private long longitude;
    private long latitude;
    private int soc;
    private long mileage;
    private int hour;

    public TboxRecord(String VIN, Timestamp time, long longitude, long latitude, int soc, long mileage, int hour) {
        this.VIN = VIN;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.soc = soc;
        this.mileage = mileage;
        this.hour = hour;
    }

    public TboxRecord(String value) {
        String[] tokens = value.split("\\t");
        this.VIN = tokens[0];
        this.time = Timestamp.valueOf(tokens[1]);
        this.longitude = Integer.valueOf(tokens[2]);
        this.latitude = Integer.valueOf(tokens[3]);
        this.soc = Integer.valueOf(tokens[4]);
        this.mileage = Long.valueOf(tokens[5]);
        this.hour = Integer.valueOf(tokens[6]);
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "TboxRecord{" +
                "VIN='" + VIN + '\'' +
                ", time=" + time +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", soc=" + soc +
                ", mileage=" + mileage +
                ", hour=" + hour +
                '}';
    }

    public int compareTo(Object o) {
        TboxRecord otherRecord = (TboxRecord)o;
        long otherTime = otherRecord.time.getTime();
        if(this.time.getTime() > otherTime) {
            return -1;
        } else if (this.time.getTime() == otherTime) {
           return 0;
        } else {
           return 1;
        }
    }
}
