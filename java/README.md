## School Management System

This is a simple School Management System implemented in Java. The system allows users to manage students, teachers, and school records. Here's an overview of the main features:

### Classes

1. **Teacher:**
   - Represents a teacher with a name and a map to store students and their grades by course.
   - Methods:
     - `updateStudentGrade(studentName, courseId, grade)`: Update a student's grade.
     - `filterStudentsByGradeAndCourse(grade, courseId)`: Filter students by grade and course.

2. **Student:**
   - Represents a student with a name and a map to store grades by course.
   - Methods:
     - `viewGrades()`: View grades for the student.
     - `viewTeacherByCourseId(courseId, teacher)`: View teacher for a specific course.

3. **SchoolRecord:**
   - Represents the school record with lists to store student and teacher record changes.
   - Methods:
     - `addStudentChange(change)`: Add a change to the school record for students.
     - `addTeacherChange(change)`: Add a change to the school record for teachers.
     - `viewStudentChanges()`: View all changes in the school record for students.
     - `viewTeacherChanges()`: View all changes in the school record for teachers.

4. **School:**
   - Represents the school with lists to store students, teachers, and a school record.
   - Methods:
     - `enrollStudent()`: Enroll a new student.
     - `hireTeacher()`: Hire a new teacher.
     - `updateStudentGrade()`: Update a student's grade.
     - `filterStudentsByGradeAndCourse()`: Filter students by grade and course.
     - `viewSchoolRecord()`: View the school record.
     - `displayAvailableCoursesWithNumbers()`: Display the available courses with numbers.
     - `saveData()`: Save data to a file.
     - `loadData()`: Load data from a file.
     - `getTeachersForCourse(courseId)`: Get teachers for a specific course.
     - `getSelectedCourses(courseNumbers)`: Get selected courses based on course numbers.
     - `getUpdatedGrade(gradeOption)`: Get updated grade based on grade option.

### Usage

1. Run the program, and a menu will prompt you to choose an action.
2. Actions include enrolling a student, hiring a teacher, updating student grades, filtering students, viewing school records, and exiting the program.
3. The system allows you to perform various tasks related to school management.

### Data Persistence

- The system saves and loads data from a file (`school_data.ser`) to maintain the state of students, teachers, and the school record between program runs.

### Flowchart
![flowchart](/Users/divine/Downloads/Screenshot 2023-12-08 at 7.59.15 PM.png)  


I really tried and worked hard on this code, Thank you!
