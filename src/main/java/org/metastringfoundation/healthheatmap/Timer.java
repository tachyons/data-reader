package org.metastringfoundation.healthheatmap;

import java.util.Date;

public class Timer {
    private Date time = new Date();

    public void reset() {
        time = new Date();
    }

    public void result(String taskDescription) {
        Date newTime = new Date();
        System.out.println(taskDescription + " +" + (newTime.getTime() - time.getTime()));
        time = newTime;
    }
}
