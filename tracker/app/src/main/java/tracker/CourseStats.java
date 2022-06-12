package tracker;

import java.util.*;

public class CourseStats {
    private final List<Student> students;

    public CourseStats(List<Student> students) {
        this.students = students;
    }

    public void getStats() {
        int[] courseTaken = new int[4];
        int[] activityTaken = new int[4];
        int[] pointTaken = new int[4];
        double[] avgPoint = new double[4];

        for (Student student : this.students) {
            List<Course> courses = student.getCourses();
            for (int i = 0; i < 4; i++) {
                if (courses.get(i).getPoint() > 0) {
                    courseTaken[i]++;
                    activityTaken[i] += courses.get(i).getActivity();
                    pointTaken[i] += courses.get(i).getPoint();
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            avgPoint[i] = activityTaken[i] == 0 ? activityTaken[i] : pointTaken[i] * 1.0 / activityTaken[i];
        }

        displayStats(courseTaken, "popular");
        displayStats(activityTaken, "activity");
        displayStats(avgPoint);
    }

    private void displayStats(int[] arrCourse, String flag) {
        int minCount = Integer.MAX_VALUE;
        int maxCount = Integer.MIN_VALUE;

        for (int j : arrCourse) {
            minCount = Math.min(j, minCount);
            maxCount = Math.max(j, maxCount);
        }

        if (minCount == 0 && maxCount == 0) {
            switch (flag) {
                case "popular":
                    System.out.println("Most popular: n/a");
                    System.out.println("Least popular: n/a");
                    return;

                case "activity":
                    System.out.println("Highest activity: n/a");
                    System.out.println("Lowest activity: n/a");
                    return;
            }
        }

        ArrayList<Integer> arrMostPopular = new ArrayList<>();
        ArrayList<Integer> arrLeastPopular = new ArrayList<>();
        for (int i = 0; i < arrCourse.length; i++) {
            if (arrCourse[i] == maxCount) {
                arrMostPopular.add(i);
            } else if (arrCourse[i] == minCount) {
                arrLeastPopular.add(i);
            }
        }

        switch (flag) {
            case "popular":
                System.out.print("Most popular: ");
                break;

            case "activity":
                System.out.print("Highest activity: ");
                break;

            default:
                break;
        }

        for (int i = 0; i < arrMostPopular.size(); i++) {
            if (i < (arrMostPopular.size() - 1)) {
                System.out.print(Course.getCourseName(arrMostPopular.get(i)) + ", ");
            } else {
                System.out.println(Course.getCourseName(arrMostPopular.get(i)));
            }
        }

        switch (flag) {
            case "popular":
                System.out.print("Least popular: ");
                break;

            case "activity":
                System.out.print("Lowest activity: ");
                break;

            default:
                break;
        }

        if (arrLeastPopular.size() == 0) {
            System.out.print("n/a\n");
        } else {
            for (int i = 0; i < arrLeastPopular.size(); i++) {
                if (i < (arrLeastPopular.size() - 1)) {
                    System.out.print(Course.getCourseName(arrLeastPopular.get(i)) + ", ");
                } else {
                    System.out.println(Course.getCourseName(arrLeastPopular.get(i)));
                }
            }
        }
    }

    private void displayStats(double[] arrCourse) {
        double minCount = Double.MAX_VALUE;
        double maxCount = Double.MIN_VALUE;

        for (double v : arrCourse) {
            minCount = Math.min(v, minCount);
            maxCount = Math.max(v, maxCount);
        }

        if (Math.abs(minCount - 0.0) < 1e-6 && Math.abs(maxCount - 0.0) < 1e-6) {
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
            return;
        }

        ArrayList<Integer> arrMostPopular = new ArrayList<>();
        ArrayList<Integer> arrLeastPopular = new ArrayList<>();
        for (int i = 0; i < arrCourse.length; i++) {
            if (arrCourse[i] == maxCount) {
                arrMostPopular.add(i);
            } else if (arrCourse[i] == minCount) {
                arrLeastPopular.add(i);
            }
        }

        System.out.print("Easiest course: ");

        for (int i = 0; i < arrMostPopular.size(); i++) {
            if (i < (arrMostPopular.size() - 1)) {
                System.out.print(Course.getCourseName(arrMostPopular.get(i)) + ", ");
            } else {
                System.out.println(Course.getCourseName(arrMostPopular.get(i)));
            }
        }

        System.out.print("Hardest course: ");

        if (arrLeastPopular.size() == 0) {
            System.out.print("n/a\n");
        } else {
            for (int i = 0; i < arrLeastPopular.size(); i++) {
                if (i < (arrLeastPopular.size() - 1)) {
                    System.out.print(Course.getCourseName(arrLeastPopular.get(i)) + ", ");
                } else {
                    System.out.println(Course.getCourseName(arrLeastPopular.get(i)));
                }
            }
        }
    }

    public void displayCourseStats(String courseName) {
        List<CourseStudent> courseStudents = new ArrayList<>();

        for (Student student : this.students) {
            for (Course course : student.getCourses()) {
                if (course.getName().toLowerCase().equals(courseName.toLowerCase()) && course.getPoint() != 0) {
                    CourseStudent cs = new CourseStudent(course.getName(), student.getId(), course.getPoint(), course.getTotalPoint());
                    courseStudents.add(cs);
                    break;
                }
            }
        }

        courseStudents.sort(Comparator.comparing(CourseStudent::getStudentPoint)
                .reversed()
                .thenComparing(CourseStudent::getStudentID));

        System.out.println(courseName.substring(0, 1).toUpperCase() + courseName.substring(1));
        System.out.print("id        points       completed\n");

        courseStudents.forEach(System.out::println);
    }
}
