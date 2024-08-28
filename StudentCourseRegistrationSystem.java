import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Course class to represent a course
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSlots() {
        return capacity - enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean registerStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + "\nTitle: " + title + "\nDescription: " + description +
                "\nCapacity: " + capacity + "\nEnrolled: " + enrolled + "\nAvailable Slots: " + getAvailableSlots() +
                "\nSchedule: " + schedule + "\n";
    }
}

// Student class to represent a student
class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            System.out.println("Already registered for this course.");
        } else if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for the course.");
        } else {
            System.out.println("Course is full. Registration failed.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Successfully dropped the course.");
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + "\nName: " + name + "\nRegistered Courses: " + registeredCourses.size();
    }
}

// Main class to run the system
public class StudentCourseRegistrationSystem {
    private static HashMap<String, Course> courses = new HashMap<>();
    private static HashMap<String, Student> students = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        int choice;
        do {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. List Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Student Information");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> listCourses();
                case 2 -> registerForCourse();
                case 3 -> dropCourse();
                case 4 -> viewStudentInformation();
                case 5 -> System.out.println("Exiting the system.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Method to initialize some sample data
    private static void initializeData() {
        courses.put("CS101", new Course("CS101", "Introduction to Programming", "Learn basic programming concepts.", 30, "Mon/Wed 10:00-11:30"));
        courses.put("CS102", new Course("CS102", "Data Structures", "Study data structures like arrays, stacks, and queues.", 25, "Tue/Thu 12:00-13:30"));
        courses.put("CS103", new Course("CS103", "Discrete Mathematics", "Introduction to mathematical reasoning.", 20, "Mon/Wed 14:00-15:30"));

        students.put("S1001", new Student("S1001", "Alice"));
        students.put("S1002", new Student("S1002", "Bob"));
    }

    // List all available courses
    private static void listCourses() {
        System.out.println("\n--- Available Courses ---");
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }

    // Register a student for a course
    private static void registerForCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student != null) {
            System.out.print("Enter Course Code to Register: ");
            String courseCode = scanner.nextLine();
            Course course = courses.get(courseCode);

            if (course != null) {
                student.registerCourse(course);
            } else {
                System.out.println("Invalid course code.");
            }
        } else {
            System.out.println("Invalid student ID.");
        }
    }

    // Drop a course for a student
    private static void dropCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student != null) {
            System.out.print("Enter Course Code to Drop: ");
            String courseCode = scanner.nextLine();
            Course course = courses.get(courseCode);

            if (course != null) {
                student.dropCourse(course);
            } else {
                System.out.println("Invalid course code.");
            }
        } else {
            System.out.println("Invalid student ID.");
        }
    }

    // View student information and their registered courses
    private static void viewStudentInformation() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student != null) {
            System.out.println("\n--- Student Information ---");
            System.out.println(student);
            System.out.println("\n--- Registered Courses ---");
            for (Course course : student.getRegisteredCourses()) {
                System.out.println(course);
            }
        } else {
            System.out.println("Invalid student ID.");
        }
    }
}