package vn.fit.ss1.monday.group1.moea_demo;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Solution;
import org.moeaframework.core.initialization.InjectedInitialization;
import org.moeaframework.core.variable.EncodingUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        StableMatching problem = new StableMatching();
        NSGAII algorithm = new NSGAII(problem);
        Solution initSolution = problem.newSolution();
        int[] matches = GaleShapley.match(StableMatching.STUDENT_PREFS, StableMatching.DOM_PREFS);

        EncodingUtils.setPermutation(initSolution.getVariable(0), matches);
        algorithm.setInitialization(new InjectedInitialization(problem, initSolution));
        algorithm.run(10000);
        algorithm.getResult().display();
    }
}