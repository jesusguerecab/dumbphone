package edu.utep.cs.cs4330.dumbphone;

import java.util.List;

public class ScheduleClass {
    String startTime;
    String endTime;
    List<App> appsList;

    ScheduleClass(String st, String et, List<App> apps){
        this.startTime=st;
        this.endTime=et;
        this.appsList=apps;
    }

    void editStartTime(String st){
        this.startTime=st;
    }

    void  editEndTime(String et){
        this.endTime=et;
    }

    void  editAppsList(List<App> apps){
        this.appsList=apps;
    }
}
