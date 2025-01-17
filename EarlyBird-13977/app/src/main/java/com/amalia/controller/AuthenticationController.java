/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amalia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import com.amalia.service.UserManager;
import java.io.IOException;
import java.util.regex.Pattern;

public class AuthenticationController {
    @FXML private TextField emailField, fullNameField, phoneNumberField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    protected void onRegisterButtonClick() {
        String email = emailField.getText().trim().toLowerCase();
        String password = passwordField.getText();
        String fullName = fullNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();

        if (!isValidEmail(email)) {
            statusLabel.setText("Invalid email format.");
            return;
        }

        if (!isStrongPassword(password)) {
            statusLabel.setText("Password must be at least 8 characters, contain a mix of letters and numbers.");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            statusLabel.setText("Phone number must be numeric and at least 12 digits long.");
            return;
        }

        UserManager.registerUser(email, password, fullName, phoneNumber, success -> Platform.runLater(() -> {
            if (success) {
                statusLabel.setText("Registration successful.");
                try {
                    transitionToMainApp(fullName);
                } catch (IOException e) {
                    e.printStackTrace();
                    statusLabel.setText("Failed to load main application view.");
                }
            } else {
                statusLabel.setText("Registration failed. User may already exist.");
            }
        }));
    }

    @FXML
    protected void onLoginButtonClick() {
        UserManager.authenticateUser(
                emailField.getText().trim().toLowerCase(),
                passwordField.getText(),
                user -> Platform.runLater(() -> {
                    if (user != null) {
                        statusLabel.setText("Login successful.");
                        try {
                            transitionToMainApp(user.getFullName()); // Mengirim ID pengguna ke metode transitionToMainApp
                        } catch (IOException e) {
                            e.printStackTrace();
                            statusLabel.setText("Failed to load main application view.");
                        }
                    } else {
                        statusLabel.setText("Login failed. Please check your credentials.");
                    }
                })
        );
    }

    // Mengalihkan ke tampilan utama aplikasi
    private void transitionToMainApp(String userId) throws IOException { // Menambahkan parameter userId
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/amalia/view/main.fxml"));
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        MainController mainController = loader.getController(); // Mendapatkan controller dari main.fxml
        mainController.setUserId(userId); // Mengatur ID pengguna di MainController
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onShowLoginView() throws IOException {
        changeScene("/com/amalia/view/login.fxml");
    }

    @FXML
    protected void onShowRegisterView() throws IOException {
        changeScene("/com/amalia/view/register.fxml");
    }

    private void changeScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath)); // Memuat file FXML
        Stage stage = (Stage) emailField.getScene().getWindow(); // Mendapatkan stage saat ini
        Scene scene = new Scene(loader.load()); // Membuat scene baru dari file FXML
        stage.setScene(scene); // Mengatur scene baru
        stage.show(); // Menampilkan scene
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 && password.chars().anyMatch(Character::isDigit)
                && password.chars().anyMatch(Character::isLetter);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "\\d{12,}";
        Pattern pat = Pattern.compile(phoneRegex);
        return phoneNumber != null && pat.matcher(phoneNumber).matches();
    }
}
