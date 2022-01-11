package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;

public class ComposeController extends Controller {

    @FXML
    private TextField senderTextField;
    @FXML
    private TextField recipientTextField;
    @FXML
    private TextField objectTextField;
    @FXML
    private HTMLEditor messageEditor;

    @FXML
    private Button sendBtn;
    @FXML
    private Button cancelBtn;


    @FXML
    public void initialize(){

    }


    @FXML
    private void onCancelButtonClick(MouseEvent mouseEvent) {
        ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
    }
}
