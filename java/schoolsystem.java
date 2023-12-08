import java.io.*; // this import statement includes classes related to input and output operations, such as file handling.
import java.util.*; // this import statement includes utility classes provided by Java, such as collections (e.g., List, Map) and the Scanner class for input.


// Represents a Teacher
class Teacher implements Serializable {
    // declare variables
    String name; // teacher's name
    Map<String, Map<String, String>> students; // map to store students and their grades by course

    // Constructor to initialize a Teacher
    public Teacher(String name) {
        this.name = name; // initialize teacher's name
        this.students = new HashMap<>(); // initialize the map for students
    }

    // Update a student's grade
    public void updateStudentGrade(String studentName, String courseId, String grade) {
        if (!students.containsKey(studentName)) {
            students.put(studentName, new HashMap<>()); // if the student doesn't exist, add them to the map
        }
        students.get(studentName).put(courseId, grade); // update the grade for the student in the specified course
    }

    // Filter students by grade and course
    public void filterStudentsByGradeAndCourse(String grade, String courseId) {
        for (Map.Entry<String, Map<String, String>> studentEntry : students.entrySet()) {
            // iterate through students and their grades
            if (studentEntry.getValue().containsKey(courseId) && studentEntry.getValue().get(courseId).equals(grade)) {
                // if the student has the specified grade in the specified course
                System.out.println("Student: " + studentEntry.getKey() + " - Grade: " + grade + " in course: " + courseId);
                // print student information
            }
        }
    }
}

// Represents a Student
class Student implements Serializable {
    // declare variables
    String name; // student's name
    Map<String, String> grades; // map to store grades by course

    // Constructor to initialize a Student
    public Student(String name) {
        this.name = name; // initialize student's name
        this.grades = new HashMap<>(); // initialize the map for grades
    }

    // View grades for the student
    public void viewGrades() {
        System.out.println("Grades for " + name + ":"); // print student's name
        for (Map.Entry<String, String> gradeEntry : grades.entrySet()) {
            // iterate through grades
            System.out.println(gradeEntry.getKey() + " - " + gradeEntry.getValue());
            // print course and grade
        }
    }

    // View teacher for a specific course
    public void viewTeacherByCourseId(String courseId, Teacher teacher) {
        System.out.println("Teachers for course " + courseId + ":"); // print course
        for (Map.Entry<String, Map<String, String>> studentEntry : teacher.students.entrySet()) {
            // iterate through students and their grades by course
            if (studentEntry.getValue().containsKey(courseId) && studentEntry.getValue().get(courseId).equals(name)) {
                // if the student is enrolled in the specified course
                System.out.println("Student: " + studentEntry.getKey() + " - Teacher: " + teacher.name);
                // print student and teacher information
            }
        }
    }
}

// Represents the School Record
class SchoolRecord implements Serializable {
    // declare variables
    List<String> studentChanges; // list to store student record changes
    List<String> teacherChanges; // list to store teacher record changes

    // Constructor to initialize a School Record
    public SchoolRecord() {
        this.studentChanges = new ArrayList<>(); // initialize the list for student changes
        this.teacherChanges = new ArrayList<>(); // initialize the list for teacher changes
    }

    // Add a change to the school record for students
    public void addStudentChange(String change) {
        studentChanges.add(change); // add student change to the list
        System.out.println("Record change for student: " + change); // print student record change
    }

    // Add a change to the school record for teachers
    public void addTeacherChange(String change) {
        teacherChanges.add(change); // add teacher change to the list
        System.out.println("Record change for teacher: " + change); // print teacher record change
    }

    // View all changes in the school record for students
    public void viewStudentChanges() {
        System.out.println("Student Changes:"); // print section header
        for (String change : studentChanges) {
            // iterate through student changes
            System.out.println(change); // print each student change
        }
    }

    // View all changes in the school record for teachers
    public void viewTeacherChanges() {
        System.out.println("Teacher Changes:"); // print section header
        for (String change : teacherChanges) {
            // iterate through teacher changes
            System.out.println(change); // print each teacher change
        }
    }
}

// Represents the School
class School implements Serializable {
    // declare variables
    List<Student> students; // list to store students
    List<Teacher> teachers; // list to store teachers
    SchoolRecord schoolRecord; // school record
    List<String> availableCourses; // list of available courses

    // Constructor to initialize a School
    public School() {
        this.students = new ArrayList<>(); // initialize the list for students
        this.teachers = new ArrayList<>(); // initialize the list for teachers
        this.schoolRecord = new SchoolRecord(); // initialize the school record
        this.availableCourses = Arrays.asList("CS101", "MATH202", "ENG301", "PHYSICS101", "CHEM101", "BIOLOGY202", "HISTORY101", "GEOGRAPHY201");
        // initialize the list of available courses
        loadData(); // Load data from file on initialization
    }

