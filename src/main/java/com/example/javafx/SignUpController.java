package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button sign_in_user, login_sign_user;


    @FXML
    private TextField username_sign_in;
    @FXML
    private PasswordField pass_sign_in;


    @FXML
    private RadioButton radio_adm;
    @FXML
    private RadioButton radio_finance;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup toggleGroup = new ToggleGroup();

        radio_adm.setToggleGroup(toggleGroup);
        radio_finance.setToggleGroup(toggleGroup);

        radio_finance.setSelected(true);

        sign_in_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if (!username_sign_in.getText().trim().isEmpty() && !pass_sign_in.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event, username_sign_in.getText(),pass_sign_in.getText(), toggleName);
                }else{
                    System.out.println("Por favor, preencha as informações corretamente!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Preencha todas as informações corretamente!");
                    alert.show();
                }
            }
        });

        login_sign_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml","Nubank - Sistema Fincanceiro", null, null);
            }
        });
    }
}
