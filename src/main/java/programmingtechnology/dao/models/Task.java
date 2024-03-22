package programmingtechnology.dao.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalTime;

public class Task {
    public SimpleIntegerProperty id;

    public SimpleStringProperty name;

    public SimpleLongProperty time;

    public SimpleBooleanProperty isFinished;
    public Task(int id, String name, long time, boolean isFinished){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleLongProperty(time);
        this.isFinished = new SimpleBooleanProperty(isFinished);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTime() {
        return LocalTime.ofNanoOfDay(time.get()).toString();
    }

    public long getNanoTime() {
        return time.get();
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public String getStatus() {
        return isFinished.get() ? "Выполнено" : "Не выполнено";
    }
    public boolean getIsFinished() {
        return isFinished.get();
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished.set(isFinished);
    }
}
