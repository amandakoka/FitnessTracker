package fitnesstrackerapp;

import java.util.List;
import java.util.ArrayList;

public class User {
    // User attributes
    private String name;
    private int age;
    private double weight;
    private List<String> goals; // changed from string to list to store multiple goals

    /* User constructors so we can store user information
     * Initialises objects
     * Called when an object of a class is created
     */
    public User(String name, int age, double weight, List<String> goals) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.goals = new ArrayList<>(goals);
    }

    // Name getter and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Age getter and setters
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    // Weight getter and setters
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Goals getter and setters
    public List<String> getGoals() {
        return new ArrayList<>(goals);
    }
    public void setGoals( List<String> goals) {
        this.goals = new ArrayList<>(goals);
    }
}
