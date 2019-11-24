package com.bjsxt.sparkjavacode;

import java.io.Serializable;

public class MySort implements Comparable<MySort>,Serializable {

    private Integer first;
    private Integer second;

    @Override
    public int compareTo(MySort other) {
        if (this.first == other.first){
            return this.second- other.second;
        }
        return this.first - other.first;
    }

    public MySort(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "MySort{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
