package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField username_login;

    @FXML
    private PasswordField pass_login;

    @FXML
    private Button login_user, sign_in_user_login;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        login_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.loginUser(event, username_login.getText(), pass_login.getText());

            }
        });

        sign_in_user_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sign-in.fxml", "Cadastre-se no Sistema!", null, null);
            }
        });
    }

}
