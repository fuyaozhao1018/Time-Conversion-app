

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeConversionApp extends Application {

    private Label localTimeLabel;
    private Label convertedTimeLabel;

    private List<String> timeZones;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Time Conversion App");

        // Get the list of available time zones
        timeZones = new ArrayList<>(ZoneId.getAvailableZoneIds());

        BorderPane root = new BorderPane();
        root.setTop(createTimeZoneSelector());
        root.setCenter(createClock());
        root.setBottom(createTimeDisplay());

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Update the clock every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private VBox createTimeZoneSelector() {
        ComboBox<String> timeZoneComboBox = new ComboBox<>();
        timeZoneComboBox.getItems().addAll(timeZones);
        timeZoneComboBox.setOnAction(event -> updateClock());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(timeZoneComboBox);
        return vBox;
    }

    private HBox createClock() {
        localTimeLabel = new Label();
        convertedTimeLabel = new Label();

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(localTimeLabel, convertedTimeLabel);
        return hBox;
    }

    private Label createTimeDisplay() {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 16pt;");
        return label;
    }

    private void updateClock() {
        String selectedTimeZone = getTimeZoneSelection();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        ZoneId selectedZoneId = ZoneId.of(selectedTimeZone);
        ZonedDateTime selectedZonedDateTime = ZonedDateTime.of(localDateTime, selectedZoneId);
        String selectedTime = selectedZonedDateTime.format(formatter);

        String localTime = localDateTime.format(formatter);

        localTimeLabel.setText("Local Time: " + localTime);
        convertedTimeLabel.setText("Converted Time: " + selectedTime);
    }

    private String getTimeZoneSelection() {
        // Get the selected time zone from the combo box
        // You can add error handling if needed
        return timeZones.get(0); // Change this to get the actual selection
    }
}
