import java.io.*;
import java.util.*;

// Represents a Teacher
class Teacher implements Serializable {
    String name;
    Map<String, Map<String, String>> students;

    // Constructor to initialize a Teacher
    public Teacher(String name) {
        this.name = name;
        this.students = new HashMap<>();
    }

    // Update a student's grade
    public void updateStudentGrade(String studentName, String courseId, String grade) {
        if (!students.containsKey(studentName)) {
            students.put(studentName, new HashMap<>());
        }
        students.get(studentName).put(courseId, grade);
    }

    // Filter students by grade and course
    public void filterStudentsByGradeAndCourse(String grade, String courseId) {
        for (Map.Entry<String, Map<String, String>> studentEntry : students.entrySet()) {
            if (studentEntry.getValue().containsKey(courseId) && studentEntry.getValue().get(courseId).equals(grade)) {
                System.out.println("Student: " + studentEntry.getKey() + " - Grade: " + grade + " in course: " + courseId);
            }
        }
    }
}

// Represents a Student
class Student implements Serializable {
    String name;
    Map<String, String> grades;

    // Constructor to initialize a Student
    public Student(String name) {
        this.name = name;
        this.grades = new HashMap<>();
    }

    // View grades for the student
    public void viewGrades() {
        System.out.println("Grades for " + name + ":");
        for (Map.Entry<String, String> gradeEntry : grades.entrySet()) {
            System.out.println(gradeEntry.getKey() + " - " + gradeEntry.getValue());
        }
    }

    // View teacher for a specific course
    public void viewTeacherByCourseId(String courseId, Teacher teacher) {
        System.out.println("Teachers for course " + courseId + ":");
        for (Map.Entry<String, Map<String, String>> studentEntry : teacher.students.entrySet()) {
            if (studentEntry.getValue().containsKey(courseId) && studentEntry.getValue().get(courseId).equals(name)) {
                System.out.println("Student: " + studentEntry.getKey() + " - Teacher: " + teacher.name);
            }
        }
    }
}

// Represents the School Record
class SchoolRecord implements Serializable {
    List<String> studentChanges;
    List<String> teacherChanges;

    // Constructor to initialize a School Record
    public SchoolRecord() {
        this.studentChanges = new ArrayList<>();
        this.teacherChanges = new ArrayList<>();
    }

    // Add a change to the school record for students
    public void addStudentChange(String change) {
        studentChanges.add(change);
        System.out.println("Record change for student: " + change);
    }

    // Add a change to the school record for teachers
    public void addTeacherChange(String change) {
        teacherChanges.add(change);
        System.out.println("Record change for teacher: " + change);
    }

    // View all changes in the school record for students
    public void viewStudentChanges() {
        System.out.println("Student Changes:");
        for (String change : studentChanges) {
            System.out.println(change);
        }
    }

    // View all changes in the school record for teachers
    public void viewTeacherChanges() {
        System.out.println("Teacher Changes:");
        for (String change : teacherChanges) {
            System.out.println(change);
        }
    }
}

// Represents the School
class School implements Serializable {
    List<Student> students;
    List<Teacher> teachers;
    SchoolRecord schoolRecord;
    List<String> availableCourses;

    // Constructor to initialize a School
    public School() {
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.schoolRecord = new SchoolRecord();
        this.availableCourses = Arrays.asList("CS101", "MATH202", "ENG301", "PHYSICS101", "CHEM101", "BIOLOGY202", "HISTORY101", "GEOGRAPHY201");
        loadData(); // Load data from file on initialization
    }

    // Enroll a new student
    public void enrollStudent(Scanner scanner) {
        System.out.println("Enter the student name:");
        String studentName = scanner.nextLine();

        System.out.println("Choose up to 5 courses to enroll (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers();
        String[] courseNumbers = scanner.nextLine().split(",");

        List<String> selectedCourses = new ArrayList<>();
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    selectedCourses.add(availableCourses.get(courseIndex));
                } else {
                    System.out.println("Invalid course number. Choose a number listed.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas.");
                return;
            }
        }

        Student student = new Student(studentName);
        students.add(student);

        // Display teachers for the chosen courses
        for (String courseId : selectedCourses) {
            System.out.println("Teachers for course " + courseId + ":");
            List<Teacher> teachersForCourse = getTeachersForCourse(courseId);
            for (Teacher teacher : teachersForCourse) {
                student.viewTeacherByCourseId(courseId, teacher);
            }
        }

