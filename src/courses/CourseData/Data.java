package courses.CourseData;

public class Data {
    String code;
    String Discipline;
    int Ects;
    public static final Data[]availableCourses={
            new Data("CSC1103","Programming Principles 1",6),
            new Data("MATH1102","Calculus 1",5 ),
            new Data("CSCI1102","Discrete Structures",5),
            new Data("LAN1180","English 1 ",10),
            new Data("CSCI1204","Programming Principles 2",6),
            new Data("FUN1105","Physics 1",5),
            new Data("CSCI2104","Databases",5),
            new Data("CSCI2105","Algorithms and Data Structures",5),
            new Data("CSCI2106","Object-Oriented Programming",5),
            new Data("PHE101","Physical Education 1",4),
            new Data("LAN1115","Kazakh/Russian Language",5),
            new Data("CSCI3115","Computer Architecture",5),
            new Data("HUM1101","History Kazakhstan",5),
            new Data("MNG 1201","Introduction Management",5)

    };
    public Data(String code, String Discipline, int Ects) {
        this.code = code;
        this.Discipline = Discipline;
        this.Ects = Ects;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDiscipline() {
        return Discipline;
    }
    public void setDiscipline(String Discipline) {
        this.Discipline = Discipline;
    }
    public int getEcts() {
        return Ects;
    }
    public void setEcts(int Ects) {
        this.Ects = Ects;
    }
    public static Data[] getAvailableCourses() {
        return availableCourses;
    }





}
