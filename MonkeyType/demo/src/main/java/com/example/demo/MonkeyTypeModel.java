package com.example.demo;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class MonkeyTypeModel {
    private static final String FILE_PATH = "D:\\Desktop Files\\GUI\\MonkeyType\\demo\\dictionary\\dictionary\\";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmm_ss");
    private static final List<Integer> wordPerMinuteList = new ArrayList<>();
    static String currentWord;
    static char currentCharacter = (char) -1;
    static int currentIndex = -1;
    static BooleanProperty testActive = new SimpleBooleanProperty(false);
    static int currentCharacterIndex;
    static List<String> words = new ArrayList<>();
    static Text timerText;
    static int totalCorrectCharacters = 0;
    static int totalIncorrectCharacters = 0;
    static int totalExtraCharacters = 0;
    static int totalMissedCharacters = 0;
    static int wpm;
    private static List<String> dictionary;
    private static long startTime;
    private static int wordCount;
    private static boolean testPaused = false;
    private static long remainingTime;

    private static void displayChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("WPM Chart");

        XYChart.Series<Number, Number> currentWpmSeries = new XYChart.Series<>();
        currentWpmSeries.setName("Current WPM");

        XYChart.Series<Number, Number> averageWpmSeries = new XYChart.Series<>();
        averageWpmSeries.setName("Average WPM");

        int totalWords = wordPerMinuteList.size();
        int cumulativeWords = 0;
        int cumulativeWpm = 0;

        for (int i = 0; i < totalWords; i++) {
            cumulativeWords += 1;
            cumulativeWpm += wordPerMinuteList.get(i);
            int averageWpm = cumulativeWpm / cumulativeWords;

            currentWpmSeries.getData().add(new XYChart.Data<>(i + 1, wordPerMinuteList.get(i)));
            averageWpmSeries.getData().add(new XYChart.Data<>(i + 1, averageWpm));
        }
        lineChart.getData().addAll(currentWpmSeries, averageWpmSeries);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(lineChart, MonkeyTypeView.createStatisticsLabel());

        StackPane rootPane = new StackPane(vbox);
        Scene scene = new Scene(rootPane, 800, 600);

        Platform.runLater(() -> {
            Stage chartStage = new Stage();
            chartStage.setScene(scene);
            chartStage.setTitle("WPM Chart and Statistics");
            chartStage.show();
        });
    }

    private static void calculateWPM(long elapsedTime) {
        double minutes = elapsedTime / 1000.0 / 60.0;
        wpm = (int) (wordCount / minutes);
        wordPerMinuteList.add(wpm);
    }

    static List<String> getAvailableLanguages() {
        List<String> availableLanguages = new ArrayList<>();
        File dictionaryDirectory = new File(FILE_PATH);
        File[] files = dictionaryDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile() && fileName.endsWith(".txt")) {
                    String language = fileName.substring(0, fileName.lastIndexOf('.'));
                    availableLanguages.add(language);
                }
            }
        }
        return availableLanguages;
    }

    static void loadDictionaryFile(String language) {
        words.clear();
        dictionary = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + language + ".txt"))) {
            String word;
            while ((word = reader.readLine()) != null) {
                dictionary.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void handleCurrentCharacter() {
        if (testActive.get() && currentIndex < MonkeyTypeView.textFlow.getChildren().size()) {
            Text currentText = (Text) MonkeyTypeView.textFlow.getChildren().get(currentIndex);
            String currentWordText = currentText.getText();

            if (currentCharacterIndex < currentWordText.length()) {
                char currentChar = currentWordText.charAt(currentCharacterIndex);
                Text characterText = new Text(String.valueOf(currentChar));
                characterText.setStyle("-fx-font-size: 24px;");
                if (currentChar == currentCharacter) {
                    characterText.setFill(Color.GREEN);
                    totalCorrectCharacters++;
                } else {
                    characterText.setFill(Color.RED);
                    totalIncorrectCharacters++;
                }
                if (currentWordText.indexOf(currentCharacter, currentCharacterIndex + 1) != -1) {
                    characterText.setFill(Color.ORANGE);
                    totalExtraCharacters++;
                }
                if (currentCharacter == ' ' && currentCharacterIndex != currentWordText.length()) {
                    characterText.setFill(Color.BLACK);
                    totalMissedCharacters++;
                }
                for (int i = currentCharacterIndex + 1; i < currentWordText.length(); i++) {
                    Text remainingCharText = new Text(String.valueOf(currentWordText.charAt(i)));
                    remainingCharText.setFill(Color.GRAY);
                    remainingCharText.setStyle("-fx-font-size: 24px;");
                    MonkeyTypeView.textFlow.getChildren().add(currentIndex + i, remainingCharText);
                }
                MonkeyTypeView.textFlow.getChildren().set(currentIndex, characterText);
                currentCharacterIndex++;
            }

            if (currentCharacterIndex >= currentWordText.length()) {
                currentCharacterIndex = 0;
                currentIndex++;
                MonkeyTypeView.animateWaveEffect(MonkeyTypeView.textFlow, currentIndex - 1);
                if (currentIndex < MonkeyTypeView.textFlow.getChildren().size()) {
                    currentText = (Text) MonkeyTypeView.textFlow.getChildren().get(currentIndex);
                    currentWord = currentText.getText();
                    currentCharacter = currentWord.charAt(currentCharacterIndex);
                    if (currentCharacter == ' ') {
                        wordCount++;
                        long elapsedTime = System.currentTimeMillis() - startTime;
                        calculateWPM(elapsedTime);
                    }
                } else {
                    endTest();
                }
            }
        }
    }


    static void startTest(int testTime) {
        Random random = new Random();
        int dictionarySize = dictionary.size();
        for (int i = 0; i < 30; i++) {
            int randomIndex = random.nextInt(dictionarySize);
            words.add(dictionary.get(randomIndex));
        }
        startTime = System.currentTimeMillis();
        wordCount = 0;

        currentIndex = 0;
        currentWord = words.get(currentIndex);
        currentCharacterIndex = 0;
        currentCharacter = currentWord.charAt(currentCharacterIndex);

        MonkeyTypeView.textFlow.getChildren().clear();

        for (String word : words) {
            Text wordText = new Text(word + " ");
            wordText.setFill(MonkeyTypeView.COLOR_DEFAULT);
            wordText.setStyle("-fx-font-size: 24px;");
            MonkeyTypeView.textFlow.getChildren().add(wordText);
        }

        timerText = new Text(testTime + " s");
        timerText.setStyle("-fx-font-size: 60px; -fx-font-family: 'Arial'; -fx-text-fill: linear-gradient(to bottom, #ff0000, #ffff00); -fx-stroke: black; -fx-stroke-width: 1px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 2, 0.5, 0, 0);");
        StackPane timerPane = new StackPane(timerText);
        timerPane.setAlignment(Pos.CENTER);
        testActive.set(true);
        BorderPane rootPane = (BorderPane) MonkeyTypeView.textFlow.getScene().getRoot();
        VBox contentBox = new VBox(MonkeyTypeView.textFlow, timerPane);
        contentBox.setAlignment(Pos.CENTER);
        rootPane.setCenter(contentBox);

        startTimer(testTime, timerText);
    }

    private static void startTimer(final int testTime, Text timerText) {
        Thread timerThread = new Thread(() -> {
            long remainingTime = testTime * 1000L;
            while (remainingTime >= 0) {
                if (testPaused) {
                    MonkeyTypeModel.remainingTime = remainingTime;
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long seconds = (remainingTime / 1000) % 60;
                timerText.setText(seconds + " s");
                remainingTime -= 1000;
            }
            endTest();
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }


    static void pauseTest() {
        if (testActive.get()) {
            if (!testPaused) {
                testPaused = true;
                remainingTime = getTimeRemaining();
            } else {
                testPaused = false;
                if (remainingTime > 0) {
                    startTimer((int) (remainingTime / 1000), timerText);
                }
            }
        }
    }


    private static long getTimeRemaining() {
        int testTime = Integer.parseInt(timerText.getText().substring(0, timerText.getText().indexOf(" ")));
        long elapsedTime = System.currentTimeMillis() - startTime;
        return (testTime * 1000L) - elapsedTime;
    }


    public static void endTest() {
        testActive.set(false);
        long elapsedTime = System.currentTimeMillis() - startTime;
        calculateWPM(elapsedTime);
        displayChart();
        String fileName = DATE_FORMAT.format(new Date()) + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < wordPerMinuteList.size(); i++) {
                int wpm = wordPerMinuteList.get(i);
                writer.write(words.get(i) + " -> " + wpm + "wpm");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void restartTest() {
        currentIndex = 0;
        currentCharacterIndex = 0;
        totalCorrectCharacters = 0;
        totalIncorrectCharacters = 0;
        totalExtraCharacters = 0;
        totalMissedCharacters = 0;
        wpm = 0;
        wordPerMinuteList.clear();
        testActive.set(false);
        words.clear();
        startTest(60);
    }
}