        schoolRecord.addStudentChange("Enrolled student: " + studentName + " in courses: " + String.join(", ", selectedCourses));
        saveData(); // Save data to file after enrolling a student
    }

    // Hire a new teacher
    public void hireTeacher(Scanner scanner) {
        System.out.println("Enter the teacher name:");
        String teacherName = scanner.nextLine();

        System.out.println("Choose the courses to hire for (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers();
        String[] courseNumbers = scanner.nextLine().split(",");

        List<String> selectedCourses = new ArrayList<>();
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    selectedCourses.add(availableCourses.get(courseIndex));
                } else {
                    System.out.println("Invalid course number. Choose a number listed.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas.");
                return;
            }
        }

        Teacher teacher = new Teacher(teacherName);
        teachers.add(teacher);

        schoolRecord.addTeacherChange("Hired teacher: " + teacherName + " for courses: " + String.join(", ", selectedCourses));
        saveData(); // Save data to file after hiring a teacher
    }

    // Update a student's grade
    public void updateStudentGrade(Scanner scanner) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Enter the student name:");
        String updatedStudentName = scanner.nextLine();

        System.out.println("Choose a course to update grade (Enter course ID):");
        displayAvailableCoursesWithNumbers();
        String updatedCourseId = scanner.nextLine();

        if (availableCourses.contains(updatedCourseId)) {
            System.out.println("Choose a grade to update (Enter grade option: A, B, C, D, E, F):");
            String updatedGradeOption = scanner.nextLine().toUpperCase();

            String updatedGrade;
            switch (updatedGradeOption) {
                case "A":
                    updatedGrade = "100-90";
                    break;
                case "B":
                    updatedGrade = "89-80";
                    break;
                case "C":
                    updatedGrade = "79-70";
                    break;
                case "D":
                    updatedGrade = "69-60";
                    break;
                case "E":
                    updatedGrade = "59-50";
                    break;
                case "F":
                    updatedGrade = "49-0";
                    break;
                default:
                    System.out.println("Invalid grade option. Please choose a valid grade option.");
                    return;
            }

            for (Teacher teacher : teachers) {
                teacher.updateStudentGrade(updatedStudentName, updatedCourseId, updatedGrade);
            }
            schoolRecord.addStudentChange("Updated grade for student: " + updatedStudentName +
                    " in course: " + updatedCourseId + " to " + updatedGrade);
            saveData(); // Save data to file after updating a student's grade
        } else {
            System.out.println("Please choose a valid course.");
        }
    }

    // Filter students by grade and course
    public void filterStudentsByGradeAndCourse(Scanner scanner) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Choose a grade to filter students (Enter grade option: A, B, C, D, E, F):");
        String filterGradeOption = scanner.nextLine().toUpperCase();

        String filterGrade;
        switch (filterGradeOption) {
            case "A":
                filterGrade = "100-90";
                break;
            case "B":
                filterGrade = "89-80";
                break;
            case "C":
                filterGrade = "79-70";
                break;
            case "D":
                filterGrade = "69-60";
                break;
            case "E":
                filterGrade = "59-50";
                break;
            case "F":
                filterGrade = "49-0";
                break;
            default:
                System.out.println("Invalid grade option. Please choose a valid grade option.");
                return;
        }

        System.out.println("Choose a course to filter students (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers();
        String[] courseNumbers = scanner.nextLine().split(",");

        List<String> selectedCourses = new ArrayList<>();
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    selectedCourses.add(availableCourses.get(courseIndex));
                } else {
                    System.out.println("Invalid course number. Choose a number listed.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas.");
                return;
            }
        }

        for (Teacher teacher : teachers) {
            teacher.filterStudentsByGradeAndCourse(filterGrade, String.join(", ", selectedCourses));
        }
    }

    // View the school record
    public void viewSchoolRecord(Scanner scanner) {
        System.out.println("Choose a record to view: Students (S) or Teachers (T)");
        String recordType = scanner.nextLine();

        switch (recordType.toUpperCase()) {
            case "S":
                schoolRecord.viewStudentChanges();
                break;
            case "T":
                schoolRecord.viewTeacherChanges();
                break;
            default:
                System.out.println("Invalid record type. Please choose either Students (S) or Teachers (T).");
                break;
        }
    }

    // Display the available courses with numbers
    private void displayAvailableCoursesWithNumbers() {
        System.out.println("Available Courses:");
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + ". " + availableCourses.get(i));
        }
    }

    // Save data to file
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("school_data.ser"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from file
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("school_data.ser"))) {
            School loadedSchool = (School) ois.readObject();
            this.students = loadedSchool.students;
            this.teachers = loadedSchool.teachers;
            this.schoolRecord = loadedSchool.schoolRecord;
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file
            // doesn't exist or there's an issue reading it
        }
    }

    // Get teachers for a specific course
    private List<Teacher> getTeachersForCourse(String courseId) {
        List<Teacher> teachersForCourse = new ArrayList<>();
        for (Teacher teacher : teachers) {
            // Check if the teacher teaches the given course
            if (teacher.students.containsKey(courseId)) {
                teachersForCourse.add(teacher);
            }
        }
        return teachersForCourse;
    }

    // Main class to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        School school = new School();

        // Main loop for the School
        while (true) {
            // Menu for user actions
            System.out.println("Choose an action: Enroll Student (A), Hire Teacher (H), Update Student Grade (U), Filter Students by Grade and Course (F), View School Record (V), Exit (E)");
            String action = scanner.nextLine();

            // Exit the program
            if (action.equalsIgnoreCase("E")) {
                break;
            }

            // Perform actions based on user input
            switch (action.toUpperCase()) {
                case "A":
                    school.enrollStudent(scanner);
                    break;
                case "H":
                    school.hireTeacher(scanner);
                    break;
                case "U":
                    school.updateStudentGrade(scanner);
                    break;
                case "F":
                    school.filterStudentsByGradeAndCourse(scanner);
                    break;
                case "V":
                    school.viewSchoolRecord(scanner);
                    break;
                default:
                    System.out.println("Invalid action. Please choose a valid action.");
                    break;
            }
        }
    }
}
