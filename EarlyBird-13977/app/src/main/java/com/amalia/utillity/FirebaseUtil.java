/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amalia.utillity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseUtil {
    private static final String FIREBASE_KEY_PATH = "D:/a112213977/PBO/simplenote.json";

    static {
        initializeFirebase();
    }

    public static void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream(FIREBASE_KEY_PATH);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://simplenote-c5045-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) { // Prevent re-initializing
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException("Firebase initialization failed.", e);
        }
    }
}
