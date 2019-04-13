package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public ArrayList<Process> processes = new ArrayList<>();

    public TextField processInput;
    public TextField burstInput;
    public TextField priorityInput;
    public TextField arrivalInput;
    public TableView<Process> table;
    public TableColumn processColumn;
    public TableColumn burstColumn;
    public TableColumn priorityColumn;
    public TableColumn arrivalColumn;
    public ComboBox comboBox;
    public VBox chartVBox;

    GanttChart<Number,String> chart;

    public void addButtonClicked() {
        Process process = new Process();
        process.setName(processInput.getText());
        process.setBurst_time(Integer.parseInt(burstInput.getText()));
        process.setArrival_time(Integer.parseInt(arrivalInput.getText()));
        process.setPriority(Integer.parseInt(priorityInput.getText()));
        table.getItems().add(process);
        processes.add(process);
        processInput.clear();
        burstInput.clear();
        priorityInput.clear();
        arrivalInput.clear();

    }

    public void deleteButtonClicked() {
        ObservableList<Process> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        System.out.println(processes.size());

        productSelected.forEach(allProducts::remove);

    }

    //called as soon as the layout loads
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst_time"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
        processColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        comboBox.getItems().add("Priority(Non-Preemptive)");
        comboBox.getItems().add("Priority(Preemptive-FCFS)");
        comboBox.getItems().add("Priority(Preemptive-Round Robin)");
        comboBox.getItems().add("FCFS");
        comboBox.getItems().add("SJF(Non-Preemptive)");
        comboBox.getItems().add("SJF(Preemptive)");
        comboBox.getItems().add("Round Robbin");




        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

         chart = new GanttChart<Number,String>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);


        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(true);
        chart.setBlockHeight(50);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chartVBox.getChildren().add(chart);


    }


    public void resetButtonClicked() {
        table.getItems().clear();
    }

    public void startButtonClicked() {
        chart.getData().clear();
        ObservableList<Process> processes =  table.getItems();
        Process[] pro = new Process[processes.size()];
        for (int i=0;i<pro.length;i++)
        {
            pro[i] = processes.get(i);

        }
        switch ((String) comboBox.getValue()) {
            case "Priority(Non-Preemptive)": // Priority(Non-Preemptive)
            {

                XYChart.Series series =  PriorityAlgorithm.NonPrePriority(pro);
                chart.getData().addAll(series);

            }
        }

    }
}
