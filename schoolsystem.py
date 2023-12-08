import pickle  # this import statement includes classes related to input and output operations, such as file handling.

class Teacher:
    def __init__(self, name):  # Represents a Teacher
        self.name = name  # teacher's name
        self.students = {}  # map to store students and their grades by course

    def update_student_grade(self, student_name, course_id, grade):  # Update a student's grade
        if student_name not in self.students:
            self.students[student_name] = {}  # if the student doesn't exist, add them to the map
        self.students[student_name][course_id] = grade  # update the grade for the student in the specified course

    def filter_students_by_grade_and_course(self, grade, course_id):  # Filter students by grade and course
        for student_name, grades in self.students.items():
            # iterate through students and their grades
            if course_id in grades and grades[course_id] == grade:
                # if the student has the specified grade in the specified course
                print(f"Student: {student_name} - Grade: {grade} in course: {course_id}")
                # print student information

class Student:
    def __init__(self, name):  # Represents a Student
        self.name = name  # student's name
        self.grades = {}  # map to store grades by course

    def view_grades(self):  # View grades for the student
        print(f"Grades for {self.name}:")  # print student's name
        for course_id, grade in self.grades.items():
            # iterate through grades
            print(f"{course_id} - {grade}")  # print course and grade

    def view_teacher_by_course_id(self, course_id, teacher):  # View teacher for a specific course
        print(f"Teachers for course {course_id}:")  # print course
        for student_name, grades in teacher.students.items():
            # iterate through students and their grades by course
            if course_id in grades and grades[course_id] == self.name:
                # if the student is enrolled in the specified course
                print(f"Student: {student_name} - Teacher: {teacher.name}")
                # print student and teacher information

class SchoolRecord:
    def __init__(self):  # Represents the School Record
        self.student_changes = []  # list to store student record changes
        self.teacher_changes = []  # list to store teacher record changes

    def add_student_change(self, change):  # Add a change to the school record for students
        self.student_changes.append(change)  # add student change to the list
        print(f"Record change for student: {change}")  # print student record change

    def add_teacher_change(self, change):  # Add a change to the school record for teachers
        self.teacher_changes.append(change)  # add teacher change to the list
        print(f"Record change for teacher: {change}")  # print teacher record change

    def view_student_changes(self):  # View all changes in the school record for students
        print("Student Changes:")  # print section header
        for change in self.student_changes:
            # iterate through student changes
            print(change)  # print each student change

    def view_teacher_changes(self):  # View all changes in the school record for teachers
        print("Teacher Changes:")  # print section header
        for change in self.teacher_changes:
            # iterate through teacher changes
            print(change)  # print each teacher change

