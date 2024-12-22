package users.employees;

import courses.Course;
import courses.TypeOfMark;
import enums.UrgencyLevel;
import users.students.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends Employee {
    private List<Course> courses;
    private List<File> courseFiles;

    {
        courses = new ArrayList<>();
        courseFiles = new ArrayList<>();
    }

    // Конструкторы
    public Teacher(String id, String fullname, String department) {
        super(id, fullname, department);
    }

    public Teacher(String id, String fullname, String email, String password, String department) {
        super(id, fullname, email, password, department);
    }

    public Teacher(String id, String fullname, String email, String password, String department, List<Course> courses) {
        super(id, fullname, email, password, department);
        if (courses != null) {
            this.courses = new ArrayList<>(courses);
        }
    }

    // Методы работы с курсами
    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
        } else {
            throw new IllegalArgumentException("Курс не может быть null");
        }
    }

    public void removeCourse(Course course) {
        if (course != null && courses.contains(course)) {
            courses.remove(course);
        } else {
            throw new IllegalArgumentException("Курс не найден или null");
        }
    }

    public Course findCourseByName(String courseName) {
        if (courseName == null || courseName.isEmpty()) {
            throw new IllegalArgumentException("Название курса не может быть пустым");
        }
        return courses.stream()
                .filter(course -> course.getCourseName().equalsIgnoreCase(courseName))
                .findFirst()
                .orElse(null);
    }

    // Методы работы с жалобами
    public void sendComplaintAboutStudent(Student student, UrgencyLevel level) {
        if (student != null && level != null) {
            student.addComplaints(this, level);
        } else {
            throw new IllegalArgumentException("Студент и уровень срочности не могут быть null");
        }
    }

    // Методы работы с файлами
    public List<File> getCourseFiles() {
        return Collections.unmodifiableList(courseFiles);
    }

    public void addCourseFile(File file) {
        if (file != null && file.exists()) {
            courseFiles.add(file);
        } else {
            throw new IllegalArgumentException("Файл null или не существует");
        }
    }

    public void setCourseFiles(List<File> courseFiles) {
        if (courseFiles != null) {
            this.courseFiles = new ArrayList<>(courseFiles);
        } else {
            throw new IllegalArgumentException("Список файлов не может быть null");
        }
    }
    public void setMark(Course course, TypeOfMark typeOfMark, Student student, double points) {
        List <Student> studentList = course.getStudents();
        for(Student st : studentList) {
            if(st.equals(student)) {
                course.putMark(st, points, typeOfMark);
                break;
            }
        }

    }

    // Переопределение toString
    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + getId() + '\'' +
                ", fullname='" + getFullname() + '\'' +
                ", department='" + getDepartment() + '\'' +
                ", courses=" + courses.stream().map(Course::getCourseName).toList() +
                '}';
    }

}