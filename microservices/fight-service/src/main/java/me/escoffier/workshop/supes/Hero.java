package me.escoffier.workshop.supes;

import java.util.Random;


public class Hero {

    public String name;
    public String otherName;
    public int level;
    public String picture;
    public String powers;

    @Override
    public String toString() {
        return "Hero{" +
                ", name='" + name + '\'' +
                ", otherName='" + otherName + '\'' +
                ", level=" + level +
                ", picture='" + picture + '\'' +
                ", powers='" + powers + '\'' +
                '}';
    }
}
