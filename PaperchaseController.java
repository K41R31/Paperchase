package Paperchase;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;

public class PaperchaseController  {

    private int level;
    private int levelProgress;
    private LinearGradient linearGradient;
    private Point[] origLevelBarPoints;
    private Timeline animation1;
    private IOData ioData;
    @FXML
    private Polyline levelBarProgress;
    @FXML
    private Polyline levelBarStroke;
    @FXML
    private Text startQuestText, playerName, percentageText, levelText, testLvlUp, errorText;
    @FXML
    private Rectangle startQuestRect;
    @FXML
    private VBox menuPane, loginPane, questDetailsPane, startQuestPane, questOverviewPane;
    @FXML
    private ImageView menuOpener, menuItem0,  menuItem1, menuItem2, menuItem3, menuItem4, statusBar, loginBackground, startQuestPaneBackgr;
    @FXML
    private AnchorPane rootPane, playerProfilePane, questsPane, friendsPane, fullMapPane, loginRoot;
    @FXML
    private ProgressBar levelProgressBarProfile;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox saveLoginChecker;

    @FXML
    private void initialize() {
        addEnterListener(emailField);
        addEnterListener(passwordField);
        addEnterListener(saveLoginChecker);
        ioData = new IOData();
        animation1 = new Timeline();
        level = 5;
        levelText.setText("LV"+level);
        levelProgress = 81;
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#fc5d1e")), new Stop(0.4, Color.web("#ff5036"))};
        linearGradient = new LinearGradient(0.5, 0, 0.75, 1, true,  CycleMethod.NO_CYCLE, stops);
        startQuestText.setFill(linearGradient);
        startQuestRect.setStroke(linearGradient);

        origLevelBarPoints = new Point[] {
                new Point(192, 100),
                new Point(72, 220),
                new Point(192, 340),
                new Point(312, 220),
                new Point(192, 100)
        };
        levelBarProgress.getPoints().clear();
        levelBarStroke.getPoints().clear();
        for (Point levelBarPoint : origLevelBarPoints) {
            levelBarStroke.getPoints().add((double)levelBarPoint.x);
            levelBarStroke.getPoints().add((double)levelBarPoint.y);
            levelBarProgress.getPoints().add((double)levelBarPoint.x);
            levelBarProgress.getPoints().add((double)levelBarPoint.y);
        }
        updatePoints();
    }

    private void animation() {
        if (animation1.getStatus().toString().equals("RUNNING")) {
            animation1.stop();
        } else {
            animation1.getKeyFrames().clear();
            animation1.getKeyFrames().addAll(
                    new KeyFrame(new Duration(40), event -> {
                        updatePoints();
                        percentageText.setText(levelProgress + "%");
                        levelProgress++;
                        if (levelProgress == 101) {
                            levelProgress = 0;
                            updatePoints();
                            level++;
                            levelText.setText("LV" + level);
                        }
                    })
            );
            animation1.setCycleCount(Animation.INDEFINITE);
            animation1.play();
        }
    }

    private void updatePoints() {
        Point[] levelBarPoints;
        if (levelProgress >= 75) {
            levelBarPoints = new Point[5];
            levelBarPoints[0] = origLevelBarPoints[0];
            levelBarPoints[1] = origLevelBarPoints[1];
            levelBarPoints[2] = origLevelBarPoints[2];
            levelBarPoints[3] = origLevelBarPoints[3];
            int x = 100 - levelProgress;
            levelBarPoints[4] = new Point((int)(x * 4.8 + 192), (int)(x * 4.8 + 100));
        } else if (levelProgress >= 50) {
            levelBarPoints = new Point[4];
            levelBarPoints[0] = origLevelBarPoints[0];
            levelBarPoints[1] = origLevelBarPoints[1];
            levelBarPoints[2] = origLevelBarPoints[2];
            int x = - 50 + levelProgress;
            int y = 50 - x;
            levelBarPoints[3] = new Point((int)(x * 4.8 + 192), (int)(y * 4.8 + 100));
        } else if (levelProgress >= 25) {
            levelBarPoints = new Point[3];
            levelBarPoints[0] = origLevelBarPoints[0];
            levelBarPoints[1] = origLevelBarPoints[1];
            int x = - 50 + levelProgress;
            int y = 50 + x;
            levelBarPoints[2] = new Point((int)(x * 4.8 + 192), (int)(y * 4.8 + 100));
        } else {
            levelBarPoints = new Point[2];
            levelBarPoints[0] = origLevelBarPoints[0];
            int x = - levelProgress;
            int y = - x;
            levelBarPoints[1] = new Point((int)(x * 4.8 + 192), (int)(y * 4.8 + 100));
        }
        updateLevelBarProgress(levelBarPoints);
    }

