package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.LinkedList;

public class PriorityAlgorithm {

    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-purple","status-move","status-white"};
    public static XYChart.Series NonPrePriority (Process[] copy_process)
    {
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();
        for(int i=0;i<p1.length;i++)
        {
            p1[i].setColor(colors[i]);
        }


        ArrayList<Process>p =new ArrayList<>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }

        String seq = new String();
        float res=0;
        int finishtime=0;
        int starttime=0;

        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }

        int t = 0;
        int max=0;
        for(int j=0;j<p.size();j++) {
            for (int i = 1; i < p.size(); i++) {
                if (t >= p.get(i).getArrival_time()) {
                    if (p.get(i).getPriority() < p.get(max).getPriority()) {
                        max = i;
                    }
                }
            }
            if (p.get(max).getArrival_time() >= 0) {
                if((t==0&&p.get(max).getArrival_time()!=0)||p.get(max).getArrival_time() >t ) {
                    int k=t;

                    for (int i = 0; i < p.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                      //  series1.getData().add(new XYChart.Data(starttime,"processes",new GanttChart.ExtraData( finishtime-starttime,colors[j])));
                        for (int i1 = 1; i1 < p.size(); i1++) {
                            if (t >= p.get(i1).getArrival_time()) {
                                if (p.get(i1).getPriority() < p.get(max).getPriority()) {
                                    max = i1;
                                }
                            }
                        }
                    }
                }
                p.get(max).setWt(t-p.get(max).getArrival_time());
                seq += t+" " +p.get(max).getName()+" ";
                starttime=t;

                t = t + p.get(max).getBurst_time();
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(p.get(max)))].getColor())));


            }
            p.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
        for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();
        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        System.out.println("Sequence is like that " + seq);


        return series1;
    }

    public static XYChart.Series PrePriorityFC (Process[] copy_process)
    {
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();
        for(int i=0;i<p1.length;i++)
        {
            p1[i].setColor(colors[i]);
        }
        int starttime=0;
        int finishtime=0;
        ArrayList<Process> p=new ArrayList<>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }
        String seq = new String();
        float res=0;
        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }
        LinkedList<Process> queue=new LinkedList<Process>();
        for (int i = 0; i < p.size(); i++) {
            queue.add(p.get(i));
        }
        int t = 0;
        int max=0;
        for(int j=0;j<queue.size();j++) {
            for (int i = 1; i < queue.size(); i++) {
                if (t >= queue.get(i).getArrival_time()) {
                    if (queue.get(i).getPriority() <= queue.get(max).getPriority()) {
                        if (queue.get(i).getName().compareTo(queue.get(max).getName()) < 0||(queue.get(i).getArrival_time() < queue.get(max).getArrival_time()&&queue.get(i).getPriority() < queue.get(max).getPriority())||queue.get(i).getPriority() < queue.get(max).getPriority()) {
                            max = i;
                        }
                    }
                }
            }
            if (queue.get(max).getArrival_time() >= 0) {
                if((t==0&&queue.get(max).getArrival_time()!=0)||queue.get(max).getArrival_time() >t ) {
                    int k=t;
                    for (int i = 0; i < queue.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                        for (int i1 = 1; i1 < queue.size(); i1++) {
                            if (t >= queue.get(i1).getArrival_time()) {
                                if (queue.get(i1).getPriority() <= queue.get(max).getPriority()) {
                                    if (queue.get(i1).getName().compareTo(queue.get(max).getName()) < 0||(queue.get(i1).getArrival_time() < queue.get(max).getArrival_time()&&queue.get(i1).getPriority() < queue.get(max).getPriority())||queue.get(i1).getPriority() < queue.get(max).getPriority()) {
                                        max = i1;
                                    }
                                }
                            }
                        }
                    }
                }
                starttime=t;
                queue.get(max).setWt(t-queue.get(max).getArrival_time());
                queue.get(max).setBurst_time(queue.get(max).getBurst_time()-1);
                seq += t+" " +queue.get(max).getName()+" ";
                t = t + 1;
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(queue.get(max)))].getColor())));

            }
            if (queue.get(max).getBurst_time() != 0) {
                queue.add( queue.get(max));
            }
            queue.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
                for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();
        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        System.out.println("Sequence is like that " + seq);
        return series1;
    }

    public static XYChart.Series PrePriorityRR (Process[] copy_process)

    {
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();
        for(int i=0;i<p1.length;i++)
        {
            p1[i].setColor(colors[i]);
        }
        int starttime=0;
        int finishtime=0;
        ArrayList <Process> p=new ArrayList<Process>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }
        String seq = new String();
        float res=0;
        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }
        LinkedList<Process> queue=new LinkedList<Process>();
        for (int i = 0; i < p.size(); i++) {
            queue.add(p.get(i));
        }
        int t = 0;
        int max=0;
        for(int j=0;j<queue.size();j++) {
            for (int i = 1; i < queue.size(); i++) {
                if (t >= queue.get(i).getArrival_time()) {
                    if (queue.get(i).getPriority() < queue.get(max).getPriority()) {
                        max = i;
                    }
                }
            }
            if (queue.get(max).getArrival_time() >= 0) {
                if((t==0&&queue.get(max).getArrival_time()!=0)||queue.get(max).getArrival_time() >t ) {
                    int k=t;
                    for (int i = 0; i < queue.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                        for (int i1 = 1; i1 < queue.size(); i1++) {
                            if (t >= queue.get(i1).getArrival_time()) {
                                if (queue.get(i1).getPriority() < queue.get(max).getPriority()) {
                                    max = i1;
                                }
                            }
                        }
                    }
                }
                starttime=t;
                queue.get(max).setWt(t-queue.get(max).getArrival_time());
                queue.get(max).setBurst_time(queue.get(max).getBurst_time()-1);
                seq += t+" " +queue.get(max).getName()+" ";
                t = t + 1;
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(queue.get(max)))].getColor())));

            }
            if (queue.get(max).getBurst_time() != 0) {
                queue.add( queue.get(max));
            }
            queue.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
        for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();

        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        System.out.println("Sequence is like that " + seq);
        return series1;
    }


}