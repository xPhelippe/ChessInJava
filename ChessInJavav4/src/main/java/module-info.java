module phil {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens phil to javafx.fxml;
    exports phil;
}