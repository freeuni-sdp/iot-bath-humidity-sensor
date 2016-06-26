package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Giorgi on 26-Jun-16.
 */
public class Humidities {

    @XmlElement
    private int num_returned;

    @XmlElement
    private List<Humidity> measurements;

    public int getNum_returned(){
        return num_returned;
    }

    public void setNum_returned(int num_returned){
        this.num_returned = num_returned;
    }

    public void setMeasurements(List<Humidity> measurements){
        this.measurements = measurements;
    }

    public List<Humidity> getMeasurements(){
        return measurements;
    }
}
