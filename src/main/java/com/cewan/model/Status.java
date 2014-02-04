package com.cewan.model;

import static java.lang.String.format;

public class Status {

    private Integer volume;
    private String input;
    private Integer radioMenuLevel;
    private boolean powerOn;

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public Integer getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        double vol = ((double)volume)/10;
        return format("Volume: %s dB, Input: %s", vol, input);
    }

    public Integer getRadioMenuLevel() {
        return radioMenuLevel;
    }

    public void setRadioMenuLevel(Integer radioMenuLevel) {
        this.radioMenuLevel = radioMenuLevel;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public boolean isPowerOn() {
        return powerOn;
    }
}
