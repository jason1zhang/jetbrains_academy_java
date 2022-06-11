package tracker;

public class CourseStudent {
    private String courseName;
    private String studentID;
    private int studentPoint;
    private final int totalPoint;
    
    public CourseStudent(String courseName, String studentID, int studentPoint, int totalPoint) {
        this.courseName = courseName;
        this.studentID = studentID;
        this.studentPoint = studentPoint;
        this.totalPoint = totalPoint;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getStudentPoint() {
        return this.studentPoint;
    }

    public void setStudentPoint(int studentPoint) {
        this.studentPoint = studentPoint;
    }

    @Override
    public String toString() {
        return String.format("%s     %-7d  %7.1f%%", this.studentID, this.studentPoint, this.studentPoint * 100.0 / this.totalPoint);
    }
}