package mzc.app.view_model;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import mzc.app.utils.FileManager;
import mzc.app.view_model.base.BaseViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class HomeViewModel extends BaseViewModel {
    private final Label clock = new Label();
    private final Label date = new Label();
    private final List<Image> listImagePath = new ArrayList<>();
    private int imageIdx = 0;
    private final ImageView logo = new ImageView(FileManager.getResourcePath("/mzc/app/assets/mzc-0.png"));
    private final Label ourTeam = new Label("Our Team");
    private int wallpaperIdx = 0;
    private final List<Image> listWallpaperPath = new ArrayList<>();
    private final ImageView wallpaper = new ImageView(FileManager.getResourcePath("/mzc/app/assets/Wallpaper1.png"));

    private VBox mainMenu;


    private StackPane mainCol;


    @Override
    public void init() {
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-0.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-1.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-2.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-3.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-4.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-5.png")));
        this.listImagePath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/mzc-6.png")));
        this.listWallpaperPath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/Wallpaper1.png")));
        this.listWallpaperPath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/Wallpaper2.png")));
        this.listWallpaperPath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/Wallpaper3.png")));
        this.listWallpaperPath.add(new Image(FileManager.getResourcePath("/mzc/app/assets/Wallpaper4.png")));
        updateTime();
        updateDate();
        updateWallpaper();
        // Container for datetime
        VBox datetime = new VBox(this.clock, this.date);
        datetime.setAlignment(Pos.CENTER);

        this.ourTeam.setStyle("-fx-font-family: Poppins; -fx-font-size: 3em; -fx-font-weight: bold;");
        this.logo.setFitWidth(150);
        this.logo.setPreserveRatio(true);
        ImageView team = new ImageView(FileManager.getResourcePath("/mzc/app/assets/our-team.png"));
        VBox teamMember = new VBox(this.ourTeam, team);
        teamMember.setAlignment(Pos.CENTER);
        this.mainMenu = new VBox(datetime, teamMember);
        this.mainMenu.setAlignment(Pos.CENTER);
        this.mainMenu.setSpacing(100);
        this.mainMenu.setPadding(new Insets(100, 0, 0, 0));
        this.mainCol = new StackPane(this.wallpaper, this.mainMenu, this.logo);
        this.mainCol.setAlignment(Pos.TOP_LEFT);

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
                    updateLogo();
                });
            }
        });
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        Thread updateWallpaperThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    updateWallpaper();
                });
            }
        });
        updateWallpaperThread.setDaemon(true);
        updateWallpaperThread.start();
    }

    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        String formattedTime = String.format("%02d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        this.clock.setText(formattedTime);
        this.clock.setStyle("-fx-font-family: Poppins; -fx-font-size: 6em;");
    }

    private void updateDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String formattedDate = currentDate.format(formatter);
        this.date.setText(formattedDate);
        this.date.setStyle("-fx-font-family: Poppins; -fx-font-size: 2em;");
    }

    private void updateLogo() {
        this.imageIdx = (this.imageIdx+1) % this.listImagePath.size();
        this.logo.setFitHeight(150);
        this.logo.setPreserveRatio(true);
        this.logo.setImage(this.listImagePath.get(this.imageIdx));
    }

    private void updateWallpaper() {
        this.wallpaperIdx = (this.wallpaperIdx+1) % this.listWallpaperPath.size();
        this.wallpaper.setFitWidth(1295);
        this.wallpaper.setFitHeight(695);
        this.wallpaper.setImage(this.listWallpaperPath.get(this.wallpaperIdx));
    }
}
