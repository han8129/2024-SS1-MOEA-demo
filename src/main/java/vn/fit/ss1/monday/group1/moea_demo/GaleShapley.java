package vn.fit.ss1.monday.group1.moea_demo;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class GaleShapley {
    // return array of order by student pref, with element being dormitory index
    public static int[] match(int[][] studentPrefs, int[][] domPrefs) {
        int[] matches = Arrays.stream(new int[studentPrefs.length]).map(x->-1).toArray();

        // if there exist -1 in matches array
        while (Arrays.stream(matches).filter(x -> x == -1).findAny().isPresent()) {

            for (int studentIndex = 0; studentIndex < studentPrefs.length; ++studentIndex) {
                int currentDomRank = matches[studentIndex];

                if (currentDomRank == -1) {
                    // get offers' ranking
                    int finalStudentIndex1 = studentIndex;


                    for (Integer offer : getOffers(domPrefs, matches)) {
                        if (matches[finalStudentIndex1] > getRank(offer, studentPrefs[finalStudentIndex1]) || matches[finalStudentIndex1] == -1)
                        {
                            matches[finalStudentIndex1] = offer;
                        }
                    }
                } else {
                    // rematch
                    for (int domIndex: getOffers(domPrefs, matches)) {
                        int finalStudentIndex = studentIndex;
                        OptionalInt offerRank = Arrays.stream(studentPrefs[finalStudentIndex]).filter(x -> x == finalStudentIndex).findFirst();
                        if (offerRank.isPresent()) {
                            if (currentDomRank > offerRank.getAsInt()) {
                                matches[finalStudentIndex] = offerRank.getAsInt();
                            }
                        }
                    }
                }
            }
        }
        return matches;
    }

    // return index of dormitory with no student
    private static List<Integer> getOffers(int[][] domPrefs, int []matches ) {
        List<Integer> offers = new ArrayList<>(0);
        for (int i = 0; i < domPrefs.length; ++i) {
            final int finalI = i;
            // find unpaired dormitory
            if (Arrays.stream(matches).filter(domIndex -> {
                return domIndex == finalI;
            }).findAny().isEmpty())
            {
                offers.add(i);
            }

        }

        return offers;
    }

    private static int getRank(int element, int[] prefs) {
        for (int i = 0; i < prefs.length ; i++) {
            if (element == prefs[i]) {
                return i;
            }
        }

        return 99;
    }
}
