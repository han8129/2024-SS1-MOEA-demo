package vn.fit.ss1.monday.group1.moea_demo;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;

public class Main {
    public static void main(String[] args) {

        Algorithm algorithm = new NSGAII(new StableMatching());
        algorithm.run(10000);

        algorithm.getResult().display();
    }
}