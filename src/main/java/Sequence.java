import java.util.*;

/**
 * Created by bri on 10-07-17.
 */
public class Sequence {


    public static String[] getSequenceWithlimitedSymbole(String[] symbolesVector, int length) {
        String sequence = "";
        int iteration = 0;

        long startTime = System.nanoTime();

        while (sequence.length() < length) {
            sequence += symbolesVector[(new Random().nextInt(symbolesVector.length))];

            int repetitionLength = FindRepetition.findRepetitiveSequenceAtTheEnd(sequence).length();
            if (repetitionLength > 0) {
                sequence = sequence.substring(0, sequence.length() - repetitionLength);

            }
            iteration++;
        }
        long endTime = System.nanoTime();

        String[] returnValue = {sequence, iteration + "", (endTime - startTime) + ""};
        return returnValue;
    }



    /**
     * @param
     * @param lengthOfWord
     * @param
     * @return
     */


    public static String[] getSequenceWithFixedRandomList(int lengthOfWord, ArrayList<ArrayList<String>> listByPosition) {

        String word = "";

        String log = "";
        int iteration = 0;

        long startTime = System.nanoTime();
        ArrayList<String> symbolesVector = new ArrayList<String>();
        int largerRepetitionLength = 0;
        String largerRepetion = "";
        String sequenceRepetitionLarger = "";

        while (word.length() < lengthOfWord) {
            symbolesVector = listByPosition.get(word.length());

            String symboleChoosen = symbolesVector.get((new Random().nextInt(symbolesVector.size())));
            word += symboleChoosen;

            String repetition = FindRepetition.findRepetitiveSequenceAtTheEnd(word);

            int repetitionLength = repetition.length();
            log += "0";

            if (repetitionLength > 0) {
                if (repetitionLength > largerRepetitionLength) {
                    largerRepetitionLength = repetitionLength;
                    largerRepetion = repetition;
                    sequenceRepetitionLarger = word;
                }


                word = word.substring(0, word.length() - repetitionLength);
                int tmpResult = (int) 1 - repetitionLength;


                for (int j = 0; j < repetitionLength; j++) {
                    log += "1";
                }
            }

            iteration++;
        }
        long endTime = System.nanoTime();

        String[] returnValue = {word, iteration + "", (endTime - startTime) + "", log, largerRepetitionLength + "", largerRepetion, sequenceRepetitionLarger};
        return returnValue;
    }


    //    public static ArrayList<ArrayList<String>> buildWordSquareFreeArithmeticProgression(int lengthOfWord, ArrayList<ArrayList<String>> listByPosition, int k){
    public static Map  buildWordSquareFreeArithmeticProgression(int lengthOfWord, ArrayList<ArrayList<String>> listByPosition, int k) {

        Map returnElements = new HashMap();
        ArrayList<ArrayList<String>> logs = new ArrayList<ArrayList<String>>();

        // -- logs produced

        String[] word = new String[lengthOfWord];
        String R = "";
        String D = "";
        String O = "";
        String P = "";

        ArrayList<String> symbolesVector = new ArrayList<String>();

        // -- Reading head is not always at the end.
        int currentPositionOfhead = 0;
        int iteration = 0 ;
        String biggestQuadraticWordFound = "" ;

        while (haveEmptyPosition(word, lengthOfWord)) {

            if(iteration > 10000){
                System.out.println(">>>>>>>>>>>>>>>>>impossible de trouver une solution après 10000 itérations");
                System.exit(1);
            }

            symbolesVector = listByPosition.get(currentPositionOfhead);

            // -- retire tous les symboles de -k à k de la liste dispnible
            ArrayList<String> currentSymbolesVector = removeSymboleFromMinJumpUpToJump(symbolesVector ,currentPositionOfhead, word, k);

            String symboleChoosen = null;
            try {
                symboleChoosen = currentSymbolesVector.get((new Random().nextInt(currentSymbolesVector.size())));
            }catch (Exception e){
                System.out.println(">>>>>>>>>>>>>>>>> IL est impossible de trouver un symbole valide, ils ont tous été retiré en fonction du saut");
                System.exit(1);
            }

            word[currentPositionOfhead] = symboleChoosen;

            // -- cherche la répétition avec un jump
            Map repetititionFinded = FindRepetition.findRepetitiveSequenceWithJump(word,currentPositionOfhead,k);
            word = (String[]) repetititionFinded.get("word");

            String biggestQuadraticWord = (String) repetititionFinded.get("biggestQuadraticWord");

            // -- fill the logs
            // -- build Dyck word
            R += "0";
            if(biggestQuadraticWord.length() > 0){
                for(int y =0 ; y <  biggestQuadraticWord.length() ; y++){
                    R += "1";
                }

                // -- register jump
                D += (String) repetititionFinded.get("jump");
                O += (String) repetititionFinded.get("positionOfSymboleAdded");
                P += (String) repetititionFinded.get("indexSymbolAddedInRepetition");
            }

            if(biggestQuadraticWordFound.length() < biggestQuadraticWord.length() ){
                biggestQuadraticWordFound = biggestQuadraticWord;
            }


            // -- place la tete d'écriture au premier endroit ou il y a un espace vide
            currentPositionOfhead = getFirstEmptyElementInString(word, lengthOfWord);

            iteration++;

        }
        //System.out.println("Word created: "+String.join("",word));

        returnElements.put("word",word);
        returnElements.put("biggestQuadraticWord",biggestQuadraticWordFound);
        returnElements.put("iteration",iteration);
        returnElements.put("R",R);
        returnElements.put("D",D);
        returnElements.put("O",O);
        returnElements.put("P",P);

        return returnElements;
    }

