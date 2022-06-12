package tracker;

public class Course {
    private String name;
    private final int totalPoint;
    private int point;
    private int activity;
    private boolean isCompleted;
    private boolean isNotified;

    public Course(String name, int totalPoint) {
        this.name = name;
        this.totalPoint = totalPoint;
        this.point = 0;
        this.activity = 0;
        this.isCompleted = false;
        this.isNotified = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getActivity() {
        return this.activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isNotified() {
        return this.isNotified;
    }

    public void setNotified(boolean isNotified) {
        this.isNotified = isNotified;
    }

    public static String getCourseName(int index) {
        switch (index) {
            case 0:
                return "Java";
            case 1:
                return "DSA";
            case 2:
                return "Databases";
            case 3:
                return "Spring";
            default:
                return "Unknown course";
        }
    }
}
