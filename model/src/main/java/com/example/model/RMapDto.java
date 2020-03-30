package com.example.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RMapDto {

    private int i;
    private String map;

    public RMapDto(int i, String map) {
        this.i = i;
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
