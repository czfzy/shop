package com.example.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderCount implements Serializable {
    private Double num;
    private String time;
    private String name;
    private Integer value;
    private List<String> column;

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<String> getColumn() {
        return column;
    }

    public void setColumn(List<String> column) {
        this.column = column;
    }
}
