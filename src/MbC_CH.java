import java.util.*;

public class MbC_CH {
    static private final double eplison = 0.0000001;
    static List<Point> upcv=new ArrayList<>();

    static public void run(List<Point> pl,List<Point> pr){
        if(pl.size()==2){
            upcv.add(pl.get(0));
            upcv.add(pl.get(1));
        }
        if(pl.size()==1) {
            upcv.add(pl.get(0));
        }
        else if(pl.size()>2){
            int index= (int)(Math.random()*pl.size());
            //System.out.println("pl "+index);
            double standard;
            if(index==0)
                standard=(pl.get(index).x+pl.get(index+1).x+pl.get(index+2).x)/3;
            else if(index==pl.size()-1) {
                standard = (pl.get(index - 2).x + pl.get(index).x + pl.get(index - 1).x) / 3;
            }
            else{
                standard = (pl.get(index - 1).x + pl.get(index).x + pl.get(index + 1).x) / 3;
            }
            List<List<Point>> tmp=partition(pl,standard);
            List<Point> bridge=getBridge(tmp.get(0),tmp.get(1), new Point(standard,0));
            for(Point p:bridge)
                upcv.add(p);
//            upcv.add(bridge.get(0));
//            upcv.add(bridge.get(1));
            run(PruneLeft(bridge,tmp.get(0)) ,PruneRight(bridge,tmp.get(1)));
        }

        if(pr.size()==2) {
            upcv.add(pr.get(0));
            upcv.add(pr.get(1));
        }
        if(pr.size()==1) {
            upcv.add(pr.get(0));
        }
        else if(pr.size()>2){
            //int index=0;
            int index=(int)(Math.random()*pr.size());
            double standard;
            //System.out.println("pr "+index);
            if(index==0)
                standard=(pr.get(index).x+pr.get(index+1).x+pr.get(index+2).x)/3;
            else if(index==pr.size()-1){
                standard=(pr.get(index).x+pr.get(index-1).x+pr.get(index-2).x)/3;
            }
            else {
                standard = (+pr.get(index+1).x+pr.get(index).x + pr.get(index - 1).x) / 3;
            }
            List<List<Point>> tmp=partition(pr,standard);
            List<Point> bridge=getBridge(tmp.get(0),tmp.get(1), new Point(standard,0));
            for(Point p:bridge)
                upcv.add(p);
//            upcv.add(bridge.get(0));
//            upcv.add(bridge.get(1));
            run(PruneLeft(bridge,tmp.get(0)) ,PruneRight(bridge,tmp.get(1)));
        }
    }

    static public List<List<Point>> partition(List<Point> datas, double standard){
        List<Point> pr=new ArrayList<>();
        List<Point> pl=new ArrayList<>();
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).x<standard)
                pl.add(datas.get(i));
            else if (datas.get(i).x>standard) {
                pr.add(datas.get(i));
            }
        }
        List<List<Point>> lst=new ArrayList<>();
        lst.add(pl);
        lst.add(pr);
        return lst;
    }

    public static Point OneDLP(List<Point> cons, Point pivit, Point line){
        double bigger= Math.sqrt(Double.MAX_VALUE)  ,smaller=-Math.sqrt(Double.MAX_VALUE) ;
        if(pivit.x>=line.x){
            for(int i=0;i<cons.size();i++){
                if(cons.get(i).x> line.x){
                    smaller=Double.max(smaller, (cons.get(i).y-line.y)/(cons.get(i).x-line.x));
                }
            }
            return new Point(smaller,line.y-line.x*smaller);
        }

        if(pivit.x<line.x){
            for(int i=0;i<cons.size();i++){
                if(cons.get(i).x< line.x){
                    bigger=Double.min(bigger, (cons.get(i).y-line.y)/(cons.get(i).x-line.x));
                }
            }
            return new Point(bigger,line.y-line.x*bigger);
        }
        return null;
    }


    static List<Point> random_shuffle(List<Point> pl,List<Point> pr){
        boolean[] chosen=new boolean[pl.size()+pr.size()];
        for(boolean b:chosen)
            b=false;
        long cons=0;
        List<Point> result=new ArrayList<>();
        while(cons<(pl.size()+pr.size())){
            int num=(int)(Math.random()*pl.size()+pr.size());
        }
        return result;
    }

    static public List<Point> getBridge(List<Point> pl,List<Point> pr, Point pivit) {
        double a=0,b=-(Double.MAX_VALUE/(1<<100));
        List<Point> constriants =new ArrayList<>();
        for(Point p:pl){
            constriants.add(p);
            if(a*p.x+b<=p.y){
                Point tmp=OneDLP(constriants,pivit,p);
                a=tmp.x;
                b=tmp.y;
            }
        }
        for(Point p:pr){
            constriants.add(p);
            if(a*p.x+b<=p.y){
                Point tmp=OneDLP(constriants,pivit,p);
                a=tmp.x;
                b=tmp.y;
            }
        }
        List<Point> result=new ArrayList<>();
        for(Point p: pl)
            if((a*p.x+b-p.y)<=eplison)
                result.add(p);
        for(Point p: pr)
            if((a*p.x+b-p.y)<=eplison)
                result.add(p);
        return result;
    }

    static public List<Point> PruneLeft(List<Point> bridge, List<Point> pl) {
        double left=bridge.get(0).x;
        for(int i=0;i<pl.size();i++){
            if(pl.get(i).x>=left){
                pl.remove(i);
                i--;
            }
        }
        pl.add(bridge.get(0));
        return pl;
    }

    static public List<Point> PruneRight(List<Point> bridge, List<Point> pr) {
        double right=bridge.get(1).x;
        for(int i=0;i<pr.size();i++){
            if(pr.get(i).x<=right){
                pr.remove(i);
                i--;
            }
        }
        pr.add(bridge.get(1));
        return pr;
    }


    protected static Set<Point> getSortedPointSet(List<Point> points) {
//        System.out.println(lowest.x+" "+lowest.y);

        TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {

                if(a == b || a.equals(b)) {
                    return 0;
                }

                if(a.x<b.x)  {
                    return -1;
                }
                else if(a.x>b.x) {
                    return 1;
                }
                return 0;
            }
        });

        set.addAll(points);

        return set;
    }


    public static List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {
        if (points.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if (INC_CH.areAllCollinear(points)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }
        Long start= System.currentTimeMillis();


        run(points, new ArrayList<>());
        Long end= System.currentTimeMillis();
        System.out.println("MBC time "+(end-start)+" ms");
        List<Point> sorted = new ArrayList<Point>(getSortedPointSet(upcv));

        return sorted;

    }
}
