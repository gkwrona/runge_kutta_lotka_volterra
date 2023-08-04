package com.example.runge_kutta_lotka_volterra;
import java.util.ArrayList;

public class EqSolver {
    /**
     * returns points being solution of lotka-volterra equation, for given t,x,y, equations parameters and time domain
     * integration step is given by user. methid is runge kutta IV.
     * when beta >>> alfa or delta >>>> gamma, method may return NaN or Inf - its just division by 0 or infinity - see wikipedia pages about RK and LV
     * zwraca punkty bedace rozwiazaniem ukladu rownan lotki volterry dla zadanych t,x,y oraz parametrow rownania i dziedziny czasu.
     * oblicza je przy pomocy algorytmu runge kutta 4 rzedu, o zadanym w parametrze kroku calkowania.
     * metoda w pewnych przypadkach, dla niektorych parametrow rowania (kiedy beta albo delta sa zbyt duze w stosunku do alfa albo gamma) moze zwracac liste zawierajaca NaN albo Inf - trzeba to wziac pod uwage podczas
     * uzywania efektu jej dzialania dalej i zabezpieczyc sie na taka sposobnosc.
     *
     * @param initCond - dwuelementowa tablica Double[] taka ze initCond[0] - poczatkowa liczba ofiar, initCond[1] - poczatkowa liczba drapiezcow}
     * @param a        - alfa z rownan lotki volterra
     * @param b        - beta z rownan lotki volterra
     * @param d        - delta z rownan lotki volterra
     * @param g        - gamma z rownan lotki volterra
     * @param h        - krok calkowania integration point
     * @param t0       - czas poczatkowy starting time
     * @param tf       - czas koncowy end time
     * @return ArrayList <double[]> taką że, każdy element Listy to tablica double: {t, x, y}, gdzie t - czas (time), x - wielkosc populacji ofiar (prey population), y -wielkosc populacji drapiezcow (carnivore population)
     **/
    public static ArrayList<double[]> callRK4LotkaVolterra(double[] initCond, double t0, double tf, double h, double a, double b, double g, double d) {

        return rk4(initCond, t0, tf, h, a, b, g, d);

    }

    //mozna by zrobic a,b,c,d globalne, ale wolalam uniknac koniecznosci tworzenia obiektow w Controllerze zeby nic nie pomieszac, bo ja sie zapominam
    private static double[] fx(double t, double x, double y, double a, double b, double g, double d) { // parametr t tu nie jest do niczego potrzebny ale dla wiekszej elastycznosci go tu zostawilam
        //a = 1.1, b = 0.4, g = 0.4, d = 0.1 < - deafultowe parametry w interfejsie
        return new double[]{
                a * x - b * x * y, // czyli dx/dt
                d * x * y - g * y // czyli dy/dt
        };
    }// zwraca tablice taka ze jej dwa elementy to: {dx/dt , dy/dt}, dla zadanych w konstruktorze parametrow rownania

    private static ArrayList<double[]> rk4(double[] initCond, double t0, double tf, double h, double a, double b, double g, double d) {
        ArrayList<double[]> results = new ArrayList<>();
        double[] xn = initCond; // i know that its redundant and that i can use initCond instead of creating new variable, but its to improve readilibity of the algoritm below
        double tn = t0;
        double[] k1, k2, k3, k4, dx;
        //System.out.println(tn + "\t" + xn[0] + " " + xn[1] + " war pocz");

        while (tn < tf) {
            k1 = fx(tn, xn[0], xn[1], a, b, g, d);
            k2 = fx(tn + h / 2, xn[0] + h * k1[0] / 2, xn[1] + h * k1[1] / 2, a, b, g, d);
            k3 = fx(tn + h / 2, xn[0] + h * k2[0] / 2, xn[1] + h * k2[1] / 2, a, b, g, d);
            k4 = fx(tn + h, xn[0] + h * k3[0], xn[1] + h * k3[1], a, b, g, d);
            dx = new double[]
                    {h / 6 * (k1[0] + 2 * k2[0] + 2 * k3[0] + k4[0]),
                            h / 6 * (k1[1] + 2 * k2[1] + 2 * k3[1] + k4[1])};
            xn[0] = xn[0] + dx[0];
            xn[1] = xn[1] + dx[1];
            tn = tn + h;
            results.add(new double[]{tn, xn[0], xn[1]});
            System.out.println(tn + "\t" + xn[0] + "\t" + xn[1]);
        }
        return results;

    }
// remove commentation below, if you want to run this code in browser compilator
 //!!!!!!!!!!! odkomentuj metode ponizej , jesli chcesz wkleic ten kod w jakims internetowym kompilatorze w przegladarce i zobaczyc jak dziala

    /*
    public static void main(String[] args) {
        ArrayList<double[]> res = callRK4LotkaVolterra(new double[]{20.0, 5.0}, 0, 70, 0.1,1.1,0.4,0.4,0.1);
        System.out.println("t \t x \t ");
        for (double[] el : res)
        {
            System.out.println(el[0] + "\t" + el[1] + "\t" + el[2]);
        }
    }

     */

}
