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
<img width="1452" alt="flowchart" src="https://github.com/divineib/miniproject2/assets/143463868/67289dc3-e272-4b1b-8322-40808d4b0d7b">

### UML Diagram
<img width="830" alt="umldiagram" src="https://github.com/divineib/miniproject2/assets/143463868/1980a1c0-47e8-4437-be6c-5336b7eef420">

### Questions for Reflection and Discussion

1. **How does the UML diagram help with planning in coding?**
   - The UML diagram is like a superhero map for coding. It shows all the cool characters (classes) and how they team up. This map helps us plan by making it easier to understand and talk about our code. It's like having a game plan for building something awesome!

2. **Why is Object-Oriented Programming better than Project 1?**
   - Object-Oriented Programming (OOP) is like having magical building blocks for coding. In Project 1, we just put things together, but in OOP, we use these blocks (classes) that can do special tricks. We can reuse them, keep secrets (encapsulation), and even make new blocks based on existing ones (inheritance). It's like having a treasure chest of cool code tricks!

3. **What cool tricks (Object-Oriented pillars) were used?**
   - The cool tricks are like rules we follow to make our code awesome. Encapsulation is like keeping secrets safe, so only the right parts are shown. Inheritance is like having a superhero family, where new heroes (classes) inherit powers from the main hero (base class). Polymorphism is a big word for saying things can change and do different tricks in different situations. These tricks make our code easy to understand and fun to work with!

I really tried and worked hard on this code, Thank you!
