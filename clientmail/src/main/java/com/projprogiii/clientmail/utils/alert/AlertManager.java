package com.projprogiii.clientmail.utils.alert;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.controller.MainController;
import com.projprogiii.clientmail.scene.SceneName;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class AlertManager {

    public synchronized static void hideAlert(TextFlow alert, int duration) {
        Timeline idlestage = new Timeline(new KeyFrame(
                Duration.seconds(duration), event -> alert.setVisible(false))
        );
        idlestage.play();
    }

    public synchronized static void showAlert(TextFlow alert, AlertText alertText) {
        ((Text) alert.getChildren().get(0)).setText(alertText.toString());
        alert.setVisible(true);
    }

    public synchronized static void showTemporizedAlert(TextFlow alert, AlertText text, int duration){
        showAlert(alert, text);
        hideAlert(alert, duration);
    }

    public synchronized static void showSuccessSendMessage(AlertText text, int duration){
        ClientApp.sceneController.switchTo(SceneName.MAIN);
        MainController controller = (MainController) ClientApp.sceneController.
                getController(SceneName.MAIN);
        showTemporizedAlert(controller.getSuccessAlert(), text, duration);
    }
}
