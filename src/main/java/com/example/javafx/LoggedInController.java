package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.lang.invoke.StringConcatFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable{

    @FXML
    private Button button_logout;

    @FXML
    private Label label_sector;

    @FXML
    private Label label_welcome;



    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "sample.fxml","Nubank - Acesso ao Sistema", null, null);

            }
        });


    }

    public void setUserInformation(String username, String sectorUser){
        label_welcome.setText("Olá, "+ username + "!");
        label_sector.setText("Setor Responsável: "+sectorUser);
    }

}