    // Enroll a new student
    public void enrollStudent(Scanner scanner) {
        System.out.println("Enter the student name:"); // prompt for student name
        String studentName = scanner.nextLine(); // read student name from user input

        System.out.println("Choose up to 5 courses to enroll (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers(); // display available courses with numbers
        String[] courseNumbers = scanner.nextLine().split(","); // read selected course numbers from user input

        List<String> selectedCourses = new ArrayList<>(); // list to store selected courses
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                // convert course number to index and subtract 1 to match array index
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    // check if the course index is valid
                    selectedCourses.add(availableCourses.get(courseIndex)); // add selected course to the list
                } else {
                    System.out.println("Invalid course number. Choose a number listed."); // print error message for invalid course number
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas."); // print error message for invalid input format
                return;
            }
        }

        Student student = new Student(studentName); // create a new student object
        students.add(student); // add the student to the list

        // Display teachers for the chosen courses
        for (String courseId : selectedCourses) {
            System.out.println("Teachers for course " + courseId + ":"); // print course
            List<Teacher> teachersForCourse = getTeachersForCourse(courseId); // get teachers for the course
            for (Teacher teacher : teachersForCourse) {
                student.viewTeacherByCourseId(courseId, teacher); // display teachers for the course
            }
        }

        schoolRecord.addStudentChange("Enrolled student: " + studentName + " in courses: " + String.join(", ", selectedCourses));
        // add student enrollment record change
        saveData(); // Save data to file after enrolling a student
    }

    // Hire a new teacher
    public void hireTeacher(Scanner scanner) {
        System.out.println("Enter the teacher name:"); // prompt for teacher name
        String teacherName = scanner.nextLine(); // read teacher name from user input

        System.out.println("Choose the courses to hire for (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers(); // display available courses with numbers
        String[] courseNumbers = scanner.nextLine().split(","); // read selected course numbers from user input

        List<String> selectedCourses = new ArrayList<>(); // list to store selected courses
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                // convert course number to index and subtract 1 to match array index
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    // check if the course index is valid
                    selectedCourses.add(availableCourses.get(courseIndex)); // add selected course to the list
                } else {
                    System.out.println("Invalid course number. Choose a number listed."); // print error message for invalid course number
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas."); // print error message for invalid input format
                return;
            }
        }

        Teacher teacher = new Teacher(teacherName); // create a new teacher object
        teachers.add(teacher); // add the teacher to the list

        schoolRecord.addTeacherChange("Hired teacher: " + teacherName + " for courses: " + String.join(", ", selectedCourses));
        // add teacher hiring record change
        saveData(); // Save data to file after hiring a teacher
    }

    // Update a student's grade
    public void updateStudentGrade(Scanner scanner) {
        if (students.isEmpty()) {
            System.out.println("No students found."); // print error message if no students are found
            return;
        }

        System.out.println("Enter the student name:"); // prompt for student name
        String updatedStudentName = scanner.nextLine(); // read student name from user input

        System.out.println("Choose a course to update grade (Enter course ID):");
        displayAvailableCoursesWithNumbers(); // display available courses with numbers
        String updatedCourseId = scanner.nextLine(); // read selected course ID from user input

        if (availableCourses.contains(updatedCourseId)) {
            System.out.println("Choose a grade to update (Enter grade option: A, B, C, D, E, F):");
            String updatedGradeOption = scanner.nextLine().toUpperCase(); // read grade option from user input

            String updatedGrade;
            switch (updatedGradeOption) {
                case "A":
                    updatedGrade = "100-90"; // set grade range for A
                    break;
                case "B":
                    updatedGrade = "89-80"; // set grade range for B
                    break;
                case "C":
                    updatedGrade = "79-70"; // set grade range for C
                    break;
                case "D":
                    updatedGrade = "69-60"; // set grade range for D
                    break;
                case "E":
                    updatedGrade = "59-50"; // set grade range for E
                    break;
                case "F":
                    updatedGrade = "49-0"; // set grade range for F
                    break;
                default:
                    System.out.println("Invalid grade option. Please choose a valid grade option."); // print error message for invalid grade option
                    return;
            }

            for (Teacher teacher : teachers) {
                teacher.updateStudentGrade(updatedStudentName, updatedCourseId, updatedGrade); // update student's grade
            }
            schoolRecord.addStudentChange("Updated grade for student: " + updatedStudentName +
                    " in course: " + updatedCourseId + " to " + updatedGrade);
            // add student grade update record change
            saveData(); // Save data to file after updating a student's grade
        } else {
            System.out.println("Please choose a valid course."); // print error message for invalid course
        }
    }

    // Filter students by grade and course
    public void filterStudentsByGradeAndCourse(Scanner scanner) {
        if (students.isEmpty()) {
            System.out.println("No students found."); // print error message if no students are found
            return;
        }

        System.out.println("Choose a grade to filter students (Enter grade option: A, B, C, D, E, F):");
        String filterGradeOption = scanner.nextLine().toUpperCase(); // read grade option from user input

        String filterGrade;
        switch (filterGradeOption) {
            case "A":
                filterGrade = "100-90"; // set grade range for A
                break;
            case "B":
                filterGrade = "89-80"; // set grade range for B
                break;
            case "C":
                filterGrade = "79-70"; // set grade range for C
                break;
            case "D":
                filterGrade = "69-60"; // set grade range for D
                break;
            case "E":
                filterGrade = "59-50"; // set grade range for E
                break;
            case "F":
                filterGrade = "49-0"; // set grade range for F
                break;
            default:
                System.out.println("Invalid grade option. Please choose a valid grade option."); // print error message for invalid grade option
                return;
        }

        System.out.println("Choose a course to filter students (Enter course numbers separated by commas):");
        displayAvailableCoursesWithNumbers(); // display available courses with numbers
        String[] courseNumbers = scanner.nextLine().split(","); // read selected course numbers from user input

        List<String> selectedCourses = new ArrayList<>(); // list to store selected courses
        for (String courseNumber : courseNumbers) {
            try {
                int courseIndex = Integer.parseInt(courseNumber.trim()) - 1;
                // convert course number to index and subtract 1 to match array index
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    // check if the course index is valid
                    selectedCourses.add(availableCourses.get(courseIndex)); // add selected course to the list
                } else {
                    System.out.println("Invalid course number. Choose a number listed."); // print error message for invalid course number
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers separated by commas."); // print error message for invalid input format
                return;
            }
        }

        for (Teacher teacher : teachers) {
            teacher.filterStudentsByGradeAndCourse(filterGrade, String.join(", ", selectedCourses));
            // filter students by grade and course for each teacher
        }
    }

    // View the school record
    public void viewSchoolRecord(Scanner scanner) {
        System.out.println("Choose a record to view: Students (S) or Teachers (T)");
        String recordType = scanner.nextLine(); // read record type from user input

        switch (recordType.toUpperCase()) {
            case "S":
                schoolRecord.viewStudentChanges(); // view student record changes
                break;
            case "T":
                schoolRecord.viewTeacherChanges(); // view teacher record changes
                break;
            default:
                System.out.println("Invalid record type. Please choose either Students (S) or Teachers (T).");
                // print error message for invalid record type
                break;
        }
    }

    // Display the available courses with numbers
    private void displayAvailableCoursesWithNumbers() {
        System.out.println("Available Courses:"); // print section header
        for (int i = 0; i < availableCourses.size(); i++) {
            System.out.println((i + 1) + ". " + availableCourses.get(i));
            // print course number and name
        }
    }

    // Save data to file
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("school_data.ser"))) {
            oos.writeObject(this); // write the current state of the school to a file
        } catch (IOException e) {
            e.printStackTrace(); // print stack trace for any IO exceptions
        }
    }

    // Load data from file
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("school_data.ser"))) {
            School loadedSchool = (School) ois.readObject(); // read the saved school data from a file
            this.students = loadedSchool.students; // update current school's students list
            this.teachers = loadedSchool.teachers; // update current school's teachers list
            this.schoolRecord = loadedSchool.schoolRecord; // update current school's school record
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file doesn't exist or there's an issue reading it
            // Print stack trace for IO or class not found exceptions
        }
    }

    // Get teachers for a specific course
    private List<Teacher> getTeachersForCourse(String courseId) {
        List<Teacher> teachersForCourse = new ArrayList<>(); // list to store teachers for the course
        for (Teacher teacher : teachers) {
            // Check if the teacher teaches the given course
            if (teacher.students.containsKey(courseId)) {
                teachersForCourse.add(teacher); // add teacher to the list if they teach the course
            }
        }
        return teachersForCourse;
    }

    // Main class to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        School school = new School(); // create a new school object

        // Main loop for the School
        while (true) {
            // Menu for user actions
            System.out.println("Choose an action: Enroll Student (A), Hire Teacher (H), Update Student Grade (U), Filter Students by Grade and Course (F), View School Record (V), Exit (E)");
            String action = scanner.nextLine(); // read user action from input

            // Exit the program
            if (action.equalsIgnoreCase("E")) {
                break; // exit the loop if the user chooses to exit
            }

            // Perform actions based on user input
            switch (action.toUpperCase()) {
                case "A":
                    school.enrollStudent(scanner); // execute method to enroll a new student
                    break;
                case "H":
                    school.hireTeacher(scanner); // execute method to hire a new teacher
                    break;
                case "U":
                    school.updateStudentGrade(scanner); // execute method to update a student's grade
                    break;
                case "F":
                    school.filterStudentsByGradeAndCourse(scanner); // execute method to filter students by grade and course
                    break;
                case "V":
                    school.viewSchoolRecord(scanner); // execute method to view the school record
                    break;
                default:
                    System.out.println("Invalid action. Please choose a valid action.");
                    // print error message for invalid action
                    break;
            }
        }
    }
}
