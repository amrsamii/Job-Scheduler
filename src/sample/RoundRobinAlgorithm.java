package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.LinkedList;

public class RoundRobinAlgorithm {
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-purple","status-move","status-white"};
    public static  XYChart.Series RoundRobin(Process copy_process[], int n) {
        int res = 0;
        Process[]p=new Process[copy_process.length];
        for (int i = 0; i < p.length; i++) {
            p[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();


        int starttime=0;
        int finishtime=0;
        LinkedList<Process> queue=new LinkedList<Process>();
        for (int i = 0; i < p.length; i++) {
            queue.add(p[i]);
        }
        ArrayList<Process> res_b= new ArrayList<Process>();
        for (int i = 0; i < p.length; i++) {
            res_b.add(p[i]);

        }
        Process mask_array[]=new Process[p.length];
        for (int i = 0; i < p.length; i++) {
            mask_array[i]=new Process(p[i]);
        }

        int t = 0;
        String seq = "0";
        while(!queue.isEmpty()) {
            int flag=0;
            Process mask2 = queue.peek();
            int index=queue.indexOf(mask2);
            int first2 = mask2.getArrival_time();
            if(t<first2) { //find another
                for (int i = 0; i < queue.size(); i++) {
                    if (queue.get(i).getArrival_time() < t) {
                        Process temp=new Process();
                        temp=mask2;
                        queue.set(index,queue.get(i));
                        mask2=queue.remove(i);
                        queue.add(queue.indexOf(mask2)+1,temp);
                        flag=1;
                        break;
                    }
                }
            }
            if(t<first2&&flag==0) {
                for (; t < first2; ) {
                    starttime=t;
                    t = t + 1;
                    finishtime=t;
                    seq = seq + " " + t + " ";

                }
            }

            Process mask = queue.remove();
            int first = mask.getArrival_time();
            if (mask.getBurst_time() > n&&t>=first) {
                starttime=t;
                t = t + n;
                finishtime=t;
                mask.setBurst_time(mask.getBurst_time() - n);
                seq = seq + " " + mask.getName() + " " + t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p[(res_b.indexOf(mask))].getColor())));

            } else if (n == mask.getBurst_time() &&t>=first) {
                starttime=t;
                t = t + n;
                finishtime=t;
                mask.setBurst_time(mask.getBurst_time() - n);
                seq = seq + " " + mask.getName() + " " + t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p[(res_b.indexOf(mask))].getColor())));

                mask.setWt(t -mask_array[res_b.indexOf(mask)].getBurst_time() -mask.getArrival_time());
            } else if (n > mask.getBurst_time() &&t>=first){
                starttime=t;
                t = t + mask.getBurst_time();
                finishtime=t;
                mask.setBurst_time(0);
                seq = seq + " " + mask.getName() + " " + t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p[(res_b.indexOf(mask))].getColor())));

                int k=res_b.indexOf(mask);
                mask.setWt(t -mask_array[res_b.indexOf(mask)].getBurst_time() -mask.getArrival_time());
            }
            if (mask.getBurst_time() != 0) {
                queue.add(mask);
            }
        }

        System.out.println("name  ctime ");
        for (int i = 0; i < p.length; i++) {
            System.out.println(" " + p[i].getName() +
                    "    " + p[i].getWt());

            res = res + p[i].getWt();
        }
        System.out.println("Average waiting time is "
                + (float)res / p.length);
        System.out.println("Sequence is like that " + seq);
        return series1;
    }

}
