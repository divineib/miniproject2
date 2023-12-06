class SchoolRecord:
    def __init__(self):
        self.students = {}
        self.teachers = {}

    def add_student(self, student):
        self.students[student.name] = student

    def add_teacher(self, teacher):
        self.teachers[teacher.name] = teacher

class Student:
    def __init__(self, name):
        self.name = name
        self.grades = {}

    def view_grades(self):
        return self.grades

    def search_teacher_by_course(self, course_id, school_record):
        if course_id in school_record.teachers:
            teacher = school_record.teachers[course_id]
            return teacher.name
        return None

class Teacher:
    def __init__(self, name):
        self.name = name
        self.courses = {}

    def search_student(self, student_name, course_id, target_grade=None):
        if course_id in self.courses:
            students = self.courses[course_id]
            if student_name in students:
                student_info = students[student_name]
                if target_grade is None or student_info['grade'] == target_grade:
                    return student_info
        return None

    def update_grade(self, student_name, course_id, grade):
        if course_id in self.courses:
            students = self.courses[course_id]
            if student_name in students:
                students[student_name]['grade'] = grade
                return True
        return False

class School:
    @staticmethod
    def enroll(student_name, course_id, school_record):
        student = school_record.students.get(student_name)
        if not student:
            student = Student(student_name)
            school_record.add_student(student)

        teacher = school_record.teachers.get(course_id)
        if teacher:
            if course_id not in student.grades:
                student.grades[course_id] = {'grade': None}
            teacher.courses[course_id][student_name] = {'grade': None}
            print(f"{student_name} enrolled in {course_id}.")
        else:
            print(f"No teacher found for {course_id}.")

    @staticmethod
    def hire(teacher_name, course_id, school_record):
        teacher = school_record.teachers.get(teacher_name)
        if not teacher:
            teacher = Teacher(teacher_name)
            school_record.add_teacher(teacher)

        if course_id not in teacher.courses:
            teacher.courses[course_id] = {}
            print(f"{teacher_name} hired for {course_id}.")
        else:
            print(f"{teacher_name} is already assigned to {course_id}.")

        return teacher

# Create school record
school_record = SchoolRecord()

# Hire teachers for each course
courses = ['math101', 'math102', 'ss101', 'ss102', 'pys101', 'pys102', 'eng101', 'eng102', 'chem101', 'chem102']
teacher_names = ['teacherm','teachers', 'teacherp', 'teachere', 'teacherc']
students = [f'student{i}' for i in range(1, 21)]

for teacher_name, course_id in zip(teacher_names, courses):
    School.hire(teacher_name, course_id, school_record)

# Enroll students
for i, student_name in enumerate(students, start=1):
    course_id = courses[i % len(courses)]  # Assign courses in a cyclic manner
    School.enroll(student_name, course_id, school_record)

# Update grades
for i, student_name in enumerate(students, start=1):
    course_id = courses[i % len(courses)]  # Assign courses in a cyclic manner
    student = school_record.students.get(student_name)
    if student:
        teacher_name = student.search_teacher_by_course(course_id, school_record)
        teacher = school_record.teachers.get(teacher_name)
        if teacher:
            grade = 'A' if i % 2 == 0 else 'B'
            teacher.update_grade(student_name, course_id, grade)
        else:
            print(f"No teacher found for {course_id}.")
    else:
        print(f"No student found with name {student_name}.")


# User Interaction Loop
while True:
    print("Are you a student or a teacher?")
    person = input()
    if all(char in "Tteacher" for char in person):
        print("\n===== School Management System =====")
        print("1. Enroll student")
        print("2. Hire teacher")
        print("3. Update student Grade")
        print("4. Search for student")
        print("0. Exit")
        choice = input()
        if choice == '0':
            print("Exiting the program. Goodbye!")
            break

        elif choice == '1':
            student_name = input("Enter student name: ")
            course_id = input("Enter course ID: ")
            School.enroll(student_name, course_id, school_record)
            print(f"{student_name} enrolled in {course_id}.")

        elif choice == '2':
            teacher_name = input("Enter teacher name: ")
            course_id = input("Enter course ID: ")
            School.hire(teacher_name, course_id, school_record)
            print(f"{teacher_name} hired for {course_id}.")

        elif choice == '3':
            student_name = input("Enter student name: ")
            course_id = input("Enter course ID: ")
            grade = input("Enter the new grade: ")
            student = school_record.students.get(student_name)
            if student:
                teacher_name = student.search_teacher_by_course(course_id, school_record)
                if teacher_name:
                    teacher = school_record.teachers[teacher_name]
                    teacher.update_grade(student_name, course_id, grade)
                    print(f"Grade updated for {student_name} in {course_id}.")
                else:
                    print(f"No teacher found for {course_id}.")
            else:
                print(f"No student found with name {student_name}.")

        elif choice == '4':
            student_name = input("Enter student name: ")
            course_id = input("Enter course ID: ")
            student = school_record.students.get(student_name)
            if student:
                teacher_name = student.search_teacher_by_course(course_id, school_record)
                if teacher_name:
                    print(f"Student {student_name} is enrolled in {course_id} with teacher {teacher_name}.")
                else:
                    print(f"Student {student_name} is not enrolled in {course_id}.")
            else:
                print(f"No student found with name {student_name}.")
        else:
            print("Invalid choice. Please enter a valid option.")
    elif all(char in "Sstudent" for char in person):
        print("\n===== Student Menu =====")
        print("1. Search for teacher")
        print("2. View Grades")
        print("0. Exit")
        choice = input()
        if choice == '0':
            print("Exiting the program. Goodbye!")
            break
        elif choice == '1':
            course_id = input("Enter course ID: ")
            teacher_name = school_record.teachers.get(course_id)
            if teacher_name:
                print(f"The teacher for {course_id} is {teacher_name}.")
            else:
                print(f"No teacher found for {course_id}.")
        elif choice == '2':
            student_name = input("Enter student name: ")
            student = school_record.students.get(student_name)
            if student:
                grades = student.view_grades()
                print(f"Grades for {student_name}: {grades}")
            else:
                print(f"No student found with name {student_name}.")
        else:
            print("Invalid choice. Please enter a valid option.")
