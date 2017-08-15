import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bri on 11-Nov-16.
 */
public class Main {
    public static void main(String[] args) {

        /////////////////////////////////////////////////
        //
        //   Creation d'un mot a répétition arithmétique
        //
        /////////////////////////////////////////////////
        System.out.println("---------------------------------------------");
        System.out.println("Algorithm start:");
        double lengthOfWord = 50;

        double maxJump = 2.0;

        // -- Nombre de symbole voulu dans lequel piocher
        int nbrSymbolAvailable = 5;
        //  Utiliser la méthode :Utils.computeNumberSymbolByPosition(maxJump); pour récupérer une liste d esymbole  2k + 10 √k

        // -- par position défini le nombre de symbole existant
        int nbrSymbolsByLists = 5;
        //  Utiliser la méthode :Utils.computeNumberSymbolByPosition(maxJump); pour récupérer une liste d esymbole  2k + 10 √k

        // -- nombre de tests totaux
        int nbrDeTests = 10000;

        String[] availableSymbole = Utils.getSymbolesListAvailable(nbrSymbolAvailable);

        ArrayList<ArrayList<String>> listOfSymbolByPlace = Utils.getArrayOfListsWithSymbole((int) lengthOfWord, availableSymbole, nbrSymbolsByLists);

        double iterationAverage = 0.0;
        double iterationMax = 0.0;

        int bigestSquareAmongAllTest = 0;
        double averageSquareAmongAllTest = 0.0;
        System.out.println("---------------------------------------------");
        System.out.println("Résultats pour " + nbrSymbolsByLists + " parmi " + nbrSymbolAvailable + " Saut:" + maxJump);

        for (int i = 0; i < nbrDeTests; i++) {

            Map result = Sequence.buildWordSquareFreeArithmeticProgression((int) lengthOfWord, listOfSymbolByPlace, (int) maxJump);

            int currentIeration = result.get("iteration");

            iterationAverage += (double) currentIeration;
            String biggestQuadraticWord = (String) result.get("biggestQuadraticWord");
            averageSquareAmongAllTest += biggestQuadraticWord.length();

            if (biggestQuadraticWord.length() > bigestSquareAmongAllTest) {

                bigestSquareAmongAllTest = biggestQuadraticWord.length();

            }

            if (currentIeration > iterationMax) {

                iterationMax = currentIeration;
            }

            if (i % 20 == 0) {
                System.out.println("test numero:" + i);
            }

        }


        System.out.println("");
        System.out.println("Average iteration:   " + iterationAverage / nbrDeTests);
        System.out.println("Max iteration:   " + iterationMax);
        System.out.println("");

        System.out.println("Average square length:   " + averageSquareAmongAllTest / nbrDeTests);
        System.out.println("Max square length:  " + bigestSquareAmongAllTest);
        System.out.println("---------------------------------------------");


        /////////////////////////////////////////////////
        //
        //   Creation d'un mot a répétition non  arithmétique
        //
        /////////////////////////////////////////////////


        int[] lengthToTest = {100, 50, 50};//,
        int sequenceLengthDecimal = 10;
        System.out.println("Start ]]]]]]]]]]]]]]]");


        lengthOfWord = 10;
        int nbrSymbolsByPosition = 4;

        System.out.println("Word length asked:" + lengthOfWord);





        System.out.println("Start ]]]]]]]]]]]]]]]");
        for (int i = 0; i < lengthToTest.length; i++) {
            ArrayList<ArrayList<String>> arrayOfListsWithSymbole = Utils.getArrayOfListsWithSymbole( lengthToTest[i], Utils.symbolAvailable5, nbrSymbolsByPosition);
            computeStatsRandomList(lengthToTest[i], 10000, arrayOfListsWithSymbole);
        }


        return;

    }

    public static void computeStatsRandomList(int length, int nbrTests, ArrayList<ArrayList<String>> arrayOfListsWithSymbole) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        int nbrSequenceCreated = nbrTests;
        int totalIteration = 0;
        long totalTime = 0;

        int largerRepetitionLength = 0;
        String largerRepetion = "";
        String sequence = "";
        for (int i = 0; i < nbrSequenceCreated; i++) {


            String[] randomSequence = Sequence.getSequenceWithFixedRandomList(length, arrayOfListsWithSymbole);

            totalIteration += Integer.parseInt(randomSequence[1]);

            totalTime += Long.parseLong(randomSequence[2]);

            if (Integer.parseInt(randomSequence[4]) > largerRepetitionLength) {
                largerRepetitionLength = Integer.parseInt(randomSequence[4]);
                largerRepetion = randomSequence[5];
                sequence = randomSequence[6];
            }


        }

        long averageIteration = totalIteration / nbrSequenceCreated;
        System.out.println(length + " ; " + averageIteration + " larger repetition:" + largerRepetitionLength + ' ' + largerRepetion + " " + sequence);


    }



}
