package users.employees;

import analytics.EmployeeRequest;
import  analytics.Report;
import analytics.SortingCriteria;
import  courses.Course;
import enums.NewsTags;
import news.News;
import news.NewsList;
import users.User;
import users.students.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Manager extends Employee {
    public Manager(String id, String fullname, String email, String password){

    }
    // Method to add a course for registration
    public void addCourseForRegistration(Course course) {
        Course.addCourseToRegistry(course);
    }


    public void assignCourseToTeacher(Course course, Teacher teacher) {
        course.addInstructor(teacher);
    }

    // Method to approve student registration for a course
    public void approveStudentRegistration(Course course, Student student) {
        course.addStudent(student);
        student.enrollCourse(course);
    }

    public void approveStudentsRegistration(Course course, Student... students) {
        for (Student student : students) {
            approveStudentRegistration(course, student);
        }
    }

    public Manager(String id, String fullname, String email, String password, String department) {
        super(id, fullname, email, password, department);
    }

    // Method to create a statistical report on academic performance
    public Report createAcademicPerformanceReport(String title, String... content) {
        return new Report(title, content);
    }



    public <T extends Student> Vector<T> viewStudentInformation(Class<T> studentType, SortingCriteria criteria) {
        Vector<T> sortedStudents = User.getAllUsers(studentType);
        switch (criteria) {
            case GPA:
                sortedStudents.sort(Comparator.comparing(Student::getGPA));
                break;
            case ALPHABETICAL:
                sortedStudents.sort(Comparator.comparing(Student::getName));
                break;
            case DEPARTMENT:
                sortedStudents.sort(Comparator.comparing(Student::getDepartment));
                break;
        }
        return sortedStudents;
    }

    // Method to view information about teachers
    public List<Teacher> viewTeacherInformation(SortingCriteria criteria) {
        Vector<Teacher> sortedStudents = User.getAllUsers(Teacher.class);
        switch (criteria) {
            case ALPHABETICAL:
                sortedStudents.sort(Comparator.comparing(Teacher::getName));
                break;
            case DEPARTMENT:
                sortedStudents.sort(Comparator.comparing(Teacher::getDepartment));
                break;
        }
        return sortedStudents;
    }

    // Method to manage news, particularly academic news
    public void addNews(NewsList newsList, String title, String content, NewsTags newsTag) {
        newsList.add(new News(title, content, newsTag));
    }

    // Method to view and sign off on requests from employees
    public void viewAndSignOffRequests(EmployeeRequest request) {

    }

    // Method to update course details
    public void updateCourseDetails(String courseId) {

    }

    // Method to remove a course from the system
    public void removeCourse(Course course) {
        Course.removeCourseFromRegistry(course);
    }

    @Override
    public String toString() {
        return "Manager{} " + super.toString();
    }
}
