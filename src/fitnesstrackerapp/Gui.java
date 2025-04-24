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
    private JTextField userNameField, ageField, weightField;
    private JTextArea progressArea;
    private JComboBox<String> typeComboBox;
    private JProgressBar progressBar;
    private User User;

    public Gui() {
        tracker = new ProgressTracker();
        setupGUI();
    }

    private void setupGUI() {
        // Create the main window
        frame = new JFrame("Smart Fitness Tracker Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 1000);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Panel with workout information
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        userNameField = new JTextField();
        ageField = new JTextField();
        weightField = new JTextField();
        goalField = new JTextField();
        progressArea = new JTextArea(5, 30);
        progressArea.setEditable(false);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Adds fields for personal information
        userPanel.add(new JLabel("User Name:"));
        userPanel.add(userNameField);
        userPanel.add(new JLabel("Age:"));
        userPanel.add(ageField);
        userPanel.add(new JLabel("Weight(kg):"));
        userPanel.add(weightField);
        userPanel.add(new JLabel("Set Calorie Goal:"));
        userPanel.add(goalField);

        // Adds save button for user information
        JButton saveUserButton = new JButton("Save User Info");
        userPanel.add(saveUserButton);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        workoutNameField = new JTextField();
        typeComboBox = new JComboBox<>(new String[]{"Cardio", "Yoga", "Strength", "Flexibility"});
        durationField = new JTextField();
        caloriesField = new JTextField();

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
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressArea = new JTextArea(5, 30);
        progressArea.setEditable(false);
        JScrollPane progressScroll = new JScrollPane(progressArea);

        // Button to show progress
        JButton showProgressButton = new JButton("Show Progress");

        // Add progress bar and area to the goal panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(progressScroll, BorderLayout.CENTER);
        bottomPanel.add(showProgressButton, BorderLayout.SOUTH);

        // Workout table
        String[] columns = {"Name", "Type", "Duration", "Calories"};
        tableModel = new DefaultTableModel(columns, 0);
        workoutTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(workoutTable);

        // Add action to the buttons
        saveUserButton.addActionListener(e -> saveUserInfo());
        addWorkoutButton.addActionListener(e -> addWorkout());
        reportButton.addActionListener(e -> tracker.generateReport());
        showProgressButton.addActionListener(e -> showProgress());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(userPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(goalPanel, BorderLayout.SOUTH);

        // Show the window
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private void saveUserInfo() {
        try {
            String name = userNameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            int goal = Integer.parseInt(goalField.getText().trim());

            if (name.isEmpty() || age <0 || weight <= 0 || goal <= 0) {
                JOptionPane.showMessageDialog(frame, "Please fill out all user fields with valid values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User = new User(name, age, weight, new ArrayList<>(Arrays.asList("Burn " + goal + " calories")));
            JOptionPane.showMessageDialog(frame, "User information saved successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter values for age, weight, and goal.", "Format Error", JOptionPane.WARNING_MESSAGE);
        }
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
                JOptionPane.showMessageDialog(frame, "Please enter valid workout details.","Error", JOptionPane.ERROR_MESSAGE);
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
            int percent = (int) (((double) total / goal) * 100);
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