package com.example.liam.dartscoreboard;

public class Game {
    private int total1;
    private int total2;
    private int total3;

    public Game() {

    }
    public Game(int total1, int total2, int total3) {
        this.total1 = total1;
        this.total2 = total2;
        this.total3 = total3;

    }

    public int getTotal3() {
        return total3;
    }

    public void setTotal3(int total3) {
        this.total3 = total3;
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
