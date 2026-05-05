import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CourseFeedbackApp {

    static ArrayList<String> courses = new ArrayList<String>();
    static ArrayList<Integer> ratings = new ArrayList<Integer>();
    static ArrayList<String> comments = new ArrayList<String>();

    static JTextField courseField;
    static JComboBox<Integer> ratingBox;
    static JTextArea commentArea;
    static JTextArea outputArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Course Feedback App");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 1));

        courseField = new JTextField();

        ratingBox = new JComboBox<Integer>();
        ratingBox.addItem(1);
        ratingBox.addItem(2);
        ratingBox.addItem(3);
        ratingBox.addItem(4);
        ratingBox.addItem(5);

        commentArea = new JTextArea();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton addButton = new JButton("Add Feedback");
        JButton averageButton = new JButton("Show Average Rating");
        JButton exportButton = new JButton("Export to CSV");

        frame.add(new JLabel("Course Name:"));
        frame.add(courseField);

        frame.add(new JLabel("Rating:"));
        frame.add(ratingBox);

        frame.add(new JLabel("Comment:"));
        frame.add(commentArea);

        frame.add(addButton);
        frame.add(averageButton);
        frame.add(exportButton);
        frame.add(outputArea);

        addButton.addActionListener(e -> addFeedback());
        averageButton.addActionListener(e -> showAverage());
        exportButton.addActionListener(e -> exportToCSV());

        frame.setVisible(true);
    }

    public static void addFeedback() {
        String course = courseField.getText();
        int rating = (Integer) ratingBox.getSelectedItem();
        String comment = commentArea.getText();

        if (course.isEmpty() || comment.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a course name and comment.");
            return;
        }

        courses.add(course);
        ratings.add(rating);
        comments.add(comment);

        outputArea.append("Course: " + course + "\n");
        outputArea.append("Rating: " + rating + "/5\n");
        outputArea.append("Comment: " + comment + "\n");
        outputArea.append("----------------------\n");

        courseField.setText("");
        commentArea.setText("");

        JOptionPane.showMessageDialog(null, "Feedback added.");
    }

    public static void showAverage() {
        if (ratings.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No ratings yet.");
            return;
        }

        int total = 0;

        for (int rating : ratings) {
            total += rating;
        }

        double average = (double) total / ratings.size();

        JOptionPane.showMessageDialog(null, "Average Rating: " + String.format("%.2f", average) + "/5");
    }

    public static void exportToCSV() {
        if (courses.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No feedback to export.");
            return;
        }

        try {
            FileWriter writer = new FileWriter("course_feedback_results.csv");

            writer.write("Course,Rating,Comment\n");

            for (int i = 0; i < courses.size(); i++) {
                writer.write(courses.get(i) + "," + ratings.get(i) + "," + comments.get(i) + "\n");
            }

            writer.close();

            JOptionPane.showMessageDialog(null, "Export complete: course_feedback_results.csv");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Export failed.");
        }
    }
}