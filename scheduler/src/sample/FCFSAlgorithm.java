package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

import static sample.PriorityAlgorithm.NonPrePriority;

public class FCFSAlgorithm {
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-purple","status-move","status-white"};
    public static  XYChart.Series FCFS(Process p[])
    {
        for(int i=0; i<p.length; i++) {
            p[i].setPriority(p[i].getArrival_time());
        }
        for(int i=0;i<p.length;i++)
        {
            p[i].setColor(colors[i]);
        }
        Process.ProcessSort(p);
        ArrayList<Process> processes = new ArrayList<Process>();
        for (int i=0;i<p.length;i++) {
            processes.add(p[i]);
        }
      return  NonPrePriority(p);
    }
}

