package com.detect.amar.messagedetect.model;

/**
 * Created by SAM on 2015/8/28.
 */
public class CheckResponse {
    private String status_sim_1;
    private String status_sim_2;
    private int cycle_frequency;

    public String getStatus_sim_1() {
        return status_sim_1;
    }

    public void setStatus_sim_1(String status_sim_1) {
        this.status_sim_1 = status_sim_1;
    }

    public String getStatus_sim_2() {
        return status_sim_2;
    }

    public void setStatus_sim_2(String status_sim_2) {
        this.status_sim_2 = status_sim_2;
    }

    public int getCycle_frequency() {
        return cycle_frequency;
    }

    public void setCycle_frequency(int cycle_frequency) {
        this.cycle_frequency = cycle_frequency;
    }
}
