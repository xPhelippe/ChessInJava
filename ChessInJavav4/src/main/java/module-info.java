module phil {
    requires javafx.controls;
    requires javafx.fxml;

    opens phil to javafx.fxml;
    exports phil;
}