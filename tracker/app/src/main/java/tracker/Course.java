package tracker;

public class Course {
    private String name;
    private int point;

    public Course(String name) {
        this.name = name;
        this.point = 0;
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
}
