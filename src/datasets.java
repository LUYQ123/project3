import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class datasets {
    static class Circle_sample {
        double radius;
        double x_center;
        double y_center;
        int num;
        ArrayList<Point> sample;
        Random ran;

        public Circle_sample(double radius, double x_center, double y_center,int num) {
            this.radius = radius;
            this.x_center = x_center;
            this.y_center = y_center;
            this.num=num;
            ran=new Random();
            randPoint();
        }

        public void randPoint() {
            sample=new ArrayList<>();
            for(int i=0;i<num;i++){
                double r = Math.sqrt(ran.nextDouble() ) * radius, angle = ran.nextDouble() * 2 * Math.PI;
                sample.add(new Point(r * Math.cos(angle) + x_center,r * Math.sin(angle) + y_center));
            }
        }
    }

    static class Square_sample {
        double left_corner_x;
        double left_corner_y;
        double height;
        double width;
        int num;

        Random ran;
        ArrayList<Point> sample;

        public Square_sample(double left_corner_x, double left_corner_y, double height,double width, int num) {
            this.left_corner_x = left_corner_x;
            this.left_corner_y = left_corner_y;
            this.height = height;
            this.width = width;
            this.num=num;
            ran=new Random();
            randPoint();
        }

        public void randPoint() {
            sample = new ArrayList<>();
            for(int i=0;i<num;i++){
                sample.add(new Point(ran.nextDouble() * width + left_corner_x, ran.nextDouble() * height + left_corner_y));
            }
        }
    }

    static class Third_sample {
        int num;
        ArrayList<Point> sample;

        public Third_sample(int num) {
            this.num=num;
            randPoint();
        }

        public void randPoint() {
            sample = new ArrayList<>();
            double d=Math.random();
            sample.add(new Point(d,d*d));
            for(int i=1;i<num;i++){
                d=sample.get(i-1).x+Math.random();
                sample.add(new Point(d,d*d));
            }
        }
    }

    static class Forth_sample {
        int num;
        ArrayList<Point> sample;

        public Forth_sample(int num) {
            this.num=num;
            randPoint();
        }

        public void randPoint() {
            sample = new ArrayList<>();
            double d=Math.random();
            sample.add(new Point(d,d*d));
            for(int i=1;i<num;i++){
                d=sample.get(i-1).x+Math.random();
                sample.add(new Point(d,-d*d));
            }
        }
    }

}