    /**
     * Retire les symboles afin d'éviter une répétition de taille 1
     * @return
     */
    public static ArrayList<String> removeSymboleFromMinJumpUpToJump( ArrayList<String> symbolesVector, int currentPositionOfhead, String[] word, int jump){

        ArrayList listCopy = new ArrayList(symbolesVector);

        for(int i = -jump ; i <= jump ; i ++){
            // -- si i est à l'emplacement de la tete de lecture
            if(i == 0){
                continue;
            }

            // -- si la tete de lecture est avant le premier symbole du mot
            else if(currentPositionOfhead + i < 0  || currentPositionOfhead + i > (word.length-1)){
                continue;
            }
            // -- si le symbole vérifié n'est pas encore défini ou vide
            else if(word[(currentPositionOfhead + i)] == null || word[(currentPositionOfhead + i)].equals("") || word[(currentPositionOfhead + i)].equals("0")) {


                continue;
            }

             // -- on retire le symbole qui existerait
            for(int j  = 0 ; j <  listCopy.size(); j++){
                if(listCopy.get(j) == word[(currentPositionOfhead + i)]){
                    listCopy.remove(j);
                }

            }

        }



        return listCopy;
    }

    public static int getFirstEmptyElementInString(String[] word, int lengthOfWord){
        for (int i = 0; i < word.length; i++) {
            if (word[i] == null || word[i].equals("") || word[i].equals("0")) {
                return i;
            }
        }



            return lengthOfWord-1;
    }


    public static Map<String, String> buildWordWithPrecedentWord(String[] word,int positionOfSymbolAdded, int jump){


        Map map = new HashMap();

        String newWord = "";

        int startIndex = positionOfSymbolAdded;
        int lastIndex = positionOfSymbolAdded;

        // -- find start by backward
        for(int i = positionOfSymbolAdded; i >= 0; i -= jump ){

            if(word[i] == null || word[i].equals("0")){
                startIndex =  i + jump ;
                break;
            }
            startIndex = i;
        }
        // -- Permet de savoir le nouvel index du symbole ajouté à la construction du mot.
        int newIndexOfSymbolAdded = 0 ;

        // -- find start by backward
        Map<Integer, Integer> indexConcordanceNewToOld = new HashMap<Integer, Integer>();
        int currentIndex = 0;
        for(int i = startIndex; i < word.length; i += jump ){
            if(word[i] == null || word[i].equals("0")){
                break;
            }

            newWord += word[i];
            indexConcordanceNewToOld.put(currentIndex, i);
            if(i == positionOfSymbolAdded){
                newIndexOfSymbolAdded = newWord.length() -1;

            }
            lastIndex = i;
            currentIndex ++ ;
        }

        map.put("newWord", newWord);
        map.put("newIndexOfSymbole",   newIndexOfSymbolAdded  + "");
        map.put("firstCaracterIndex",   startIndex );
        map.put("lastCaracterIndex",   lastIndex );
        map.put("indexConcordance",   indexConcordanceNewToOld );

        return map;
    }

    public static boolean haveEmptyPosition(String[] word, int length) {

        for (int i = 0; i < word.length; i++) {
            if (word[i] == null || word[i].equals("")) {
                return true;
            }
        }

        if (word.length < length) {
            return true;
        }

        return false;
    }


}
