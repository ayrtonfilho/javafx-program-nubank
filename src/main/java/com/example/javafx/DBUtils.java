package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.invoke.StringConcatFactory;
import java.sql.*;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String sectorUser) {
        Parent root = null;

        if (username != null && sectorUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, sectorUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800, 525));
        stage.show();

    }

    public static void signUpUser(ActionEvent event, String username, String password, String sectorUser){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx", "root", "#Ase3k5l12074");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("O usuário já existe!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Escolha outro nome de Usuário!");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO users (username, userpassword, sector_user) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, sectorUser);
                psInsert.executeUpdate();


                changeScene(event, "logged-in.fxml", "Sistema Nubank - Bem vindo!", username, sectorUser);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx", "root", "#Ase3k5l12074");
            preparedStatement = connection.prepareStatement("SELECT userpassword, sector_user FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Usuário não encontrado no Banco de Dados!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuário não encontrado no Banco de Dados");
                alert.show();
            }else{
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("userpassword");
                    String retrieveSectorUser = resultSet.getString("sector_user");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "logged-in.fxml","Nubank - Sistema de Acompanhamento", username, retrieveSectorUser);
                    }else {
                        System.out.println("Senha incorreta!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Alguma credencial pode estar incorreta!");
                        alert.show();
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}