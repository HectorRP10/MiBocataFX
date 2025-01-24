module com.example.mibocatafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mibocatafx to javafx.fxml;
    exports com.example.mibocatafx;
}