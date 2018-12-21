package com.it.jwt.entity;

public class Employee {

    private String name;
    private int age;
    private int km;
    private String cityName;

    public Employee(String name, String cityName, int km, int age) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", km=" + km +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
