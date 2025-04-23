package fitnesstrackerapp;

import java.util.List;
import java.util.ArrayList;

public class ProgressTracker {
    // Variable
    private List<Workout> workouts;

    // Constructor
    public ProgressTracker() {
        workouts = new ArrayList<>();
    }

    // Method to calculate the progress
    public String calculateProgress(int calorieGoal) {
        int burned = totalCalories();

        if (burned <= 0) {
            return "You haven't started working out yet!";
        }

        int progress = (int) (((double) burned / calorieGoal) * 100);

        if (progress >= 100) {
            return "Congratulations! You have reached your goal!";
        } else {
            return ("You have reached " + progress + "% of your calorie goal.");
        }
    }

    // Method to add a workout
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    // Method to calculate the total calories
    public int totalCalories() {
        int total = 0;
        for (Workout w : workouts) {
            total += w.getCaloriesBurned();
        }
        return total;
    }

    // Method to generate a report
    public void generateReport() {
        System.out.println("Workout Report:");
        for (Workout w : workouts) {
            System.out.println(w);
        }
        System.out.println("Total Calories Burned: " + totalCalories());
    }
}
