package sample;

import javafx.scene.chart.XYChart;

import static sample.PriorityAlgorithm.*;

public class SJFAlgorithm {
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-purple","status-move","status-white"};
    public static XYChart.Series NonPreSJF(Process p[])
    {
        for(int i=0; i<p.length; i++)
        {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);
      /*  ArrayList<Process> processes = new ArrayList<Process>();
        for (int i=0;i<p.length;i++) {
            processes.add(p[i]);
        }*/
        return NonPrePriority( p);
    }

    public static XYChart.Series  PreSJFFC(Process p[])
    {
        for(int i=0; i<p.length; i++) {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);
      /*  ArrayList<Process> processes = new ArrayList<Process>();
        for (int i=0;i<p.length;i++) {
            processes.add(p[i]);
        }*/
       return PrePriorityFC( p);
    }
    public static XYChart.Series  PreSJFRR(Process p[])
    {
        for(int i=0; i<p.length; i++) {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);
      /*  ArrayList<Process> processes = new ArrayList<Process>();
        for (int i=0;i<p.length;i++) {
            processes.add(p[i]);
        }*/
        return PrePriorityRR( p);
    }

}

