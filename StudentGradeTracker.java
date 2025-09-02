

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Student {
    String name;
    ArrayList<Integer> grades = new ArrayList<>();

    Student(String name) {
        this.name = name;
    }

    double getAverage() {
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    int getHighest() {
        return grades.stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    int getLowest() {
        return grades.stream().mapToInt(Integer::intValue).min().orElse(0);
    }

    String getSummary() {
        return String.format(
            "Name: %s\nGrades: %s\nAverage: %.2f\nHighest: %d\nLowest: %d\n",
            name, grades, getAverage(), getHighest(), getLowest()
        );
    }
}

public class GradeTrackerGUI {
    private JFrame frame;
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private ArrayList<Student> students = new ArrayList<>();
    private Student currentStudent;

    public GradeTrackerGUI() {
        frame = new JFrame("Student Grade Tracker");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        nameField = new JTextField(20);
        gradeField = new JTextField(5);
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        JButton addStudentBtn = new JButton("Add Student");
        JButton addGradeBtn = new JButton("Add Grade");
        JButton showSummaryBtn = new JButton("Show Summary");

        frame.add(new JLabel("Student Name:"));
        frame.add(nameField);
        frame.add(addStudentBtn);

        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        frame.add(addGradeBtn);

        frame.add(showSummaryBtn);
        frame.add(new JScrollPane(outputArea));

        addStudentBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                currentStudent = new Student(name);
                students.add(currentStudent);
                outputArea.append("Added student: " + name + "\n");
                nameField.setText("");
            }
        });

        addGradeBtn.addActionListener(e -> {
            if (currentStudent == null) {
                outputArea.append("Please add a student first.\n");
                return;
            }
            try {
                int grade = Integer.parseInt(gradeField.getText().trim());
                currentStudent.grades.add(grade);
                outputArea.append("Added grade: " + grade + " to " + currentStudent.name + "\n");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid grade input.\n");
            }
        });

        showSummaryBtn.addActionListener(e -> {
            outputArea.setText("");
            for (Student s : students) {
                outputArea.append(s.getSummary() + "\n");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GradeTrackerGUI::new);
    }
}