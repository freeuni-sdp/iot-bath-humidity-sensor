package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Humidity {

    @XmlElement
    private double humidity;

    @XmlElement
    private String measurement_time;

    public double getHumidity(){
        return humidity;
    }

    public void setHumidity(double humidity){
        this.humidity = humidity;
    }

    public String getMeasurement_time(){
        return measurement_time;
    }

    public void setMeasurement_time(String measurement_time){
        this.measurement_time = measurement_time;
    }
}
