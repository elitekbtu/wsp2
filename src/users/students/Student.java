package users.students;

import courses.Course;
import courses.Mark;
import courses.Transcript;
import enums.UrgencyLevel;
import users.User;
import users.employees.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Student extends User {
    private String studentId;
    private String department;
    private List<Course> enrolledCourses;
    private Map<Teacher, UrgencyLevel> complaints;
    private Transcript transcript;


    {
        this.enrolledCourses = new ArrayList<>();
        this.complaints = new HashMap<>();
    }

    public Student() {
    }

    public Student(String id, String fullname, String email, String password) {
        super(id, fullname, email, password);
    }

    public Student(String id, String fullname, String email, String password, List<Course> enrolledCourses, Transcript transcript) {
        super(id, fullname, email, password);
    }

    public Student(String id, String fullname, String email, String password, List<Course> enrolledCourses) {
    }


    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public Map<Course, Mark>  getTranscript(Student student) {
        return transcript.getCourseMarks();
    }
    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public Map<Teacher, UrgencyLevel> getComplaints() {
        return complaints;
    }

    public void addComplaints(Teacher teacher, UrgencyLevel urgencyLevel) {
        this.complaints.put(teacher, urgencyLevel);
    }

    public double getGPA() {
        return 0;
    }
    public String getDepartment() {
        return "";
    }
    public Transcript getTranscript(){
        return transcript;
    }
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", department='" + department + '\'' +
                ", enrolledCourses=" + enrolledCourses +
                ", complaints=" + complaints +
                "} " + super.toString();
    }
}