class School:
    def __init__(self):  # Represents the School
        self.students = []  # list to store students
        self.teachers = []  # list to store teachers
        self.school_record = SchoolRecord()  # school record
        self.available_courses = ["CS101", "MATH202", "ENG301", "PHYSICS101", "CHEM101", "BIOLOGY202", "HISTORY101", "GEOGRAPHY201"]
        # list of available courses
        self.load_data()  # Load data from file on initialization

    def enroll_student(self):  # Enroll a new student
        student_name = input("Enter the student name:")  # prompt for student name
        print("Choose up to 5 courses to enroll (Enter course numbers separated by commas):")
        self.display_available_courses_with_numbers()  # display available courses with numbers
        course_numbers = input().split(",")  # read selected course numbers from user input
        selected_courses = self.get_selected_courses(course_numbers)  # list to store selected courses

        student = Student(student_name)  # create a new student object
        self.students.append(student)  # add the student to the list

        # Display teachers for the chosen courses
        for course_id in selected_courses:
            print(f"Teachers for course {course_id}:")  # print course
            teachers_for_course = self.get_teachers_for_course(course_id)  # get teachers for the course
            for teacher in teachers_for_course:
                student.view_teacher_by_course_id(course_id, teacher)  # display teachers for the course

        self.school_record.add_student_change(f"Enrolled student: {student_name} in courses: {', '.join(selected_courses)}")
        # add student enrollment record change
        self.save_data()  # Save data to file after enrolling a student

    def hire_teacher(self):  # Hire a new teacher
        teacher_name = input("Enter the teacher name:")  # prompt for teacher name
        print("Choose the courses to hire for (Enter course numbers separated by commas):")
        self.display_available_courses_with_numbers()  # display available courses with numbers
        course_numbers = input().split(",")  # read selected course numbers from user input
        selected_courses = self.get_selected_courses(course_numbers)  # list to store selected courses

        teacher = Teacher(teacher_name)  # create a new teacher object
        self.teachers.append(teacher)  # add the teacher to the list

        self.school_record.add_teacher_change(f"Hired teacher: {teacher_name} for courses: {', '.join(selected_courses)}")
        # add teacher hiring record change
        self.save_data()  # Save data to file after hiring a teacher

    def update_student_grade(self):  # Update a student's grade
        if not self.students:
            print("No students found.")  # print error message if no students are found
            return

        updated_student_name = input("Enter the student name:")  # prompt for student name
        print("Choose a course to update grade (Enter course ID):")
        self.display_available_courses_with_numbers()  # display available courses with numbers
        updated_course_id = input()  # read selected course ID from user input

        if updated_course_id in self.available_courses:
            print("Choose a grade to update (Enter grade option: A, B, C, D, E, F):")
            updated_grade_option = input().upper()  # read grade option from user input

            updated_grade = self.get_updated_grade(updated_grade_option)  # get updated grade based on the option

            for teacher in self.teachers:
                teacher.update_student_grade(updated_student_name, updated_course_id, updated_grade)
                # update student's grade

            self.school_record.add_student_change(f"Updated grade for student: {updated_student_name} "
                                                  f"in course: {updated_course_id} to {updated_grade}")
            # add student grade update record change
            self.save_data()  # Save data to file after updating a student's grade
        else:
            print("Please choose a valid course.")  # print error message for invalid course

    def filter_students_by_grade_and_course(self):  # Filter students by grade and course
        if not self.students:
            print("No students found.")  # print error message if no students are found
            return

        print("Choose a grade to filter students (Enter grade option: A, B, C, D, E, F):")
        filter_grade_option = input().upper()  # read grade option from user input

        filter_grade = self.get_updated_grade(filter_grade_option)  # get updated grade based on the option

        print("Choose a course to filter students (Enter course numbers separated by commas):")
        self.display_available_courses_with_numbers()  # display available courses with numbers
        course_numbers = input().split(",")  # read selected course numbers from user input
        selected_courses = self.get_selected_courses(course_numbers)  # list to store selected courses

        for teacher in self.teachers:
            teacher.filter_students_by_grade_and_course(filter_grade, ', '.join(selected_courses))
            # filter students by grade and course for each teacher

    def view_school_record(self):  # View the school record
        print("Choose a record to view: Students (S) or Teachers (T)")
        record_type = input().upper()  # read record type from user input

        if record_type == "S":
            self.school_record.view_student_changes()  # view student record changes
        elif record_type == "T":
            self.school_record.view_teacher_changes()  # view teacher record changes
        else:
            print("Invalid record type. Please choose either Students (S) or Teachers (T).")
            # print error message for invalid record type

    def display_available_courses_with_numbers(self):  # Display the available courses with numbers
        print("Available Courses:")  # print section header
        for i, course in enumerate(self.available_courses, 1):
            print(f"{i}. {course}")  # print course number and name

    def save_data(self):  # Save data to file
        with open("school_data.pkl", "wb") as file:
            pickle.dump(self, file)  # write the current state of the school to a file

    def load_data(self):  # Load data from file
        try:
            with open("school_data.pkl", "rb") as file:
                loaded_school = pickle.load(file)  # read the saved school data from a file
                self.students = loaded_school.students  # update current school's students list
                self.teachers = loaded_school.teachers  # update current school's teachers list
                self.school_record = loaded_school.school_record  # update current school's school record
        except (FileNotFoundError, EOFError):
            pass  # Ignore if the file doesn't exist or there's an issue reading it

    def get_teachers_for_course(self, course_id):  # Get teachers for a specific course
        teachers_for_course = []  # list to store teachers for the course
        for teacher in self.teachers:
            # Check if the teacher teaches the given course
            if course_id in teacher.students:
                teachers_for_course.append(teacher)  # add teacher to the list if they teach the course
        return teachers_for_course

    def get_selected_courses(self, course_numbers):  # Get selected courses based on course numbers
        selected_courses = []  # list to store selected courses
        for course_number in course_numbers:
            try:
                course_index = int(course_number.strip()) - 1
                # convert course number to index and subtract 1 to match array index
                if 0 <= course_index < len(self.available_courses):
                    # check if the course index is valid
                    selected_courses.append(self.available_courses[course_index])  # add selected course to the list
                else:
                    print("Invalid course number. Choose a number listed.")
                    # print error message for invalid course number
                    return []
            except ValueError:
                print("Invalid input. Enter numbers separated by commas.")
                # print error message for invalid input format
                return []
        return selected_courses

    def get_updated_grade(self, grade_option):  # Get updated grade based on grade option
        grade_ranges = {"A": "100-90", "B": "89-80", "C": "79-70", "D": "69-60", "E": "59-50", "F": "49-0"}
        # dictionary to map grade options to grade ranges
        return grade_ranges.get(grade_option, "")
        # return the corresponding grade range, or an empty string if the option is invalid

# Main class to run the program
if __name__ == "__main__":
    school = School()  # create a new school object

    # Main loop for the School
    while True:
        # Menu for user actions
        print("Choose an action: Enroll Student (A), Hire Teacher (H), Update Student Grade (U), "
              "Filter Students by Grade and Course (F), View School Record (V), Exit (E)")
        action = input().upper()  # read user action from input

        # Exit the program
        if action == "E":
            break  # exit the loop if the user chooses to exit

        # Perform actions based on user input
        if action == "A":
            school.enroll_student()  # execute method to enroll a new student
        elif action == "H":
            school.hire_teacher()  # execute method to hire a new teacher
        elif action == "U":
            school.update_student_grade()  # execute method to update a student's grade
        elif action == "F":
            school.filter_students_by_grade_and_course()  # execute method to filter students by grade and course
        elif action == "V":
            school.view_school_record()  # execute method to view the school record
        else:
            print("Invalid action. Please choose a valid action.")
            # print error message for invalid action