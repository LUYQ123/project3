import javax.swing.*;
import java.text.DecimalFormat;
import java.util.*;

public class test {
    public static void show(List<Point> list){
         DecimalFormat df = new DecimalFormat("0.00");
        Collections.sort(list,(p1,p2)->{
            if(p1.x>p2.x)
                return 1;
            else if(p1.x==p2.x && p1.y>p2.y)
                return 1;
            else
                return -1;
        });
        for(Point p:list){
            System.out.print("("+df.format(p.x) +" "+df.format(p.y)+") ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        datasets.Square_sample square_sample =new datasets.Square_sample(-10000,-10000,20000,20000,100000);

        datasets.Circle_sample circle_sample=new datasets.Circle_sample(10000,-5000,-5000,1000);

        datasets.Third_sample third_sample=new datasets.Third_sample(10000,10000);


        datasets.Forth_sample forth_sample=new datasets.Forth_sample(10000,10000);

        List<Point> datas=circle_sample.sample;

//        double[] xs=new double[]{-2,-1,-1.1,-1,0,0,1,2};
//        double[] ys=new double[]{0,1,0,-1,1,-1.5,-1,0};
//
//        datas=new ArrayList<>();
//        for(int i=0;i<xs.length;i++)
//            datas.add(new Point(xs[i],ys[i]));
//
//        show(datas);


        List<Point> Graham_scan_answer=INC_CH.getConvexHull(datas);

        List<Point> Gift_answer=GIFT_CH.getConvexHull(datas);

        List<Point> MbC_answer=MbC_CH.getConvexHull(datas);
//        show(Graham_scan_answer);
//        show(Gift_answer);
//        show(MbC_answer);



//        List<Integer> l=new ArrayList<>();
//        l.add(2);
//        l.add(1);
//        l.add(1);
//        l.add(3);
//        l.add(1);
//        for(int i=0;i<l.size();i++){
//            if(l.get(i).equals(1)) {
//                l.remove(i);
//                i--;
//            }
//            for(int in:l){
//                System.out.print(in+" ");
//            }
//            System.out.println();
////            System.out.println(i+" "+l.size());
//        }
    }
}
