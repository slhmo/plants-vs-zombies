module testgroup.plantsvszombies {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens testgroup.plantsvszombies to javafx.fxml;
    exports testgroup.plantsvszombies;
    exports testgroup.plantsvszombies.plants;
    opens testgroup.plantsvszombies.plants to javafx.fxml;
    exports testgroup.plantsvszombies.zombies;
    opens testgroup.plantsvszombies.zombies to javafx.fxml;
}