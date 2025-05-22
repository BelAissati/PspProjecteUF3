module bilal.elaissati.pspchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens bilal.elaissati.pspchat.controladors to javafx.fxml;

    exports bilal.elaissati.pspchat;
}
