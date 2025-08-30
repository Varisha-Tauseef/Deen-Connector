package com.example.summer_project.gui;

import com.example.summer_project.HijriCalendar;
import com.example.summer_project.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Objects;

public class CalendarController {
    private YearMonth currentYearMonth;

    private Map<LocalDate, String> events;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthLabel;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();

        // Fetch Islamic events from HijriCalendar
        events = HijriCalendar.getIslamicEvents(
                currentYearMonth.getMonthValue(),
                currentYearMonth.getYear()
        );

        // Set the month title
        monthLabel.setText(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());

        drawCalendar();
    }

    private void drawCalendar() {
        calendarGrid.getChildren().clear();

        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        // Add day headers
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setFont(Font.font(14));
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setPrefSize(160, 90);
            dayLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15; -fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7; // Sunday = 0
        int daysInMonth = currentYearMonth.lengthOfMonth();

        int row = 1;
        int col = dayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            VBox cell = new VBox(5);
            cell.setPrefSize(160, 90);
            cell.setPadding(new Insets(5));
            cell.setStyle("-fx-border-radius: 4; -fx-background-color: #223d3c;");

            Label dayNumber = new Label(String.valueOf(day));
            //dayNumber.setFont(Font.font(14));
            dayNumber.setStyle("-fx-text-fill: #f8f3f2; -fx-font-size: 14;");

            LocalDate currentDate = currentYearMonth.atDay(day);

            // Highlight today
            if (currentDate.equals(LocalDate.now())) {
                cell.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            cell.getChildren().add(dayNumber);

            // Add Islamic event if exists
            if (events.containsKey(currentDate)) {
                Label eventLabel = new Label(events.get(currentDate));
                eventLabel.setFont(Font.font(8));
                eventLabel.setWrapText(true);
                eventLabel.setTextFill(Color.WHITE);
                cell.getChildren().add(eventLabel);
            }

            calendarGrid.add(cell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    void handleHome(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    SessionManager.class.getResource("dashboard.fxml")
            ));

            Scene scene = new Scene(root);

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
