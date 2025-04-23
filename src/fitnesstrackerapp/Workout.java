package fitnesstrackerapp;

// class for workouts
public class Workout {
  private String name;
  private String type;
  //integer to display the duration in minutes
  private int duration;
  //integer to display the amount of calories burned (kcal)
  private int caloriesBurned;

  //adding the attributes
  public Workout(String name, String type, int duration, int caloriesBurned) {
    this.name = name;
    this.type = type;
    this.duration = duration;
    this.caloriesBurned = caloriesBurned;
  }

  public String getType() {
    return type;
  }

  public int getDuration() {
    return duration;
  }

  public int getCaloriesBurned() {
    return caloriesBurned;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setCaloriesBurned(int caloriesBurned) {
    this.caloriesBurned = caloriesBurned;
  }

  //method used to display
  public void displayWorkout() {
    System.out.println("Workout Type: " + type);
    System.out.println("Duration: " + duration + "minutes");
    System.out.println("Calories Burned: " + caloriesBurned + " kcal");
  }
}
