package mzc.app.view_model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.base.PageViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
public class HomeViewModel extends BaseViewModel {
    private Label clock = new Label();
    private Label date = new Label();
//    private final String[] listImagePath = new String[];
    private final ImageView logo = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/mzc-0.png")).toExternalForm());
    private Label ourTeam = new Label("Our Team");
    private HBox teamMember;

    private VBox mainMenu;


    private VBox mainCol;

    public HomeViewModel() {
    }

    @Override
    public void init() {
//        this.listImagePath.add("/mzc/app/assets/mzc-0.png");
        updateTime();
        updateDate();
        // Container for datetime
        VBox datetime = new VBox(this.clock, this.date);
        datetime.setAlignment(Pos.CENTER);

        this.ourTeam.setStyle("-fx-font-family: Helvetica; -fx-font-size: 3em;");
        this.ourTeam.setFont(new Font("Gotham", 12));
        this.logo.setFitWidth(200);
        this.logo.setPreserveRatio(true);
        ImageView team = new ImageView(Objects.requireNonNull(getClass().getResource("/mzc/app/assets/our-team.png")).toExternalForm());
        VBox teamMember = new VBox(this.ourTeam, team);
        teamMember.setAlignment(Pos.CENTER);
        this.mainMenu = new VBox(datetime, teamMember);
        this.mainMenu.setAlignment(Pos.CENTER);
        this.mainMenu.setSpacing(100);
        this.mainCol = new VBox(this.logo, this.mainMenu);

        Thread updateTimeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    updateTime(); // Update the time label every second
                    updateDate();
                });
            }
        });
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        String formattedTime = String.format("%02d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        this.clock.setText(formattedTime);
        this.clock.setStyle("-fx-font-family: Helvetica; -fx-font-size: 6em;");
    }

    private void updateDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String formattedDate = currentDate.format(formatter);
        this.date.setText(formattedDate);
        this.date.setStyle("-fx-font-family: Helvetica; -fx-font-size: 2em;");
    }
}
