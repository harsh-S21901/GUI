package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MonkeyTypeController extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        MenuBar menuBar = MonkeyTypeView.createMenuBar();
        root.setTop(menuBar);
        VBox contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.setPadding(new Insets(10));
        MonkeyTypeView.textFlow = new TextFlow();
        MonkeyTypeView.textFlow.setLineSpacing(10);
        contentBox.getChildren().add(MonkeyTypeView.textFlow);
        root.setCenter(contentBox);
        Label keyboardControlsLabel = new Label("""
                Keyboard Controls: Shift + Enter (Restart Test)\s
                 Ctrl + Shift + P (Pause Test)\s
                 Esc (End Test)""");
        keyboardControlsLabel.setPadding(new Insets(10));
        keyboardControlsLabel.setStyle("-fx-font-size: 16px;");
        root.setBottom(keyboardControlsLabel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Monkey-type");
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER && event.isShiftDown()) {
                MonkeyTypeModel.restartTest();
                event.consume();
            } else if (event.getCode() == KeyCode.P && event.isControlDown() && event.isShiftDown()) {
                MonkeyTypeModel.pauseTest();
                event.consume();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                MonkeyTypeModel.endTest();
                event.consume();
            } else if (MonkeyTypeModel.testActive.get() && MonkeyTypeModel.currentIndex < MonkeyTypeView.textFlow.getChildren().size()) {
                if (event.getCode().isLetterKey() || event.getCode().isWhitespaceKey()) {
                    MonkeyTypeModel.currentCharacter = event.getText().charAt(0);
                    MonkeyTypeModel.handleCurrentCharacter();
                    event.consume();
                }
            }
        });

        MonkeyTypeModel.loadDictionaryFile("english");
        MonkeyTypeModel.startTest(60);
    }
}
