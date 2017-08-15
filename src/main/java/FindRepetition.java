import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bri on 10-07-17.
 */
public class FindRepetition {

    // -- limité à 12 sauts avec 60 caractères
    public static  String[] symboles = {"a", "b", "c" ,"d" ,"e", "f" ,"g" , "h" ,"i" , "j" , "k" , "l" ,"m", "n","o", "p","q" ,"r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","#","$","(",")","{","}", "!","|","é","ç","à",",","?",".",";",":","/","+","=","~","-","@"," ", "ù" ,"ô"};



    public static String findRepetitiveSequenceAtTheEnd(String word) {

        int lastCharPos = word.length() - 1;
        int nbrCharToTest = 0;

        double nbrIteration = Math.floor(word.length() / 2);

        while (nbrIteration > 0) {
            // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            String firstSubstring = word.substring(lastCharPos - ((nbrCharToTest * 2) + 1), lastCharPos - nbrCharToTest);

            String secondSubstring = word.substring(lastCharPos - nbrCharToTest);
            nbrIteration--;
            nbrCharToTest++;

            if (firstSubstring.equals(secondSubstring)) {
                //    System.out.println("Repetition found:"+firstSubstring);
                return firstSubstring;
            }

            //System.out.println("Current subsctring:"+sequence.substring(currentChar));
        }

        //     System.out.println("Nbr iteration :"+nbrIteration);


        return "";
    }


    /**
     * Trouve tous les mots quadratiques depuis le symbole ajouté avec les sauts de 1 à k
     * @param word
     * @param indexOfLastSymbolAdded
     * @param jump
     * @return
     */
    public static Map findRepetitiveSequenceWithJump(String[] word, int indexOfLastSymbolAdded, int jump) {



        Map quadraticWordsFound = new HashMap();
        quadraticWordsFound.put("biggestQuadraticWord","");
        quadraticWordsFound.put("positionOfSymboleAdded","");
        quadraticWordsFound.put("jump","");
        quadraticWordsFound.put("newWordStartOldIndex","");
        quadraticWordsFound.put("newWordEndOldIndex","");

        // -- boucle qui va tester les sauts de k à 1
        for(int i = jump; i > 0 ; i--){
            // -- Construction du mot à tester à partir du saut et on récupère le nouvel index du symbole ajouté
            Map<String,String> wordWithCurrentJump = Sequence.buildWordWithPrecedentWord(word, indexOfLastSymbolAdded, i);

            int newIndexOfSymbole = Integer.parseInt(wordWithCurrentJump.get("newIndexOfSymbole"));
            Map<String,String>  tmpBiggestQuadraticWordFound = FindRepetition.findBiggestQuadraticWordAtTheRightOfTheString(wordWithCurrentJump.get("newWord"), newIndexOfSymbole);

            if( quadraticWordsFound.get("biggestQuadraticWord").toString().length() < tmpBiggestQuadraticWordFound.get("biggestQuadraticWord").length()){
//                if( tmpBiggestQuadraticWordFound.get("biggestQuadraticWord").length()  == 1){
//                    System.out.println("Square found: " + tmpBiggestQuadraticWordFound.get("biggestQuadraticWord"));
//                    System.out.println("Word: " + String.join("",word) );
//                    System.out.println("Jump: " + i );
//                }

                quadraticWordsFound.put("biggestQuadraticWord",tmpBiggestQuadraticWordFound.get("biggestQuadraticWord"));
                quadraticWordsFound.put("positionOfSymboleAdded",tmpBiggestQuadraticWordFound.get("positionOfSymboleAdded"));
                quadraticWordsFound.put("jump", i + "");
                quadraticWordsFound.put("newWordStartOldIndex", wordWithCurrentJump.get("firstCaracterIndex"));
                quadraticWordsFound.put("newWordEndOldIndex", wordWithCurrentJump.get("lastCaracterIndex"));
                quadraticWordsFound.put("indexConcordance", wordWithCurrentJump.get("indexConcordance"));
                quadraticWordsFound.put("repetitionStartNewIndex", tmpBiggestQuadraticWordFound.get("repetitionStartNewIndex"));
                quadraticWordsFound.put("indexSymbolAddedInRepetition", tmpBiggestQuadraticWordFound.get("indexSymbolAddedInRepetition"));

                // -- calcul de la position du caractere ajouté dans la répétition
                /*map.put("newIndexOfSymbole",   newIndexOfSymbolAdded  + "");
        map.put("firstCaracterIndex",   startIndex );
        map.put("lastCaracterIndex",   lastIndex );
        map.put("indexConcordance",   indexConcordanceNewToOld ); Map<Integer, Integer>*/



            }
        }

        if(quadraticWordsFound.get("biggestQuadraticWord").toString().length() > 0){


            int lengthOfQuadraticWord = quadraticWordsFound.get("biggestQuadraticWord").toString().length();
            int startIndex = Integer.parseInt(quadraticWordsFound.get("repetitionStartNewIndex").toString());


            Map indexConcordance = (Map) quadraticWordsFound.get("indexConcordance");
            // -- suppression de la plus grande séqunce répétitive dans le mot ajouté
            for(int i = 0 ; i < lengthOfQuadraticWord ; i++){

                 int concordance = Integer.parseInt(indexConcordance.get(startIndex+i).toString());
                 word[concordance] = "0";
            }
        }

        // TODO: retourner toutes les valeurs pour les ajouter dans le bloc 

        quadraticWordsFound.put("word", word);

        return quadraticWordsFound;
    }

