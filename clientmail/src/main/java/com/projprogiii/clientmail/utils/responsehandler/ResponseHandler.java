package com.projprogiii.clientmail.utils.responsehandler;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.controller.Controller;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.lib.objects.ServerResponse;

public class ResponseHandler {

    public static void handleResponse(ServerResponse resp,
                                      Controller controller,
                                      SuccessHandler successHandler,
                                      Object successArg){

        if (resp == null) {
            AlertManager.showAlert(
                    controller.getDangerAlert(),
                    AlertText.NO_CONNECTION);
        }
        else {
            AlertManager.hideAlert(ClientApp.sceneController
                    .getController(SceneName.MAIN)
                    .getDangerAlert(), 1);
            AlertManager.hideAlert(ClientApp.sceneController
                    .getController(SceneName.COMPOSE)
                    .getDangerAlert(), 1);

            switch (resp.responseName()){
                case SUCCESS -> successHandler.handle(
                        successArg != null ?
                                successArg :
                                resp
                );
                case ILLEGAL_PARAMS -> AlertManager.showTemporizedAlert(
                        controller.getDangerAlert(),
                        AlertText.ILLEGAL_PARAMS,
                        2);
                case INVALID_RECIPIENTS -> AlertManager.showTemporizedAlert(
                        controller.getDangerAlert(),
                        AlertText.INVALID_RECIPIENTS,
                        2);
                case OP_ERROR -> AlertManager.showTemporizedAlert(
                        controller.getDangerAlert(),
                        AlertText.OP_ERROR,
                        2);
            }
        }
    }
}
