import java.util.Calendar;

/**
 * Created by GonçaloSilva on 26/02/2015.
 */
public class ListNode {



    private Calendar time;
    private long speed;
    private long height;
    private double pressure;
    private long temperature;
    private long pitch;

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public long getPitch() {
        return pitch;
    }

    public void setPitch(long pitch) {
        this.pitch = pitch;
    }
}
