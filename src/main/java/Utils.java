import java.util.*;

/**
 * Created by bri on 10-07-17.
 */
public class Utils {


    public static String[] symbolAvailable3 = {"a", "b", "c"};
    public static String[] symbolAvailable4 = {"a", "b", "c", "d"};
    public static String[] symbolAvailable5 = {"a", "b", "c", "d", "e"};
    // -- limité à 12 sauts avec 60 caractères
    public static String[] symbolesAvailable60 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "$", "(", ")", "{", "}", "!", "|", "é", "ç", "à", ",", "?", ".", ";", ":", "/", "+", "=", "~", "-", "@", " ", "ù", "ô"};
    public static String[] symbolesAvailable35 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};


    public static String[] randomizeArray(String[] array) {
        Random rgen = new Random();  // Random number generator

        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            String temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    public static String[] getSymbolesListAvailable(int nbrSymbolAvailable){
        String[] listSymboleAvailable =  new String[nbrSymbolAvailable];
        for(int i = 0 ; i< nbrSymbolAvailable ; i++){
            listSymboleAvailable[i] = symbolesAvailable60[i];
        }
        return listSymboleAvailable;
    }

    public static ArrayList<ArrayList<String>> getArrayOfListsWithSymbole(int arrayLength, String[] availableSymboleList, int nbrOfSymbolByList) {

        ArrayList<ArrayList<String>> arrayOfListsWithSymbole = new ArrayList<ArrayList<String>>();

        // -- Build symboles list by position
        for (int i = 0; i < arrayLength; i++) {
            // -- temporary list building
            ArrayList<String> tmpList = new ArrayList<String>();
            availableSymboleList = Utils.randomizeArray(availableSymboleList);
            for (int j = 0; j < nbrOfSymbolByList; j++) {
                tmpList.add(availableSymboleList[j]);
            }
            arrayOfListsWithSymbole.add(tmpList);
        }

        return arrayOfListsWithSymbole;
    }

    /**
     * Get list of symbole for list
     *
     * @param k
     * @return
     */
    public static String[] getSymbolListForK(double k) {
        int listLength = computeNumberSymbolByPosition(  k );

        List<String> strList = Arrays.asList(symbolesAvailable60);
        Collections.shuffle(strList);

        String[] shortList = new String[listLength];
        for (int i = 0; i < listLength; i++) {
            shortList[i] = strList.get(i);

        }
        return shortList;
    }

    public static int computeNumberSymbolByPosition(double  k ){


        return (int) Math.ceil(2 * k + 10 * Math.sqrt(k));
    }

}
