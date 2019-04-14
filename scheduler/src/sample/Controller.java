package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
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
    public TextField quantumInput;
    public Label quantumError;
    public TextField average_waiting_time;
    public TextField Average_Turnaround_Time;
    GanttChart<Number,String> chart;

    public void addButtonClicked() {
        Process process = new Process();
        String name = processInput.getText();
        String burstTime = burstInput.getText();
        String arrivalTime = arrivalInput.getText();
        String priority = priorityInput.getText();
        if(!validateName(name) |!validateBurst(burstTime) | !validateArrival(arrivalTime) |!validatePriority(priority))
            return;

        for (Process p : table.getItems())
        {
           if(("P"+name).equals(p.getName())) {
               nameLabel.setText("*Enter unique No.");
               return;
           }
        }
       // nameLabel.setText("");

        process.setName("P"+name);
        process.setBurst_time(Integer.parseInt(burstTime));
        process.setArrival_time(Integer.parseInt(arrivalTime));
        process.setPriority(Integer.parseInt(priority));
        table.getItems().add(process);
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
            if(number<0)
            {
                nameLabel.setText("*Enter +ve No.");
                return false;
            }
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
            if(number<0)
            {
                burstLabel.setText("*Enter +ve No.");
                return false;
            }
            burstLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            burstLabel.setText("*Enter integer Time");
            return false;
        }
    }
    boolean validateArrival(String arrival)
    {
        try {
            int number = Integer.parseInt(arrival);
            if(number<0)
            {
                arrivalLabel.setText("*Enter +ve No.");
                return false;
            }
            arrivalLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            arrivalLabel.setText("*Enter integer Time");
            return false;
        }
    }
    boolean validatePriority(String priority)
    {
        try {
            int number = Integer.parseInt(priority);
            if(number<0)
            {
                priorityLabel.setText("*Enter +ve No.");
                return false;
            }
           priorityLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            priorityLabel.setText("*Enter integer No.");
            return false;
        }
    }
    boolean validateQuantum(String quantum)
    {
        try {
            int number = Integer.parseInt(quantum);
            if (number<=0)
            {

                quantumError.setText("*Enter +ve No.");
                return false;
            }
            quantumError.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            quantumError.setText("*Enter Correct Quantum");
            return false;
        }
    }

    public void deleteButtonClicked() {
        ObservableList<Process> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

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
        comboBox.getItems().add("SJF(PreemptiveFC)");
        comboBox.getItems().add("SJF(PreemptiveRR)");
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
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chartVBox.getChildren().add(chart);

        comboBox.setOnAction(event -> {
            comboErrorLabel.setText("");
            if (comboBox.getValue().equals("Round Robbin"))
            {
                quantumInput.setVisible(true);
                quantumError.setVisible(true);

            }
            else
            {
                quantumInput.setVisible(false);
                quantumError.setVisible(false);
            }
        });

        processInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(processInput.getText()==null || processInput.getText().isEmpty()) {
                nameLabel.setText("");
                return;
            }
            validateName(processInput.getText());

        }));
        burstInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(burstInput.getText()==null || burstInput.getText().isEmpty()) {
                burstLabel.setText("");
                return;
            }
            validateBurst(burstInput.getText());
        }));
        arrivalInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(arrivalInput.getText()==null || arrivalInput.getText().isEmpty()) {
                arrivalLabel.setText("");
                return;
            }
            validateArrival(arrivalInput.getText());
        }));
        priorityInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(priorityInput.getText()==null || priorityInput.getText().isEmpty()) {
                priorityLabel.setText("");
                return;
            }
            validatePriority(priorityInput.getText());
        }));

        quantumInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(quantumInput.getText()==null || quantumInput.getText().isEmpty()){
                quantumError.setText("");
                return;
            }
            validateQuantum(quantumInput.getText());
        }));
    }


    public void resetButtonClicked() {
        table.getItems().clear();
        chart.getData().clear();
    }

    public void startButtonClicked() {
        chart.getData().clear();
        double Average_waiting_time=0;
        ObservableList<Process> processes =  table.getItems();
        Process[] pro = new Process[processes.size()];
        for (int i=0;i<pro.length;i++)
        {
            pro[i] = processes.get(i);

        }
      if(  comboBox.getSelectionModel().getSelectedItem() == null)
          comboErrorLabel.setText("*Please Choose Algorithm First ");
      else if(table.getItems().size()==0)
          comboErrorLabel.setText("*Please Enter at least one Process ");
      else {
          comboErrorLabel.setText("");
          switch ((String) comboBox.getValue()) {
              case "Priority(Non-Preemptive)": // Priority(Non-Preemptive)
              {

                  XYChart.Series series = PriorityAlgorithm.NonPrePriority(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                  chart.getData().addAll(series);
                  break;

              }
              case "Priority(Preemptive-FCFS)": // Priority(Non-Preemptive)
              {

                  XYChart.Series series = PriorityAlgorithm.PrePriorityFC(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                  chart.getData().addAll(series);
                  break;

              }
              case "Priority(Preemptive-Round Robin)": // Priority(Non-Preemptive)
              {
                  XYChart.Series series = PriorityAlgorithm.PrePriorityRR(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                  chart.getData().addAll(series);
                  break;
              }
              case "FCFS": // Priority(Non-Preemptive)
              {
                  XYChart.Series series = FCFSAlgorithm.FCFS(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                  chart.getData().addAll(series);
                  break;
              }
              case "SJF(Non-Preemptive)": // Priority(Non-Preemptive)
              {
                  XYChart.Series series = SJFAlgorithm.NonPreSJF(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                  chart.getData().addAll(series);
                  break;
              }
              case "SJF(PreemptiveFC)": // Priority(Non-Preemptive)
              {
                  XYChart.Series series = SJFAlgorithm.PreSJFFC(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));

                  chart.getData().addAll(series);
                  break;
              }
              case "SJF(PreemptiveRR)": // Priority(Non-Preemptive)
              {
                  XYChart.Series series = SJFAlgorithm.PreSJFRR(pro);
                  average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                  Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));

                  chart.getData().addAll(series);
                  break;
              }
              case "Round Robbin": // Priority(Non-Preemptive)
              {
                  if(validateQuantum(quantumInput.getText())) {
                      XYChart.Series series = RoundRobinAlgorithm.RoundRobin(pro, Integer.parseInt(quantumInput.getText()));
                      average_waiting_time.setText(Double.toString(RoundRobinAlgorithm.getAverage_waiting_time()));
                      Average_Turnaround_Time.setText(Double.toString(RoundRobinAlgorithm.getAverage_turnAround_time()));

                      chart.getData().addAll(series);
                  }
                  break;

              }
          }
      }

    }


}
