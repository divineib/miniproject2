class Teacher:
   def __init__(self, name):
       self.name = name
       self.students = {} # StudentName -> CourseID -> Grade

   def update_student_grade(self, student_name, course_id, grade):
       if student_name not in self.students:
           self.students[student_name] = {}
       self.students[student_name][course_id] = grade

   def filter_students_by_grade_and_course(self, grade, course_id):
       for student_name, grades in self.students.items():
           if course_id in grades and grades[course_id] == grade:
               print(f"Student: {student_name} - Grade: {grade} in course: {course_id}")

class Student:
   def __init__(self, name):
       self.name = name
       self.grades = {} # CourseID -> TeacherName

   def view_grades(self):
       print(f"Grades for {self.name}:")
       for course_id, teacher_name in self.grades.items():
           print(f"{course_id} - {teacher_name}")

   def view_teacher_by_course_id(self, course_id, teacher):
       print(f"Teachers for course {course_id}:")
       for student_name, grades in teacher.students.items():
           if course_id in grades and grades[course_id] == self.name:
               print(f"Student: {student_name} - Teacher: {teacher.name}")

class SchoolRecord:
   def __init__(self):
       self.changes = []

   def add_change(self, change):
       self.changes.append(change)
       print(f"Record change: {change}")

   def view_changes(self):
       print("School Record Changes:")
       for change in self.changes:
           print(change)


class School:
   def __init__(self):
       self.students = []
       self.teachers = []
       self.school_record = SchoolRecord()

   def enroll_student(self, student_name, course_id):
       student = Student(student_name)
       self.students.append(student)
       for teacher in self.teachers:
           student.view_teacher_by_course_id(course_id, teacher)
       self.school_record.add_change(f"Enrolled student: {student_name} in course: {course_id}")

   def hire_teacher(self, teacher_name, course_id):
       teacher = Teacher(teacher_name)
       self.teachers.append(teacher)
       self.school_record.add_change(f"Hired teacher: {teacher_name} for course: {course_id}")

   def update_student_grade(self, student_name, course_id, grade):
       for teacher in self.teachers:
           teacher.update_student_grade(student_name, course_id, grade)
       self.school_record.add_change(f"Updated grade for student: {student_name} in course: {course_id} to {grade}")

   def filter_students_by_grade_and_course(self, grade, course_id):
       for teacher in self.teachers:
           teacher.filter_students_by_grade_and_course(grade, course_id)

   def view_school_record(self):
       self.school_record.view_changes()


def main():
   school = School()

   while True:
       action = input("Choose an action: Enroll Student (A), Hire Teacher (H), Update Student Grade (U), Filter Students by Grade and Course (F), View School Record (V), or Exit (E): ")
       if action.lower() == "a":
           student_name = input("Enter student name: ")
           course_id = input("Enter course ID: ")
           school.enroll_student(student_name, course_id)
       elif action.lower() == "h":
           teacher_name = input("Enter teacher name: ")
           course_id = input("Enter course ID: ")
           school.hire_teacher(teacher_name, course_id)
       elif action.lower() == "u":
           student_name = input("Enter student name: ")
           course_id = input("Enter course ID: ")
           grade = input("Enter grade: ")
           school.update_student_grade(student_name, course_id, grade)
       elif action.lower() == "f":
           grade = input("Enter grade: ")
           course_id = input("Enter course ID: ")
           school.filter_students_by_grade_and_course(grade, course_id)
       elif action.lower() == "v":
           school.view_school_record()
       elif action.lower() == "e":
           break
       else:
           print("Invalid action. Please try again.")

if __name__ == "__main__":
   main()