package edu.utep.cs.cs4330.dumbphone;

import java.sql.Time;
import java.util.List;

public class LockSchedule {

    String name;
    boolean[] days= new boolean[7];//sunday is the first day bool,0
    Integer startHour,startMin,endHour,endMin;
    List<String> apps;

    public LockSchedule(String name, boolean[] days, Integer startHour, Integer startMin, Integer endHour, Integer endMin, List<String> apps) {
        this.name = name;
        this.days = days;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.apps = apps;
    }
}
