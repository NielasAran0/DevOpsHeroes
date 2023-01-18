package me.escoffier.workshop.supes.fight;

public class Hero {

    public String name;
    public String otherName;
    public int level;
    public String picture;

    public String powers;
    public Long id;

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", otherName='" + otherName + '\'' +
                ", level=" + level +
                ", picture='" + picture + '\'' +
                ", powers='" + powers + '\'' +
                '}';
    }
}
