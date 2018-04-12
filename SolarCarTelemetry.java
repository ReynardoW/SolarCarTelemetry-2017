package solarcartelemetry;

import javafx.application.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.NumberAxis;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.animation.AnimationTimer;
import java.util.Random;

public class SolarCarTelemetry extends Application{
    Stage window;
    //Line Chart
        //Defining Axis
        final NumberAxis xAxis = new NumberAxis(0,50,50/10);
        final NumberAxis yAxis = new NumberAxis();
        private int series1Count=0;
        final LineChart<Number,Number> line1= new LineChart<Number,Number>(xAxis,yAxis); 
        //Series
        XYChart.Series<Number,Number> series1;
        
    
    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;
        window.setTitle("SolarCar Telemetry"); //Title of Frame
        //Gonna add panes to this pane
        BorderPane MainPane=new BorderPane();
        MainPane.setPadding(new Insets(5,5,5,5));
        
        //Setting LineChart Paramters
        xAxis.setLabel("Time");
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        yAxis.setLabel("Stuff");
        yAxis.setAutoRanging(true);
        line1.setTitle("Testing");
        series1=new XYChart.Series<>();
        line1.setAnimated(false);
        line1.getData().addAll(series1);
        
        prepareTimeline();
        //Left Panel
        GridPane leftPane=new GridPane();
        
        //Data Pane
        TilePane DataPane=new TilePane();
        DataPane.setStyle("-fx-background-color: linear-gradient(to bottom, #924444, #E87373);"
                + "-fx-background-radius: 0px 0px 6px 6px;" 
                ); //use this to determine the size of your pane and other things
        DataPane.setPrefHeight(60);
        DataPane.setHgap(130);
        
                
        //Text objects in DataPane
        Text speed = new Text("Speed:");
        Text RPM= new Text("RPM:");
        Text PConsume= new Text("P.Consumption:");
        Text CVoltage= new Text("C.Voltage:");
        Text RunTime= new Text("RunTime:");
        Text Status= new Text("Status:");
        speed.setFont(Font.font("Arial",14));
        RPM.setFont(Font.font("Arial",14));
        PConsume.setFont(Font.font("Arial",14));
        CVoltage.setFont(Font.font("Arial",14));
        RunTime.setFont(Font.font("Arial",14));
        Status.setFont(Font.font("Arial",14));
        speed.setWrappingWidth(200);
        RPM.setWrappingWidth(200);
        PConsume.setWrappingWidth(200);
        CVoltage.setWrappingWidth(200);
        RunTime.setWrappingWidth(200);
        Status.setWrappingWidth(200);
       
        //Add data to pane
        DataPane.getChildren().addAll(speed,RPM,PConsume,CVoltage,RunTime,Status);
        //Tabs Pane
        TabPane tabPane=new TabPane();
        Tab tab=new Tab();
        tab.setText("Tab1");
        tab.setContent(new Rectangle(420,300,Color.BLUE));
        tab.setClosable(false);
        Tab tab1=new Tab();
        tab1.setText("Tab2");
        tab1.setContent(new Rectangle(420,300,Color.BLUE));
        tab1.setClosable(false);
        tabPane.getTabs().add(tab);
        tabPane.getTabs().add(tab1);
        
        //Map Pane
        StackPane MapPane=new StackPane();
        MapPane.setStyle("-fx-background-color: #CBCBCB;");
        Rectangle demo = new Rectangle(420, 350);
        Text demoText = new Text("MAP GOES HERE");
        demoText.setFill(Color.WHITE); 
        demo.setFill(Color.WHITE); 
        demoText.setTranslateY(demo.getY() - demo.getHeight()/2);
        MapPane.getChildren().addAll(demo, demoText);
        MapPane.setAlignment(Pos.BOTTOM_CENTER);
        //Graphs Pane
        StackPane GraphPane=new StackPane();
        GraphPane.getChildren().addAll(line1);
        GraphPane.setAlignment(Pos.TOP_LEFT);
        
        //Adding to leftPane
        leftPane.addRow(0,tabPane);
        leftPane.addRow(1,MapPane);

        //Add all the panes to the MainPane
        //Set the scene as the mainpane and show it on the stage(Frame)
        MainPane.setTop(DataPane);
        MainPane.setCenter(GraphPane);
        MainPane.setLeft(leftPane);
        Scene Main=new Scene(MainPane,1280,720);
        window.setScene(Main);
        window.show();
        
        //Tasks
        
        //New task for updating values in real-time Used for Datapanel, and updating data
        Task<Void> task = new Task<Void>(){
            Random rand = new Random();
            @Override
            public Void call() throws Exception{
                for(int i=0; i<10000; i++){
                    Platform.runLater(() -> {
                        speed.setText("Speed: "+rand.nextInt()); 
                        RPM.setText("RPM: "+rand.nextInt());
                        PConsume.setText("PConsume "+rand.nextInt());
                        CVoltage.setText("CVoltage: "+rand.nextInt());
                        RunTime.setText("RunTime: "+rand.nextInt());
                        Status.setText("Status: ON");
                        });
                    Thread.sleep(100);
                } 
                return null;
            }
        };
        //New Task for updating the graph accoridng to data
        Task<Void> map = new Task<Void>(){
            @Override
            public Void call()throws Exception{
                for(int i=0;i<10000;i++){
                    Platform.runLater(() -> {
                    
                    });
                    Thread.sleep(100);
                }
                return null;
            }
        };
        //Threads
        Thread thData = new Thread(task);
        thData.setDaemon(true);
        thData.start();
    }
    private void addtoSeries(){ //Adds Numbers to the series TESTING PURPOSES MUST CHANGE FOR LATER
        Random r=new Random();
        for(int i=0;i<20;i++){
            series1.getData().add(new AreaChart.Data(series1Count++,r.nextInt(100-1)+1));
        }
        if(series1.getData().size() > 100){
            series1.getData().remove(0,series1.getData().size()-100);
        }
        
        //Update xAxis
        xAxis.setLowerBound(series1Count-20);
        xAxis.setUpperBound(series1Count-1);
    }
    private void prepareTimeline(){
        new AnimationTimer(){
            @Override public void handle(long now){
                
                addtoSeries();
            }
        }.start();
    }
      
    public static void main(String[] args) {
        launch(args);
    }
    //bryan succs
}
