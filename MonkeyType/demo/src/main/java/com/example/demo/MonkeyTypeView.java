package com.example.demo;

import javafx.animation.TranslateTransition;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.Optional;

public class MonkeyTypeView {
    static final Color COLOR_DEFAULT = Color.rgb(200, 200, 200);
    public static TextFlow textFlow;

    static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem restartMenuItem = new MenuItem("Restart Test");
        restartMenuItem.setOnAction(event -> MonkeyTypeModel.restartTest());
        fileMenu.getItems().add(restartMenuItem);
        menuBar.getMenus().add(fileMenu);

        Menu languageMenu = new Menu("Language");
        MonkeyTypeModel.getAvailableLanguages().forEach(language -> {
            MenuItem languageMenuItem = new MenuItem(language);
            languageMenuItem.setOnAction(event -> {
                MonkeyTypeModel.loadDictionaryFile(language);
                startTestDialog();
            });
            languageMenu.getItems().add(languageMenuItem);
        });
        menuBar.getMenus().add(languageMenu);

        return menuBar;
    }

    static Label createStatisticsLabel() {
        int totalCharacters = MonkeyTypeModel.totalCorrectCharacters + MonkeyTypeModel.totalIncorrectCharacters + MonkeyTypeModel.totalExtraCharacters + MonkeyTypeModel.totalMissedCharacters;
        double accuracy = ((double) MonkeyTypeModel.totalCorrectCharacters / totalCharacters) * 100;
        int missedCharacter = MonkeyTypeModel.totalMissedCharacters - MonkeyTypeModel.wpm;

        String statisticsText = "Statistics:\n" +
                "Correct Characters: " + MonkeyTypeModel.totalCorrectCharacters + "\n" +
                "Incorrect Characters: " + MonkeyTypeModel.totalIncorrectCharacters + "\n" +
                "Extra Characters: " + MonkeyTypeModel.totalExtraCharacters + "\n" +
                "Missed Characters: " + missedCharacter + "\n" +
                "Accuracy: " + accuracy + "%";

        Label statisticsLabel = new Label(statisticsText);
        statisticsLabel.setStyle("-fx-font-size: 16px;");

        return statisticsLabel;
    }

    private static void startTestDialog() {
        TextInputDialog dialog = new TextInputDialog("60");
        dialog.setTitle("Start Test");
        dialog.setHeaderText("Enter the test duration in seconds:");
        dialog.setContentText("Duration (seconds):");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(duration -> {
            try {
                int testTime = Integer.parseInt(duration);
                if (testTime > 0) {
                    MonkeyTypeModel.startTest(testTime);
                } else {
                    showAlert("Please enter a positive integer for the test duration.");
                    startTestDialog();
                }
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid integer for the test duration.");
                startTestDialog();
            }
        });
    }

    private static void animateJumpEffect(Text text) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.2), text);
        translateTransition.setFromY(0);
        translateTransition.setToY(-5);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.setOnFinished(event -> text.setY(0));
        translateTransition.play();
    }

    static void animateWaveEffect(TextFlow textFlow, int currentIndex) {
        Text currentCharacter = (Text) textFlow.getChildren().get(currentIndex);
        animateJumpEffect(currentCharacter);
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Duration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
