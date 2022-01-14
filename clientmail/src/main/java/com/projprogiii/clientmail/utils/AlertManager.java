package com.projprogiii.clientmail.utils;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.controller.MainController;
import com.projprogiii.clientmail.scene.SceneName;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class AlertManager {

    public static void hideAlert(TextFlow alert, int duration) {
        Timeline idlestage = new Timeline(new KeyFrame(
                Duration.seconds(duration), event -> alert.setVisible(false))
        );
        idlestage.play();
    }

    public static void showAlert(TextFlow alert, AlertText alertText) {
        ((Text) alert.getChildren().get(0)).setText(alertText.toString());
        alert.setVisible(true);
    }

    public static void showTemporizedAlert(TextFlow alert, AlertText text, int duration){
        showAlert(alert, text);
        hideAlert(alert, duration);
    }

    public static void showSuccessSendMessage(AlertText text, int duration){
        ClientApp.sceneController.switchTo(SceneName.MAIN);
        MainController controller = (MainController) ClientApp.sceneController.
                getController(SceneName.MAIN);
        showTemporizedAlert(controller.getSuccessAlert(), text, duration);
    }
}
