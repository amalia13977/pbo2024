/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amalia.service;

import java.io.FileWriter;
import java.io.IOException;

public class NoteService {

    public void createNoteOutsideProject(String noteContent) {
        // Tentukan path di luar folder proyek, misalnya direktori pengguna
        String userHome = System.getProperty("user.home");
        String filePath = userHome + "/note.txt";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(noteContent);
            System.out.println("Note saved successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing note: " + e.getMessage());
        }
    }
}
