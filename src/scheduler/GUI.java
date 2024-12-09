package scheduler;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    static public List<Process> processes;

    @Override
    public void start(Stage primaryStage) {
        try {
            ArrayList<String> processNames = new ArrayList<>();
            int maxTime = 0;
            for (Process process : processes) {
                processNames.add(process.process_name);
                for (int[] interval : process.getExecutionLog()) {
                    maxTime = Math.max(maxTime, interval[1]);
                }
            }

            NumberAxis xAxis = new NumberAxis("Time", 0, maxTime + 1, 1);
            CategoryAxis yAxis = new CategoryAxis();
            yAxis.setLabel("Processes");
            yAxis.setCategories(FXCollections.observableArrayList(processNames));

            LineChart<Number, String> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Process Timeline");
            lineChart.setLegendVisible(false);

            StringBuilder cssRules = new StringBuilder();
            int seriesIndex = 0;

            for (Process process : processes) {
                String hexColor = process.getColor(); // Get the assigned color
                for (int[] interval : process.getExecutionLog()) {
                    int start = interval[0];
                    int end = interval[1];
                    if (end - start > 0) { // Ensure there's execution time
                        XYChart.Series<Number, String> series = new XYChart.Series<>();
                        series.getData().add(new XYChart.Data<>(start, process.process_name));
                        series.getData().add(new XYChart.Data<>(end, process.process_name));

                        // Append CSS for the series to set color and stroke width
                        cssRules.append(".chart-series-line.series")
                                .append(seriesIndex)
                                .append(" { -fx-stroke: ")
                                .append(hexColor)
                                .append("; -fx-stroke-width: 5px; }");

                        lineChart.getData().add(series);
                        seriesIndex++;
                    }
                }
            }

            VBox vbox = new VBox(lineChart);
            VBox.setVgrow(lineChart, Priority.ALWAYS); // Allow the chart to grow dynamically
            Scene scene = new Scene(vbox, 1000, 400);
            scene.getStylesheets().add("data:, " + cssRules.toString().replace("\n", "%0A"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Process Timeline Chart");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 