    /**
     * Charche un mot quadratique
     * @param string
     * @return
     */
    public static Map<String,String>  findBiggestQuadraticWordAtTheRightOfTheString(String string, int indexLastAddedSymbole) {
        char[] chars = string.toCharArray();

        Map<String,String> returnArray = new HashMap<String,String>();

        String biggestQuadraticWord = "";

        int sizeOfRepetition;
        // -- il part du dernier caractere
        int currentChar = chars.length-1;

        // -- Enregistre sir le  symble ajouté se trouve dans la premiere partie ou la deuxieme partie de la répétition.
        // -- Valuer est  -1 pour la prmeiere partie,  1 pour la deuxieme.
        String firstOrSecondBloc = "";
        int repetitionStartIndex = -1 ;

        // -- every character
        // -- Il part du dernier caractere pour remonter
        while (currentChar > 0) {

            int maxRepetition = (int) Math.ceil(currentChar / 2.0);

            sizeOfRepetition = maxRepetition;

            while (sizeOfRepetition > 0) {
                String originString = "";
                String previousToTest = "";
                // build string

                int i = sizeOfRepetition;

                while (i > 0) {
                    originString += chars[currentChar - (i-1)];
                    previousToTest += chars[currentChar - sizeOfRepetition - (i-1)];
                    i--;
                }

                // -- Test afin de savoir si c'est une répétition
                if (originString.equals(previousToTest)) {
                    // -- Si le nouveau mot est plus grand que l'ancien alors on le remplace
                    if(biggestQuadraticWord.length() < originString.length()){
                        biggestQuadraticWord = originString;
                        // -- si l'index se trouve dans la premiere partie ou la seconde partie
                        firstOrSecondBloc = indexLastAddedSymbole <= (currentChar - sizeOfRepetition) ? "-1" : "1";
                        if(firstOrSecondBloc.equals("-1")){
                            repetitionStartIndex = currentChar - sizeOfRepetition - (sizeOfRepetition-1);
                        }else{
                            repetitionStartIndex = currentChar - (sizeOfRepetition-1);
                        }
                    }

                }

                sizeOfRepetition --;
            }
            // -- on passe au caractere suivant
            currentChar--;
        }

        returnArray.put("biggestQuadraticWord", biggestQuadraticWord);
        // -- permet de savoir ou se situe le dernier symbole: dans  la premiere  partie ou la deuxieme
        returnArray.put("positionOfSymboleAdded", firstOrSecondBloc);
        returnArray.put("repetitionStartNewIndex", repetitionStartIndex + "");
        returnArray.put("indexSymbolAddedInRepetition", (indexLastAddedSymbole - repetitionStartIndex)+"");

        return returnArray;
    }
//      BACKUP
//    public static String haveRepetitiveSequenceAnyWhere(String string) {
//        int sizeOfRepetition;
//        int currentChar = 1;
//        char[] chars = string.toCharArray();
//        // -- every character
//        //   System.out.println("Chars length:"+chars.length);
//        while (currentChar <= chars.length) {
//            sizeOfRepetition = 1;
//            int maxRepetition = (int) Math.floor(currentChar / 2);
//            //   System.out.println("start : sizeOfRepetition <= maxRepetition: MAX REPETITION:"+maxRepetition);
//            while (sizeOfRepetition <= maxRepetition) {
//                String originString = "";
//                String previousToTest = "";
//                // build string
//
//                int i = sizeOfRepetition;
//                while (i > 0) {
//                    originString += chars[currentChar - i];
//                    previousToTest += chars[currentChar - sizeOfRepetition - i];
//                    i--;
//                }
//
//                //      System.out.println("Test sequence  is :"+originString+"/"+previousToTest);
//                if (originString.equals(previousToTest)) {
//                    return originString;
//                }
//
//                sizeOfRepetition++;
//            }
//
//            currentChar++;
//        }
//        return "";
//    }
}
