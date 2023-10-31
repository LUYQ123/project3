import java.util.*;

public class GIFT_CH {
    protected static Point getLeftMostPoint(List<Point> points) {

        Point lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            Point temp = points.get(i);

            if(temp.x < lowest.x || (temp.x == lowest.x && temp.y < lowest.y)) {
                lowest = temp;
            }
        }

        return lowest;
    }

    protected static Point getRightMostPoint(List<Point> points) {

        Point lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            Point temp = points.get(i);

            if(temp.x > lowest.x || (temp.x == lowest.x && temp.y < lowest.y)) {
                lowest = temp;
            }
        }

        return lowest;
    }


    public static List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {
        if(points.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if(INC_CH.areAllCollinear(points)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }
        Stack<Point> stack = new Stack<Point>();
        Point ipoint=getLeftMostPoint(points);
        Point epoint=getRightMostPoint(points);
        Point point=ipoint;
        stack.push(ipoint);
        Point vec=new Point(0,1);
        Point temp_point=ipoint;
        Long start= System.currentTimeMillis();

        double epsilon=0.00000000001;
        do {
            double temp_cos=-1;
            for (int i = 0; i < points.size(); i++) {
                Point tpoint = points.get(i);
                if (!tpoint.equals(point)) {
//                    double a=(tpoint.y-point.y)*vec.y+(tpoint.x-point.x)* vec.x;
//                    double b=Math.sqrt(vec.x*vec.x+vec.y*vec.y);
//                    double c=Math.sqrt((tpoint.y-point.y)*(tpoint.y-point.y)+(tpoint.x-point.x)*(tpoint.x-point.x));
//                    double cos= a/(b*c);
                    double cos = ((tpoint.y-point.y)*vec.y+(tpoint.x-point.x)* vec.x)
                            /(Math.sqrt(vec.x*vec.x+vec.y*vec.y)
                              *Math.sqrt((tpoint.y-point.y)*(tpoint.y-point.y)+(tpoint.x-point.x)*(tpoint.x-point.x)));
                    if (cos>temp_cos || (Math.abs(cos-temp_cos)<=epsilon && Math.abs(tpoint.x-point.x)>Math.abs(temp_point.x-point.x))) {
                            temp_cos = cos;
                            temp_point = tpoint;
                    }
                }
            }
            vec.y=temp_point.y-point.y;
            vec.x=temp_point.x-point.x;
            point=temp_point;
            stack.push(temp_point);
//            System.out.println(temp_point.x+" "+temp_point.y);
        }while(!temp_point.equals(epoint));
        Long end= System.currentTimeMillis();
        System.out.println("Gift warping processing time "+(end-start)+" ms");
        return new ArrayList<Point>(stack);
    }
}
