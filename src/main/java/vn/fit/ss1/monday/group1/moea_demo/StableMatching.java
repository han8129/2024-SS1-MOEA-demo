package vn.fit.ss1.monday.group1.moea_demo;

import com.github.javafaker.Faker;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.problem.AbstractProblem;

import java.util.*;

public class StableMatching extends AbstractProblem {
    public static int[][] DOM_PREFS = {{0,1,2}, {2, 0, 1}, {1, 2, 0}};
    public static int[][] STUDENT_PREFS = {{2, 0, 1}, {2, 1, 0}, {2,1,0}};
    private static final Faker faker = new Faker();

    public StableMatching() {
        super(1 ,1 , 0);
    }

    @Override
    public void evaluate(Solution solution) {
        int[] matches = EncodingUtils.getPermutation(solution.getVariable(0));
        int[] ranks = new int[matches.length];

        // Get each dormitories ranking
        for (int i = 0; i < matches.length - 1; ++i) {
            int pairedDom = matches[i];

            OptionalInt domIndex = Arrays.stream(StableMatching.STUDENT_PREFS[i])
                .filter(dom -> pairedDom == dom)
                .findFirst();

            ranks[i] = domIndex.isPresent()
                ? domIndex.getAsInt()
                : 99;
        }

        // the lower the total ranking the better
        solution.setObjective(0, Arrays.stream(ranks).sum());

        // Have no constraint
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(1, 1 ,0);
        int[] matches = new int[StableMatching.STUDENT_PREFS.length];

        for (int i = 0; i < matches.length - 1; i++) {
            for (int j = 0; j < 1000; j++) {
                int value = faker.number().numberBetween(0 , DOM_PREFS.length);
                OptionalInt duplicate = Arrays.stream(matches)
                    .filter(match -> value == match)
                    .findFirst();

                if (duplicate.isEmpty()) {
                    matches[i] = value;
                    break;
                }
            }
        }

        // the solution is array of students' dormitory, with the first element being first student's dormitory index
        solution.setVariable(0, new Permutation(matches));
        return solution;
    }
}
