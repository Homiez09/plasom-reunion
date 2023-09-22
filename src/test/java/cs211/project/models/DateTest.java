package cs211.project.models;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DatePicker to java.util.Date Example");

        DatePicker datePicker = new DatePicker();

        VBox vbox = new VBox(datePicker);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        datePicker.setOnAction(event -> {
            LocalDate localDate = datePicker.getValue();
            Date utilDateTest = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println("Selected java.util.Date: " + utilDateTest);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
