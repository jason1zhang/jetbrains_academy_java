/**
 * Abstract class Shape hierarchy
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-17
 * 
 * You have an abstract class Shape with two abstract methods: getPerimeter() and getArea(). See the provided code template.
 *
 * You need to declare and implement three classes: Triangle, Rectangle and Circle. The classes must extend the Shape class
 * and implement all abstract methods. To implement the methods the standard class Math may help you.
 *
 * The class Triangle must have a constructor with three double arguments for setting the length of each side. To calculate
 * the area of this shape you may use Heron's formula.
 * The class Rectangle must have a constructor with two double arguments for setting the length of different sides.
 * The class Circle must have a constructor with one double argument for setting the radius. You may use Math.PI as the PI
 * constant or you can declare it yourself.
 *
 * Note: do NOT make your classes public.
 *
 * Examples
 *
 * A circle has a radius = 10: perimeter = 62.831853..., area = 314.159265....
 *
 * A triangle has three sides a = 3, b = 4 and c = 5: perimeter = 12.0, area = 6.0.
 *
 * A rectangle has two different sides a = 5, b = 10: perimeter = 30.0, area = 50.0.
 */

abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double getPerimeter() {
        return this.a + this.b + this.c;
    }

    @Override
    double getArea() {
        double s = this.getPerimeter() / 2;
        return Math.sqrt(s * (s - this.a) * (s - this.b) * (s - this.c));
    }
}

class Rectangle extends Shape {
    private double a;
    private double b;

    Rectangle(double a, double b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    double getPerimeter() {
        return 2 * (this.a + this.b);
    }

    @Override
    double getArea() {
        return this.a * this.b;
    }
}

class Circle extends Shape {
    private double r;

    public Circle(double r) {
        super();
        this.r = r;
    }

    @Override
    double getPerimeter() {
        return 2 * Math.PI * this.r;
    }

    @Override
    double getArea() {
        return Math.PI * Math.pow(this.r, 2);
    }
}