    private void updateLevelBarProgress(Point[] levelBarPoints) {
        levelBarProgress.getPoints().clear();
        for (Point levelBarPoint : levelBarPoints) {
            levelBarProgress.getPoints().add((double)levelBarPoint.x);
            levelBarProgress.getPoints().add((double)levelBarPoint.y);
        }
        levelProgressBarProfile.setProgress((double)levelProgress/100);
    }

    private void toggleMenu() {
        if (!menuPane.isVisible()) {
            menuPane.setVisible(true);
            menuPane.setDisable(false);
            Timeline openMenu = new Timeline();
            openMenu.getKeyFrames().addAll(new KeyFrame(new Duration(300),
                    new KeyValue(menuPane.prefWidthProperty(), 384, Interpolator.EASE_BOTH),
                    new KeyValue(menuOpener.layoutXProperty(), 358, Interpolator.EASE_BOTH),
                    new KeyValue(menuOpener.scaleXProperty(), -1, Interpolator.EASE_BOTH))
            );
            openMenu.setOnFinished(event -> {
                menuItem0.setFitWidth(384);
                menuItem1.setFitWidth(384);
                menuItem2.setFitWidth(384);
                menuItem3.setFitWidth(384);
                menuItem4.setFitWidth(384);
            });
            openMenu.play();
        } else {
            menuItem0.setFitWidth(1);
            menuItem1.setFitWidth(1);
            menuItem2.setFitWidth(1);
            menuItem3.setFitWidth(1);
            menuItem4.setFitWidth(1);
            Timeline closeMenu = new Timeline();
            closeMenu.getKeyFrames().addAll(new KeyFrame(new Duration(300),
                    new KeyValue(menuPane.prefWidthProperty(), 0, Interpolator.EASE_BOTH),
                    new KeyValue(menuOpener.layoutXProperty(), 10, Interpolator.EASE_BOTH),
                    new KeyValue(menuOpener.scaleXProperty(), 1, Interpolator.EASE_BOTH))
            );
            closeMenu.setOnFinished(event -> {
                menuPane.setVisible(false);
                menuPane.setDisable(true);
            });
            closeMenu.play();
        }
    }

    @FXML
    private void toggleQuest() {
        if (!questDetailsPane.isVisible()) {
            Timeline openQuest = new Timeline();
            openQuest.getKeyFrames().addAll(new KeyFrame(new Duration(500),
                    new KeyValue(questDetailsPane.prefHeightProperty(), 246, Interpolator.EASE_BOTH))
            );
            openQuest.setOnFinished(event -> fadeIn(questDetailsPane));
            openQuest.play();
        } else {
            Timeline openQuest = new Timeline();
            openQuest.getKeyFrames().addAll(new KeyFrame(new Duration(500),
                    new KeyValue(questDetailsPane.prefHeightProperty(), 0, Interpolator.EASE_BOTH))
            );
            fadeOut(questDetailsPane);
            openQuest.play();
        }
    }

    @FXML
    private void startQuestEntered() {
        startQuestText.setFill(Color.web("#23231b"));
        startQuestRect.setFill(linearGradient);
    }

    @FXML
    private void startQuestExited() {
        startQuestText.setFill(linearGradient);
        startQuestRect.setFill(Color.TRANSPARENT);
    }

    @FXML
    private void startQuestClicked() {
        fadeIn(questsPane);
        questsPane.setDisable(false);
    }

    @FXML
    private void questsFromMenu() {
        fadeIn(questsPane);
        questsPane.setDisable(false);
        toggleMenu();
    }

    @FXML
    private void profileFromMenu() {
        fadeIn(playerProfilePane);
        playerProfilePane.setDisable(false);
        toggleMenu();
    }

    @FXML
    private void menuOpenerClicked() {
        toggleMenu();
    }

    @FXML
    private void togglePlayerProfile() {
        fadeIn(playerProfilePane);
        playerProfilePane.setDisable(false);
    }

