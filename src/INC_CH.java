import java.util.*;

/**
 *
 */
public final class INC_CH {

    /**
     * An enum denoting a directional-turn between 3 points (vectors).
     */

    static private final double eplison = 0.000000001;
    protected static enum Turn { CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR }

    /**
     * Returns true iff all points in <code>points</code> are collinear.
     *
     * @param points the list of points.
     * @return       true iff all points in <code>points</code> are collinear.
     */
    protected static boolean areAllCollinear(List<Point> points) {

        if(points.size() < 2) {
            return true;
        }

        final Point a = points.get(0);
        final Point b = points.get(1);

        for(int i = 2; i < points.size(); i++) {

            Point c = points.get(i);

            if(getTurn(a, b, c) != Turn.COLLINEAR) {
                return false;
            }
        }

        return true;
    }

//    public static List<Point> getConvexHull(int[] xs, int[] ys) throws IllegalArgumentException {
//
//        if(xs.length != ys.length) {
//            throw new IllegalArgumentException("xs and ys don't have the same size");
//        }
//
//        List<Point> points = new ArrayList<Point>();
//
//        for(int i = 0; i < xs.length; i++) {
//            points.add(new Point(xs[i], ys[i]));
//        }
//
//        return getConvexHull(points);
//    }

    /**
     * Returns the convex hull of the points created from the list
     * <code>points</code>. Note that the first and last point in the
     * returned <code>List&lt;java.awt.Point&gt;</code> are the same
     * point.
     *
     * @param points the list of points.
     * @return       the convex hull of the points created from the list
     *               <code>points</code>.
     * @throws IllegalArgumentException if all points are collinear or if there
     *                                  are less than 3 unique points present.
     */
    public static List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {

        Long start= System.currentTimeMillis();
        List<Point> sorted = new ArrayList<Point>(getSortedPointSet(points));
        final Point rightest = getRightMostPoint(points);

        Long end= System.currentTimeMillis();
        System.out.println("Graham scan sorting time "+(end-start)+" ms");
        if(sorted.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if(areAllCollinear(sorted)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }



        Stack<Point> stack = new Stack<Point>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            if(stack.peek().equals(rightest))
                break;

            Point head = sorted.get(i);
            Point middle = stack.pop();
            Point tail = stack.peek();

            Turn turn = getTurn(tail, middle, head);


            switch(turn) {
                case COUNTER_CLOCKWISE:
                    i--;
                    break;
                case CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }


//            System.out.println(i+" "+stack.size()+" "+turn);
        }

        // close the hull
        //stack.push(sorted.get(0));
        start= System.currentTimeMillis();
        System.out.println("Graham scan processing time "+(start-end)+" ms");
        return new ArrayList<Point>(stack);
    }

    /**
     * Returns the points with the lowest y coordinate. In case more than 1 such
     * point exists, the one with the lowest x coordinate is returned.
     *
     * @param points the list of points to return the lowest point from.
     * @return       the points with the lowest y coordinate. In case more than
     *               1 such point exists, the one with the lowest x coordinate
     *               is returned.
     */
    protected static Point getLeftMostPoint(List<Point> points) {

        Point lowest = points.get(0);



        for(int i = 1; i < points.size(); i++) {

            Point temp = points.get(i);

            if(temp.x < lowest.x || (temp.x == lowest.x && temp.y < lowest.y)) {
                lowest = temp;
            }
        }
       // System.out.println("abc "+lowest.x+" "+lowest.y);

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

    /**
     * Returns a sorted set of points from the list <code>points</code>. The
     * set of points are sorted in increasing order of the angle they and the
     * lowest point <tt>P</tt> make with the x-axis. If tow (or more) points
     * form the same angle towards <tt>P</tt>, the one closest to <tt>P</tt>
     * comes first.
     *
     * @param points the list of points to sort.
     * @return       a sorted set of points from the list <code>points</code>.
     */
    protected static Set<Point> getSortedPointSet(List<Point> points) {

        final Point lowest = getLeftMostPoint(points);


//        System.out.println(lowest.x+" "+lowest.y);

        TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {

                double theta=-Math.PI/2;
                if(a == b || a.equals(b)) {
                    return 0;
                }

                if(a.equals(lowest))
                    return -1;
                if(b.equals(lowest))
                    return 1;

                // use longs to guard against int-underflow
                double thetaA = Math.atan2(a.y - lowest.y, a.x - lowest.x);
                //atan2 function to get the theta of point a to the lowest anchor point
                double thetaB = Math.atan2(b.y - lowest.y, b.x - lowest.x);

                if((theta- thetaA) > (theta- thetaB) ) {
                    return 1;
                }
                else if((-theta- thetaA) < (-theta- thetaB)) {
                    return -1;
                }
                else {
                    // collinear with the 'lowest' point, let the point closest to it come first

                    // use longs to guard against int-over/underflow
                    double distanceA = Math.sqrt((((long)lowest.x - a.x) * ((long)lowest.x - a.x)) +
                            (((long)lowest.y - a.y) * ((long)lowest.y - a.y)));
                    double distanceB = Math.sqrt((((long)lowest.x - b.x) * ((long)lowest.x - b.x)) +
                            (((long)lowest.y - b.y) * ((long)lowest.y - b.y)));

                    if(distanceA < distanceB) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            }
        });

        set.addAll(points);

        return set;
    }

    /**
     * Returns the GrahamScan#Turn formed by traversing through the
     * ordered points <code>a</code>, <code>b</code> and <code>c</code>.
     * More specifically, the cross product <tt>C</tt> between the
     * 3 points (vectors) is calculated:
     *
     * <tt>(b.x-a.x * c.y-a.y) - (b.y-a.y * c.x-a.x)</tt>
     *
     * and if <tt>C</tt> is less than 0, the turn is CLOCKWISE, if
     * <tt>C</tt> is more than 0, the turn is COUNTER_CLOCKWISE, else
     * the three points are COLLINEAR.
     *
     * @param a the starting point.
     * @param b the second point.
     * @param c the end point.
     * @return the GrahamScan#Turn formed by traversing through the
     *         ordered points <code>a</code>, <code>b</code> and
     *         <code>c</code>.
     */
    protected static Turn getTurn(Point a, Point b, Point c) {

        // use longs to guard against int-over/underflow
        double crossProduct = (b.x - a.x) * (c.y - a.y) -
                ((b.y - a.y) * (c.x - a.x));

//        System.out.println("crossProduct "+crossProduct);

        if(crossProduct < -eplison) {
            return Turn.CLOCKWISE;
        }
        else if(crossProduct > eplison) {
            return Turn.COUNTER_CLOCKWISE;
        }
        else {
            return Turn.COLLINEAR;
        }
    }
}