module com.example.mibocatafx {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;

    opens com.example.mibocatafx to javafx.fxml;
    exports com.example.mibocatafx;
    exports com.example.mibocatafx.controller;
    opens com.example.mibocatafx.controller to javafx.fxml;

    // Abre el paquete models a javafx.base para que PropertyValueFactory pueda acceder a las propiedades
    opens com.example.mibocatafx.models to javafx.base, org.hibernate.orm.core;

}

