module com.example.runge_kutta_lotka_volterra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.runge_kutta_lotka_volterra to javafx.fxml;
    exports com.example.runge_kutta_lotka_volterra;
}