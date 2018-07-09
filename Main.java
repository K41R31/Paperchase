package Paperchase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    static AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("PaperchaseView.fxml"));
        primaryStage.setTitle("Paperchase");
        Scene primaryScene = new Scene(root, 1200, 1100);
        root.setLayoutX(378);
        root.setLayoutY(100);
        primaryScene.getStylesheets().add("Paperchase/PaperchaseStyle.css");
        primaryScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(primaryScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        primaryStage.getIcons().add(new Image("Paperchase/Ressources/taskbarIcon.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
