module org.example.viginere_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.viginere_1 to javafx.fxml;
    exports org.example.viginere_1;
}