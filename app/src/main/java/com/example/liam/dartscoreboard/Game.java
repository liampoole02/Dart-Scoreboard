package com.example.liam.dartscoreboard;

public class Game {
    private int total1;
    private int total2;

    public Game() {

    }
    public Game(int total1, int total2) {
        this.total1 = total1;
        this.total2 = total2;
    }


    public int getTotal1() {
        return total1;
    }

    public void setTotal1(int total1) {
        this.total1 = total1;
    }

    public int getTotal2() {
        return total2;
    }

    public void setTotal2(int total2) {
        this.total2 = total2;
    }
}
