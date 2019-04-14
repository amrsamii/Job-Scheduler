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
    public Label comboErrorLabel;
    public Label nameLabel;
    public Label burstLabel;
    public Label priorityLabel;
    public Label arrivalLabel;

    GanttChart<Number,String> chart;

    public void addButtonClicked() {
        Process process = new Process();
        String name = processInput.getText();
        String burstTime = burstInput.getText();
        String arrivalTime = arrivalInput.getText();
        String priority = priorityInput.getText();
        if(!validateName(name) |!validateBurst(burstTime) | !validateArrival(arrivalTime) |!validatePriority(priority))
            return;

        process.setName("P"+name);
        process.setBurst_time(Integer.parseInt(burstTime));
        process.setArrival_time(Integer.parseInt(arrivalTime));
        process.setPriority(Integer.parseInt(priority));
        table.getItems().add(process);
        processes.add(process);
        processInput.clear();
        burstInput.clear();
        priorityInput.clear();
        arrivalInput.clear();

    }
    boolean validateName(String name)
    {
        try {
            int number = Integer.parseInt(name);
            nameLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            nameLabel.setText("*Enter No. of Process");
            return false;
        }
    }
    boolean validateBurst(String burst)
    {
        try {
            int number = Integer.parseInt(burst);
            burstLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            burstLabel.setText("*Enter Burst Time");
            return false;
        }
    }
    boolean validateArrival(String arrival)
    {
        try {
            int number = Integer.parseInt(arrival);
            arrivalLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            arrivalLabel.setText("*Enter Arrival Time");
            return false;
        }
    }
    boolean validatePriority(String priority)
    {
        try {
            int number = Integer.parseInt(priority);
           priorityLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            priorityLabel.setText("*Enter Priority");
            return false;
        }
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

         chart = new GanttChart<>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(new String[]{""})));

        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(true);
        chart.setBlockHeight(50);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chartVBox.getChildren().add(chart);

        comboBox.setOnAction(event -> {
            comboErrorLabel.setText("");
        });


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
      if(  comboBox.getSelectionModel().getSelectedItem() == null)
          comboErrorLabel.setText("*Please Choose Algorithm First ");
      else {
          comboErrorLabel.setText("");
          switch ((String) comboBox.getValue()) {
              case "Priority(Non-Preemptive)": // Priority(Non-Preemptive)
              {

                  XYChart.Series series = PriorityAlgorithm.NonPrePriority(pro);
                  chart.getData().addAll(series);
                  break;

              }
          }
      }

    }


}
