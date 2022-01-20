package com.projprogiii.clientmail.utils.responsehandler;

import com.projprogiii.clientmail.controller.Controller;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.lib.objects.ServerResponse;

public class ResponseHandler {

    public static void handleResponse(ServerResponse resp,
                                      Controller controller,
                                      SuccessHandler successHandler){
        if (resp == null) {
            AlertManager.showTemporizedAlert(
                    controller.getDangerAlert(),
                    AlertText.NO_CONNECTION,
                    2);
        }
        switch (resp.responseName()){
            case SUCCESS -> successHandler.handle();
            case ILLEGAL_PARAMS -> AlertManager.showTemporizedAlert(
                    controller.getDangerAlert(),
                    AlertText.INVALID_RECIPIENTS_FORMAT,
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
