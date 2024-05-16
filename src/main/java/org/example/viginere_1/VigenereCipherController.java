package org.example.viginere_1;

// VigenereCipherController.java
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class VigenereCipherController {

    @FXML
    private TextField inputField;

    @FXML
    private TextField keyField;

    @FXML
    private TextField outputField;
    @FXML
    private TextField frequencyResultsField;


    @FXML
    protected void encryptText() {
        String text = inputField.getText();
        String key = keyField.getText();
        String encryptedText = encryptVigenere(text, key);
        updateOutput(encryptedText);
    }



    @FXML
    protected void decryptText() {
        String text = inputField.getText();
        String key = keyField.getText();
        String decryptedText = decryptVigenere(text, key);
        updateOutput(decryptedText);
    }

    private String encryptVigenere(String text, String key) {
        text = text.toUpperCase(); // Convertim textul în majuscule
        key = key.toUpperCase(); // Convertim cheia în majuscule
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (!Character.isLetter(currentChar)) {
                encryptedText.append(currentChar); // Ignorăm spațiile sau alte caractere care nu sunt litere
                continue;
            }
            char base = 'A';
            int shift = key.charAt(keyIndex) - 'A';
            char encryptedChar = (char) ((currentChar + shift - base) % 26 + base);
            encryptedText.append(encryptedChar);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return encryptedText.toString();
    }

    private String decryptVigenere(String text, String key) {
        text = text.toUpperCase(); // Convertim textul în majuscule
        key = key.toUpperCase(); // Convertim cheia în majuscule
        StringBuilder decryptedText = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (!Character.isLetter(currentChar)) {
                decryptedText.append(currentChar); // Ignorăm spațiile sau alte caractere care nu sunt litere
                continue;
            }
            char base = 'A';
            int shift = key.charAt(keyIndex) - 'A';
            char decryptedChar = (char) ((currentChar - shift - base + 26) % 26 + base);
            decryptedText.append(decryptedChar);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return decryptedText.toString();
    }
    private void updateOutput(String result) {
        outputField.setText(result);
    }

    @FXML
    protected void saveToFile() {
        String encryptedText = outputField.getText();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Encrypted Text");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            saveToFile(encryptedText, selectedFile);
        }
    }



    @FXML
    protected void browseTextFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Text File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            try {
                String fileContent = readFileContent(filePath);
                inputField.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    private String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }


    private void saveToFile(String content, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Map<Character, Integer> calculateLetterFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        text = text.toUpperCase(); // Asigurăm că textul este în majuscule
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                frequencyMap.put(currentChar, frequencyMap.getOrDefault(currentChar, 0) + 1);
            }
        }
        return frequencyMap;
    }
    @FXML

    protected void analyzeFrequency() {
        String text = inputField.getText(); // Obține conținutul textului din câmpul de intrare
        Map<Character, Integer> frequencyMap = calculateLetterFrequency(text);

        // Construiește un șir de caractere pentru afișarea rezultatelor
        StringBuilder resultsBuilder = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            resultsBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Atribuie rezultatele la câmpul de text pentru afișare
        frequencyResultsField.setText(resultsBuilder.toString());
    }




}