    @FXML
    private void questMenuBack() {
        if (startQuestPane.isVisible()) {
            fadeOut(startQuestPane);
            startQuestPane.setDisable(true);
            fadeIn(questOverviewPane);
            fadeOut(startQuestPaneBackgr);
        } else {
            fadeOut(questsPane);
            questsPane.setDisable(true);
        }
    }

    @FXML
    private void playerProfileMenuBack() {
        fadeOut(playerProfilePane);
        playerProfilePane.setDisable(true);
    }

    @FXML
    private void friendsMenuOpen() {
        friendsPane.setDisable(false);
        fadeIn(friendsPane);
        toggleMenu();
    }

    @FXML
    private void friendsMenuBack() {
        friendsPane.setDisable(true);
        fadeOut(friendsPane);
    }

    @FXML
    private void questAdded() {
        fadeOut(startQuestPane);
        startQuestPane.setDisable(true);
        questsPane.setDisable(true);
        fadeOut(questsPane);
        fadeIn(questOverviewPane);
        fadeOut(startQuestPaneBackgr);
    }

    @FXML
    private void testLvlUpClicked() {
        animation();
    }

    @FXML
    private void testLvlUpEntered() {
        testLvlUp.setOpacity(1);
    }

    @FXML
    private void testLvlUpExited() {
        testLvlUp.setOpacity(0);
    }

    @FXML
    private void toggleStartQuestPane() {
        if (startQuestPane.getOpacity() == 0) {
            startQuestPane.setDisable(false);
            fadeIn(startQuestPane);
            fadeOut(questOverviewPane);
            fadeIn(startQuestPaneBackgr);
        } else {
            startQuestPane.setDisable(true);
            fadeOut(startQuestPane);
            fadeIn(questOverviewPane);
            fadeOut(startQuestPaneBackgr);
        }
    }

    @FXML
    private void loginClicked() {
        String password = ioData.isAlreadyRegistered(emailField.getText());
        if (password.equals("false")) {
            errorText.setText("Es besteht kein Account mit diesem Namen.");
            fadeIn(errorText);
        } else {
            if (passwordField.getText().equals(password)) toggleLoginPane();
            else {
                errorText.setText("Falsches Passwort.");
                fadeIn(errorText);
            }
        }
    }

    private void toggleLoginPane() {
        Timeline closeLogin = new Timeline();
        closeLogin.getKeyFrames().addAll(
                new KeyFrame(new Duration(500),
                        new KeyValue(loginBackground.translateYProperty(), 350, Interpolator.EASE_IN),
                        new KeyValue(loginBackground.scaleYProperty(), 0, Interpolator.EASE_IN)),
                new KeyFrame(new Duration(200),
                        new KeyValue(loginPane.opacityProperty(), 0, Interpolator.EASE_IN))
        );
        closeLogin.setOnFinished(event -> {
            loginRoot.setVisible(false);
            loginRoot.setDisable(true);
        });
        closeLogin.play();
        loginPane.setDisable(true);
    }

    @FXML
    private void toggleFullMap() {
        if (fullMapPane.getOpacity() == 0) {
            toggleMenu();
            Timeline rotateToMap = new Timeline();
            rotateToMap.getKeyFrames().addAll(new KeyFrame(new Duration(1000),
                    new KeyValue(rootPane.rotateProperty(), -90, Interpolator.EASE_BOTH),
                    new KeyValue(Main.root.layoutXProperty(), 100, Interpolator.EASE_BOTH),
                    new KeyValue(Main.root.layoutYProperty(), -400, Interpolator.EASE_BOTH))
            );
            rotateToMap.play();
            rotateToMap.setOnFinished(event -> {
                fullMapPane.setDisable(false);
                fadeIn(fullMapPane);
                fadeOut(statusBar);
            });
        } else {
            Timeline rotateToOrigin = new Timeline();
            rotateToOrigin.getKeyFrames().addAll(new KeyFrame(new Duration(1000),
                    new KeyValue(rootPane.rotateProperty(), 0, Interpolator.EASE_BOTH),
                    new KeyValue(Main.root.layoutXProperty(), 378, Interpolator.EASE_BOTH),
                    new KeyValue(Main.root.layoutYProperty(), 100, Interpolator.EASE_BOTH))
            );
            rotateToOrigin.play();
            fullMapPane.setDisable(true);
            fadeOut(fullMapPane);
            fadeIn(statusBar);
        }
    }

    private void addEnterListener(Node node) {
        node.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) loginClicked();
        });
    }

    private void fadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOut(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
