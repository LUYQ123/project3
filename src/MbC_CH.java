import java.util.*;

public class MbC_CH {
    static private final double eplison = 0.0000001;

    static private double random_suffle_time= 0;
    static private double get_bridge_time= 0;

    static private final double Cons= Math.sqrt(Double.MAX_VALUE);
    static List<Point> upcv=new ArrayList<>();

    static public void run(List<Point> pl,List<Point> pr){
        if(pl.size()==2){
            upcv.add(pl.get(0));
            upcv.add(pl.get(1));
        }
        else if(pl.size()==1) {
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
//            for(Point p:bridge)
//                upcv.add(p);
            upcv.add(bridge.get(0));
            upcv.add(bridge.get(1));
            run(PruneLeft(bridge,tmp.get(0)) ,PruneRight(bridge,tmp.get(1)));
        }

        if(pr.size()==2) {
            upcv.add(pr.get(0));
            upcv.add(pr.get(1));
        }
        else if(pr.size()==1) {
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
        List<Point> pr=new ArrayList<>(datas.size());
        List<Point> pl=new ArrayList<>(datas.size());
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).x<standard)
                pl.add(datas.get(i));
            else if (datas.get(i).x>standard) {
                pr.add(datas.get(i));
            }
        }
        List<List<Point>> lst=new ArrayList<>(2);
        lst.add(pl);
        lst.add(pr);
        return lst;
    }

    public static List<Point> OneDLP(List<Point> cons, Point pivit, Point line){
        double bigger= Cons  ,smaller=-Cons;
        Point p=new Point(pivit.x,smaller);
        List<Point> result=new ArrayList<>();
        if(pivit.x>=line.x){
            for(int i=0;i<cons.size();i++){
                if(cons.get(i).x> line.x){
                    double tmp=(cons.get(i).y-line.y)/(cons.get(i).x-line.x);
                    if(tmp>smaller){
                        smaller=tmp;
                        p=cons.get(i);
                    }
                }
            }
            result.add(new  Point(smaller,line.y-line.x*smaller));

        }

        if(pivit.x<line.x){
            for(int i=0;i<cons.size();i++){
                if(cons.get(i).x< line.x){
                    double tmp=(cons.get(i).y-line.y)/(cons.get(i).x-line.x);
                    if(tmp<bigger){
                        bigger=tmp;
                        p=cons.get(i);
                    }
                }
            }
            result.add(new  Point(bigger,line.y-line.x*bigger));
        }
        result.add(p);
        return result;
    }


    static List<Point> random_shuffle(List<Point> pl,List<Point> pr){

        Long start= System.currentTimeMillis();
        List<Integer> chosen=new ArrayList<>();
        int total_num=pl.size()+pr.size();
        for(int i=0;i<total_num;i++)
            chosen.add(i);
        List<Point> result=new ArrayList<>();
        while(chosen.size()>0){
            int num=(int)(Math.random()*chosen.size());
            if(chosen.get(num)>=pl.size())
                result.add(pr.get(chosen.get(num)-pl.size()));
            else
                result.add(pl.get(chosen.get(num)));
            chosen.remove(num);
        }
        Long end= System.currentTimeMillis();
        random_suffle_time+=end-start;
        return result;
    }

    static List<Point> random_shuffle2(List<Point> pl,List<Point> pr){

//        Long start= System.currentTimeMillis();
        List<Point> result=new ArrayList<>();
        result.addAll(pl);
        result.addAll(pr);
        int lsize=pl.size();
        int rsize=pr.size();
        int total_num=lsize+rsize;
        for(int i=0;i<total_num;i++){
            int num=(int)(Math.random()*(total_num-i)+i);
            Point tmp=result.get(num);
            result.set(num,result.get(i));
            result.set(i,tmp);
        }

//        Long end= System.currentTimeMillis();
//        random_suffle_time+=end-start;
        return result;
    }

    static List<Point> nrandom_shuffle(List<Point> pl,List<Point> pr){
      //  Long start= System.currentTimeMillis();
        List<Point> result=new ArrayList<>();
        int ii=0;
        long plsize=pl.size();
        long prsize= pr.size();
        long max= plsize>prsize? plsize:prsize;
        while(ii<max){
            if(plsize>ii)
                result.add(pl.get(ii));
            if(prsize>ii)
                result.add(pr.get(ii));
            ii++;
        }
//        Long end= System.currentTimeMillis();
//        random_suffle_time+=end-start;
        return result;
    }

    static public List<Point> getBridge(List<Point> pl,List<Point> pr, Point pivit) {
//        Long start= System.currentTimeMillis();
        long hit_1D_LP=0;
        double a=0,b=-(Cons);
        List<Point> po=random_shuffle2(pl,pr);



        List<Point> constriants =new ArrayList<>(pl.size()+pr.size());
        Point p1=new Point();
        Point p2=new Point();
        for(Point p:po){
            constriants.add(p);
            if(a*p.x+b<=p.y){
                hit_1D_LP++;
                List<Point> tmp=OneDLP(constriants,pivit,p);
                a=tmp.get(0).x;
                b=tmp.get(0).y;
                p1=p;
                p2=tmp.get(1);
            }
        }
        List<Point> result=new ArrayList<>();
        if(p1.x<p2.x) {
            result.add(p1);
            result.add(p2);
        }
        else {
            result.add(p2);
            result.add(p1);
        }
        //System.out.println(pl.size()+" "+pr.size()+" "+po.size()+" "+hit_1D_LP);
//        Long end= System.currentTimeMillis();
//        get_bridge_time+=end-start;
        return result;
    }

    static public List<Point> PruneLeft(List<Point> bridge, List<Point> pl) {
        List<Point> pl_tmp=new ArrayList<>(pl.size());
        double left=bridge.get(0).x;
        for(Point p:pl){
            if(p.x<left){
                pl_tmp.add(p);
            }
        }
        pl_tmp.add(bridge.get(0));
        return pl_tmp;
    }

    static public List<Point> PruneRight(List<Point> bridge, List<Point> pr) {
        List<Point> pr_tmp=new ArrayList<>(pr.size());
        double right=bridge.get(1).x;
        for(Point p:pr){
            if(p.x>right){
                pr_tmp.add(p);
            }
        }
        pr_tmp.add(bridge.get(1));
        return pr_tmp;
    }

//    static public List<Point> PruneLeft(List<Point> bridge, List<Point> pl) {
//        double left=bridge.get(0).x;
//        for(int i=0;i<pl.size();i++){
//            if(pl.get(i).x>=left){
//                pl.remove(i);
//                i--;
//            }
//        }
//        pl.add(bridge.get(0));
//        return pl;
//    }
//
//    static public List<Point> PruneRight(List<Point> bridge, List<Point> pr) {
//        double right=bridge.get(1).x;
//        for(int i=0;i<pr.size();i++){
//            if(pr.get(i).x<=right){
//                pr.remove(i);
//                i--;
//            }
//        }
//        pr.add(bridge.get(1));
//        return pr;
//    }




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

        Long start= System.currentTimeMillis();


        run(points, new ArrayList<>());
        Long end= System.currentTimeMillis();
        System.out.println("MBC time "+(end-start)+" ms");
        System.out.println("Total random shuffle time "+ random_suffle_time+" ms");
      //  System.out.println("Total get bridge time "+ get_bridge_time+" ms");
        List<Point> sorted = new ArrayList<Point>(getSortedPointSet(upcv));

        return sorted;

    }
}
