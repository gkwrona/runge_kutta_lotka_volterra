package com.example.runge_kutta_lotka_volterra;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private Pane pane;
    @FXML
    private TextField alfaTextField;
    @FXML
    private TextField betaTextField;
    @FXML
    private TextField gammaTextField;
    @FXML
    private TextField deltaTextField;
    @FXML
    private TextField xTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField t0TextField;
    @FXML
    private TextField tfTextField;
    @FXML
    private TextField hTextField;
    @FXML
    private TextArea errorTextArea;

    NumberAxis xAxis = new NumberAxis();
    NumberAxis txAxis = new NumberAxis();
    NumberAxis tyAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    ArrayList<double[]> data;
    XYChart.Series<Number, Number> seriesPhase;
    XYChart.Series<Number, Number> seriesTimeX;
    XYChart.Series<Number, Number> seriesTimeY;
    private final String okMessage = "wprowadż dane i odśwież wykres";
    private final String badMessage = "zle wspolczynniki modelu \n wynikami opisujacymi licznosc draipeznikow lub ofiar są NaN albo Inf \n spróbuj z innymi wspolczynnikami";

    @FXML
    ScatterChart<Number, Number> phaseChart = new ScatterChart<>(xAxis, yAxis); //chart; = new ScatterChart<>(xAxis, yAxis);
    @FXML
    LineChart<Number, Number> pop_time_chart = new LineChart<>(txAxis, tyAxis);

    public HelloController() {
    }

    public void onActionButtonRefresh(ActionEvent actionEvent) {

        String uname = null, pass = null;
        String alfa = "0", beta = "0", gamma = "0'", delta = "0", x = "0", y = "0", t0 = "0", tf = "0", h = "0";


        if (alfaTextField.getText() != null) {
            alfa = alfaTextField.getText();
        }
        if (betaTextField.getText() != null) {
            beta = betaTextField.getText();
        }
        if (deltaTextField.getText() != null) {
            delta = deltaTextField.getText();
        }
        if (gammaTextField.getText() != null) {
            gamma = gammaTextField.getText();
        }
        if (xTextField.getText() != null) {
            x = xTextField.getText();
        }
        if (yTextField.getText() != null) {
            y = yTextField.getText();
        }
        if (t0TextField.getText() != null) {
            t0 = t0TextField.getText();
        }
        if (tfTextField.getText() != null) {
            tf = tfTextField.getText();
        }
        if (hTextField.getText() != null) {
            h = hTextField.getText();
        }


        System.out.println(alfa + "a " + beta + " b" + gamma + "g " + delta);

        if (verifyIfDoubles(alfa, beta, gamma, delta, x, y, t0, tf, h)) {
            System.out.println("hurra");
            data = EqSolver.callRK4LotkaVolterra(new double[]{Double.parseDouble(x), Double.parseDouble(y)}, Double.parseDouble(t0), Double.parseDouble(tf), Double.parseDouble(h), Double.parseDouble(alfa), Double.parseDouble(beta), Double.parseDouble(gamma), Double.valueOf(delta));
            phaseChart.getData().clear();
            pop_time_chart.getData().clear();
            rysuj(data);
        }
    }

    private boolean verifyIfDoubles(String alfa, String beta, String gamma, String delta, String x, String y, String t0, String tf, String h) {
        boolean b = true;
        try {
            Double.valueOf(alfa);
            Double.valueOf(beta);
            Double.valueOf(gamma);
            Double.valueOf(delta);
            Double.valueOf(x);
            Double.valueOf(y);
            Double.valueOf(h);
            Double.valueOf(t0);
            Double.valueOf(tf);
        } catch (Exception e) {
            b = false;
            System.out.println("zły input");
            System.out.println(e);
        }
        return b;
    }

    private void rysuj(ArrayList<double[]> data) {

        seriesPhase = new XYChart.Series<>();
        seriesTimeX = new XYChart.Series<>();
        seriesTimeY = new XYChart.Series<>();
        seriesPhase.setName("d(o)");
        seriesTimeX.setName("ofiary(t)");
        seriesTimeY.setName("drapiezcy(t)");
        XYChart.Data<Number, Number> dpPhase;
        XYChart.Data<Number, Number> dpTimeX;
        XYChart.Data<Number, Number> dpTimeY;
        phaseChart.setId("bifurcation-diagram");
        for (int i = 1; i < data.size() - 1; i++) {
            // Add generic parameters (uses the <> operator on the right)
            if (!Double.isNaN(data.get(i)[1]) && !Double.isNaN(data.get(i)[2]) && !Double.isInfinite(data.get(i)[1]) && !Double.isInfinite(data.get(i)[2])) {

                dpPhase = new XYChart.Data<>(data.get(i)[1], data.get(i)[2]);
                dpTimeX = new XYChart.Data<>(data.get(i)[0], data.get(i)[1]);
                dpTimeY = new XYChart.Data<>(data.get(i)[0], data.get(i)[2]);

                seriesPhase.getData().add(dpPhase);
                seriesTimeX.getData().add(dpTimeX);
                seriesTimeY.getData().add(dpTimeY);
                errorTextArea.setText(okMessage);

            } else {
                if (seriesPhase.getData().isEmpty()) {
                    System.out.println("zaden wynik nie był nie-Nan :c co jest co zepsules :c");
                    dpPhase = new XYChart.Data<>(0, 0);
                    seriesPhase.getData().add(dpPhase);
                } else {
                    errorTextArea.setText(badMessage);
                }
            }
        }
        phaseChart.getData().add(seriesPhase);
        pop_time_chart.getData().add(seriesTimeX);
        pop_time_chart.getData().add(seriesTimeY);
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    }

}