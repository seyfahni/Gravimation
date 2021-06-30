module de.seyfarth.gravimation {
    requires javafx.controls;
    requires javafx.fxml;
    opens de.seyfarth.gravimation to javafx.fxml;
    exports de.seyfarth.gravimation;
    exports de.seyfarth.math;
}
