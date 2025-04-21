package fitnesstrackerapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
  private ProgressTracker tracker;
    private JFrame frame;
    private JTable workoutTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, durationField, caloriesField, goalField;
    private JTextArea progressArea;
    private JComboBox<String> typeComboBox;
    private JProgressBar progressBar;

    public Gui() {
        tracker = new ProgressTracker();
        setupGUI();
    }

    private void setupGUI() {
        // Create the main window
        frame = new JFrame("Smart Fitness Tracker Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 840);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Panel with workout information
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        nameField = new JTextField();
        typeComboBox = new JComboBox<>(new String[]{"Cardio", "Yoga", "Strength", "Flexibility"});
        durationField = new JTextField();
        caloriesField = new JTextField();
        goalField = new JTextField();
        progressArea = new JTextArea(5, 30);
        progressArea.setEditable(false);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Add labels to the input section
        inputPanel.add(new JLabel("Workout Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Workout Type:"));
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel("Duration (min):"));
        inputPanel.add(durationField);
        inputPanel.add(new JLabel("Calories Burned:"));
        inputPanel.add(caloriesField);

        JButton addWorkoutButton = new JButton("Add Workout");
        JButton reportButton = new JButton("Generate Report");
        inputPanel.add(addWorkoutButton);
        inputPanel.add(reportButton);

        // Layout to input calorie goal
        JPanel goalPanel = new JPanel(new BorderLayout(5, 5));
        goalPanel.add(new JLabel("Set Calorie Goal:"), BorderLayout.WEST);
        goalPanel.add(goalField, BorderLayout.CENTER);

        // Button to show progress
        JButton showProgressButton = new JButton("Show Progress");
        goalPanel.add(showProgressButton, BorderLayout.EAST);

        // Add progress bar and area to the goal panel
        goalPanel.add(progressBar, BorderLayout.CENTER);
        goalPanel.add(new JScrollPane(progressArea), BorderLayout.SOUTH);

        // Workout table
        String[] columns = {"Name", "Type", "Duration", "Calories"};
        tableModel = new DefaultTableModel(columns, 0);
        workoutTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(workoutTable);

        // Add action to the buttons
        addWorkoutButton.addActionListener(e -> addWorkout());
        reportButton.addActionListener(e -> tracker.generateReport());
        showProgressButton.addActionListener(e -> showProgress());

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(goalPanel, BorderLayout.SOUTH);

        // Show the window
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    // When user clicks Add Workout
    private void addWorkout() {
        try {
            String name = nameField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            int duration = Integer.parseInt(durationField.getText().trim());
            int calories = Integer.parseInt(caloriesField.getText().trim());

            // Check valid input
            if (name.isEmpty() || duration <= 0 || calories <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter valid workout details.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a workout and add to tracker
            Workout workout = new Workout(name, type, duration, calories);
            tracker.addWorkout(workout);

            // Show in the table
            tableModel.addRow(new Object[]{name, type, duration, calories});

            nameField.setText("");
            durationField.setText("");
            caloriesField.setText("");

            // Show success message
            JOptionPane.showMessageDialog(frame, "Workout logged successfully.");
        } catch (NumberFormatException ex) {
            // If duration or calories are not numbers
            JOptionPane.showMessageDialog(frame, "Please use only numbers for duration and calories.", "Error, try again", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Show progress towards goal
    private void showProgress() {
        try {
            int goal = Integer.parseInt(goalField.getText().trim());
            if (goal <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a positive number for the goal.", "Error, try again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate progress
            int total = tracker.totalCalories();
            int percent = (int) ((double) total / goal * 100);
            percent = Math.min(percent, 100);
            progressBar.setValue(percent);

            // Show message in text area
            String status = tracker.calculateProgress(goal);
            progressArea.setText(status);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Goal must be a valid number.", "Error, try again", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Run the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui());
    }
}
  

