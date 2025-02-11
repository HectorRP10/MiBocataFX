module com.example.mibocatafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.example.mibocatafx to javafx.fxml;
    exports com.example.mibocatafx;
    exports com.example.mibocatafx.controller;
    opens com.example.mibocatafx.controller to javafx.fxml;

    opens com.example.mibocatafx.models to org.hibernate.orm.core;

}
