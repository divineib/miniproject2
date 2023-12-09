## School Management System

This is a simple School Management System implemented in Python. The system allows users to manage students, teachers, and school records. Here's an overview of the main features:

### Classes

1. **Teacher:**
   - Represents a teacher with a name and a map to store students and their grades by course.
   - Methods:
     - `update_student_grade(student_name, course_id, grade)`: Update a student's grade.
     - `filter_students_by_grade_and_course(grade, course_id)`: Filter students by grade and course.

2. **Student:**
   - Represents a student with a name and a map to store grades by course.
   - Methods:
     - `view_grades()`: View grades for the student.
     - `view_teacher_by_course_id(course_id, teacher)`: View teacher for a specific course.

3. **SchoolRecord:**
   - Represents the school record with lists to store student and teacher record changes.
   - Methods:
     - `add_student_change(change)`: Add a change to the school record for students.
     - `add_teacher_change(change)`: Add a change to the school record for teachers.
     - `view_student_changes()`: View all changes in the school record for students.
     - `view_teacher_changes()`: View all changes in the school record for teachers.

4. **School:**
   - Represents the school with lists to store students, teachers, and a school record.
   - Methods:
     - `enroll_student()`: Enroll a new student.
     - `hire_teacher()`: Hire a new teacher.
     - `update_student_grade()`: Update a student's grade.
     - `filter_students_by_grade_and_course()`: Filter students by grade and course.
     - `view_school_record()`: View the school record.
     - `display_available_courses_with_numbers()`: Display the available courses with numbers.
     - `save_data()`: Save data to a file.
     - `load_data()`: Load data from a file.
     - `get_teachers_for_course(course_id)`: Get teachers for a specific course.
     - `get_selected_courses(course_numbers)`: Get selected courses based on course numbers.
     - `get_updated_grade(grade_option)`: Get updated grade based on grade option.

### Usage

1. Run the program, and a menu will prompt you to choose an action.
2. Actions include enrolling a student, hiring a teacher, updating student grades, filtering students, viewing school records, and exiting the program.
3. The system allows you to perform various tasks related to school management.

### Data Persistence

- The system saves and loads data from a file (`school_data.pkl`) to maintain the state of students, teachers, and the school record between program runs.

### Flowchart

<img width="1608" alt="flowchart" src="https://github.com/divineib/miniproject2/assets/143463868/2fd9c2d8-2eb1-4343-b6a0-77368e6a8b90">

### UML Diagram

<img width="797" alt="umldiagram" src="https://github.com/divineib/miniproject2/assets/143463868/817f04c4-5ae0-4422-8784-35bb8f26630a">

### Questions for Reflection and Discussion

1. **How does the UML diagram help with planning in coding?**
   - The UML diagram is like a superhero map for coding. It shows all the cool characters (classes) and how they team up. This map helps us plan by making it easier to understand and talk about our code. It's like having a game plan for building something awesome!

2. **Why is Object-Oriented Programming better than Project 1?**
   - Object-Oriented Programming (OOP) is like having magical building blocks for coding. In Project 1, we just put things together, but in OOP, we use these blocks (classes) that can do special tricks. We can reuse them, keep secrets (encapsulation), and even make new blocks based on existing ones (inheritance). It's like having a treasure chest of cool code tricks!

3. **What cool tricks (Object-Oriented pillars) were used?**
   - The cool tricks are like rules we follow to make our code awesome. Encapsulation is like keeping secrets safe, so only the right parts are shown. Inheritance is like having a superhero family, where new heroes (classes) inherit powers from the main hero (base class). Polymorphism is a big word for saying things can change and do different tricks in different situations. These tricks make our code easy to understand and fun to work with!

I really tried and worked hard on this code, Thank you!