package users.students;

import courses.Course;

import java.util.List;

public class UndergraduateStudent extends Student {


    public UndergraduateStudent() {
    }

    public UndergraduateStudent(String id, String fullname, String email, String password) {
        super(id, fullname, email, password);
    }

    public UndergraduateStudent(String id, String fullname, String email, String password, List<Course> enrolledCourses) {
        super(id, fullname, email, password, enrolledCourses);
    }
}
