/*
 * Tools for basic arabic text processing
 * Includes: tokenization, checking for valid arabic alphabets, diacritic marks,
 * stopwords, Saaltamoniha characters.
 * Supports: tokenization, diacritic removal, stopwords removal, generating
 * character ngrams
 *
 * @author: Muazzam Siddiqui
 * @modified: August 11, 2011
 * @modified: July 13, 2013
 * @modified July 16, 2013
 * @modified November 20, 2013
 * @modified November 28, 2013
 * @modified June 13, 2014
 */
package arabictextprocessing;

import java.io.FileReader;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import utils.FileUtils;
import utils.ShowString;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import khojastemmer.Stem;
import static arabictextprocessing.Constants.*;
import java.util.regex.Pattern;

public class ArabicTextProcessing {

    Vector staticFiles;
    public String[] extractWords(String target) {

        BreakIterator wordIterator =
                BreakIterator.getWordInstance(new Locale("ar", "SA"));
        ArrayList wordsList = new ArrayList();
        String[] words = null;

        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (Character.isLetter(word.charAt(0))) {
                wordsList.add(word);
            }
            start = end;
            end = wordIterator.next();
        }
        words = new String[wordsList.size()];
        wordsList.toArray(words);
        return (words);
    }

    public String[] tokenize(String target){

        BreakIterator wordIterator =
                BreakIterator.getWordInstance(new Locale("ar", "SA"));
        ArrayList wordsList = new ArrayList();
        Arrays.sort(ARABIC_ALPHABETS);
        String[] words = null;

        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if(isArabicWord(word)){
                wordsList.add(word);
            }
            start = end;
            end = wordIterator.next();
        }
        words = new String[wordsList.size()];
        wordsList.toArray(words);
        return (words);

    }

    public String[] tokenize(String target, boolean punctuation){

        BreakIterator wordIterator =
                BreakIterator.getWordInstance(new Locale("ar", "SA"));
        ArrayList wordsList = new ArrayList();
        Arrays.sort(ARABIC_ALPHABETS);
        String[] words = null;

        wordIterator.setText(target);
        int start = wordIterator.first();
        int end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (punctuation){
                wordsList.add(word);
            }else{
                if(isArabicWord(word)){
                    wordsList.add(word);
                }
            }
            start = end;
            end = wordIterator.next();
        }
        words = new String[wordsList.size()];
        wordsList.toArray(words);
        return (words);

    }

    public String[] segmentSentences(String target){

        BreakIterator sentenceIterator =
                BreakIterator.getSentenceInstance(new Locale("ar", "SA"));
        ArrayList sentencesList = new ArrayList();
        String[] sentences = null;

        sentenceIterator.setText(target);
        int start = sentenceIterator.first();
        int end = sentenceIterator.next();

        while (end != BreakIterator.DONE) {
            String sentence = target.substring(start, end);
                sentencesList.add(target.substring(start, end));
            
            start = end;
            end = sentenceIterator.next();
        }
        sentences = new String[sentencesList.size()];
        sentencesList.toArray(sentences);
        return (sentences);

    }
    
    public String[] segmentSentences2(String target){
        
        String[] tokens = tokenize(target, true);
        ArrayList sentencesList = new ArrayList();
        StringBuilder str = new StringBuilder();
        String[] sentences = null;
        
        for(int i=0; i<tokens.length; i++){
            
            str.append(tokens[i].trim() + " ");
            /*
            if(tokens[i].trim().matches("\.|\?")){
                sentencesList.add(str.toString());
                str.setLength(0);
            }
            */
        }

        sentences = new String[sentencesList.size()];
        sentencesList.toArray(sentences);
        return (sentences);
        
    }
    
    public int isArabicAlphabet(char ch) {
        return (Arrays.binarySearch(ARABIC_ALPHABETS, ch));
    }

    public boolean isArabicWord(String word){
        
        boolean flag = true;
        for(int i=0; i<word.length(); i++){
            if (isArabicAlphabet(word.charAt(i)) < 0) {
                flag = false;
            }
        }
        return (flag);
    }
    
    public int isDiacritic(char ch) {
        return (Arrays.binarySearch(DIACRITICS, ch));
    }

    public int isDiacritic(String str) {
        return (Arrays.binarySearch(DIACRITICS_HEX, str));
    }

    public int isStopWord(String word) {
        return (Arrays.binarySearch(STOPWORDS, word));
    }

    public int isQuranStopWord(String word) {
        return (Arrays.binarySearch(QURAN_STOPWORDS, word));
    }

    public int isUrduStopWord(String word) {
        return (Arrays.binarySearch(URDU_STOPWORDS, word));
    }

    public int isUrduStopWord(String word, String[] stopwords) {
        return (Arrays.binarySearch(stopwords, word));
    }

    public int isSaal(char ch) {
        return (Arrays.binarySearch(SAALTAMONIHA, ch));
    }

    public boolean containsSaal(String inputString){

        boolean flag = false;
        
        for(int i=0; i<inputString.length(); i++){
            char ch = inputString.charAt(i);
            if(isSaal(ch) > 0){
                flag = true;
            }
        }
        return (flag);

    }

    public String normalize(String inputString) {

        String outputString = "";

        for (int i = 0; i < inputString.length(); i++) {

            char ch = inputString.charAt(i);
            if (ch == 'أ' || ch == 'آ' || ch == 'إ')
                ch = 'ا';
            else if (ch == 'ؤ')
                ch = 'و';
            else if (ch == 'ى' || ch == 'ئ')
                ch = 'ي';

            outputString += ch;

        }

        return (outputString);

    }

    /*
    public String removeDiacritics(String inputString) {
        
        String outputString = "";

        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);
            if (isDiacritic(ch) < 0) {
                outputString += ch;
            }
        }

        return (outputString);

    }
    */

    public String removeDiacritics(String inputString) {
        
        String outputString = "";

        for (int i = 0; i < inputString.length(); i++) {
            String str = UnicodeFormatter.charToHex(inputString.charAt(i));
            if (isDiacritic(str) < 0) {
                outputString += (char)Integer.parseInt(str, 16);
            }
        }

        return (outputString);

    }

    public String removeEndDiacritics(String inputString) {

        String outputString = "";
        int i;

        for (i=inputString.length()-1; isDiacritic(inputString.charAt(i)) > 0; i--);
        for (int j=0; j<i+1; j++){
            outputString += inputString.charAt(j);
        }

        return (outputString);

    }

     public String[] normalize(String[] words) {

        String[] trimmed = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            trimmed[i] = normalize(words[i]);
        }

        return (trimmed);
    }

     public String[] toLowerCase(String[] words) {

        String[] trimmed = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            trimmed[i] = toLowerCase(words[i]);
        }

        //System.out.println("Number of terms after removing the diacritics = " + trimmed.length);
        return (trimmed);
    }

     public String toLowerCase(String word){
         return word.toLowerCase();
     }

     public String[] removeDiacritics(String[] words) {

        String[] trimmed = new String[words.length];
        Arrays.sort(DIACRITICS);

        for (int i = 0; i < words.length; i++) {
            trimmed[i] = removeDiacritics(words[i]);
        }

        //System.out.println("Number of terms after removing the diacritics = " + trimmed.length);
        return (trimmed);
    }

     public String[] removeEndDiacritics(String[] words) {

        String[] trimmed = new String[words.length];
        Arrays.sort(DIACRITICS);

        for (int i = 0; i < words.length; i++) {
            trimmed[i] = removeEndDiacritics(words[i]);
        }

        //System.out.println("Number of terms after removing the end diacritics = " + trimmed.length);
        return (trimmed);
    }

     public String removeStopWords(String text){

        StringBuilder str = new StringBuilder();
        String[] words = extractWords(text);
        for(int i=0; i<words.length; i++){
            if(isStopWord(words[i]) < 0){
                str.append(words[i]);
            }
        }
        
        return (str.toString());

    }

    public String[] removeStopWords(String[] words) {

        ArrayList noStopWordsList = new ArrayList();
        String[] noStopWords = null;
        Arrays.sort(STOPWORDS);

        for (int i = 0; i < words.length; i++) {
            if(isStopWord(removeDiacritics(words[i])) < 0){
                noStopWordsList.add(words[i]);
            }
        }

        noStopWords = new String[noStopWordsList.size()];
        noStopWordsList.toArray(noStopWords);

        return (noStopWords);
        
    }

    public String[] removeQuranStopWords(String[] words) {

        ArrayList noStopWordsList = new ArrayList();
        String[] noStopWords = null;
        Arrays.sort(QURAN_STOPWORDS);

        for (int i = 0; i < words.length; i++) {
            if(isQuranStopWord(removeDiacritics(words[i])) < 0){
                noStopWordsList.add(words[i]);
            }
        }

        noStopWords = new String[noStopWordsList.size()];
        noStopWordsList.toArray(noStopWords);

        return (noStopWords);
        
    }

    public String[] removeUrduStopWords(String[] words) {

        ArrayList noStopWordsList = new ArrayList();
        String[] noStopWords = null;
        Arrays.sort(URDU_STOPWORDS);

        for (int i = 0; i < words.length; i++) {
            if(isUrduStopWord(removeDiacritics(words[i])) < 0){
                noStopWordsList.add(words[i]);
            }
        }

        noStopWords = new String[noStopWordsList.size()];
        noStopWordsList.toArray(noStopWords);

        return (noStopWords);
        
    }

    public String[] removeUrduStopWords(String[] words, String[] stopwords) {

        ArrayList noStopWordsList = new ArrayList();
        String[] noStopWords = null;
        Arrays.sort(stopwords);

        for (int i = 0; i < words.length; i++) {
            if(isUrduStopWord(words[i].trim(), stopwords) < 0){
                noStopWordsList.add(words[i]);
            }
        }

        noStopWords = new String[noStopWordsList.size()];
        noStopWordsList.toArray(noStopWords);

        return (noStopWords);
        
    }
    
    public String[] removePunctuations(String[] words) {

        ArrayList noPuncList = new ArrayList();
        String[] noPunc = null;

        for (int i = 0; i < words.length; i++) {
            if(!Pattern.matches("\\p{Punct}|،|‘|�", words[i]) &&
                    !words[i].trim().isEmpty()){
                noPuncList.add(words[i]);
            }
        }

        noPunc = new String[noPuncList.size()];
        noPuncList.toArray(noPunc);

        return (noPunc);
        
    }

    public String[] removeSpaces(String[] words){
        
        ArrayList noSpaceList = new ArrayList();
        String[] noSpace = null;

        for (int i = 0; i < words.length; i++) {
            if(!words[i].trim().isEmpty()){
                noSpaceList.add(words[i]);
            }
        }

        noSpace = new String[noSpaceList.size()];
        noSpaceList.toArray(noSpace);

        return (noSpace);
        
    }
    
//     public String removeEnglishStopWords(String text){
//
//        StringBuilder str = new StringBuilder();
//        String[] words = extractWords(text);
//        for(int i=0; i<words.length; i++){
//            if(isStopWord(words[i]) < 0){
//                str.append(words[i]);
//            }
//        }
//
//        return (str.toString());
//
//    }
//
//    public String[] removeEnglishStopWords(String[] words) {
//
//        ArrayList noStopWordsList = new ArrayList();
//        String[] noStopWords = null;
//        Arrays.sort(ENGLISH_STOPWORDS);
//
//        for (int i = 0; i < words.length; i++) {
//            if(isStopWord(removeDiacritics(words[i])) < 0){
//                noStopWordsList.add(words[i]);
//            }
//        }
//
//        noStopWords = new String[noStopWordsList.size()];
//        noStopWordsList.toArray(noStopWords);
//
////        System.out.println("Number of terms after removing the stopwords = " + noStopWords.length);
//        return (noStopWords);
//
//    }

    public String[] stemmer(String[] original){

        String[] stemmed = new String[original.length];
        readInStaticFiles();
        Stem st = new Stem (staticFiles);
        for(int i=0; i<original.length; i++){
            stemmed[i] = st.stemWord(original[i]);
        }
        return (stemmed);
    }

    // Generate overlapping word ngrams with a given n
    public String[][] getWordNGrams(String text, int n){

        String[] terms = tokenize(text, true);
        //String[] terms = text.split(" ");
        System.out.println(terms.length);
        // If ngram length is greater than the no of words
        // assign no of words to ngram length
        if(n > terms.length){
            n = terms.length;
        }
        // No of ngrams will be
        // term length - ngram length + 1
        int k=0;
        String[][] ngrams = new String[terms.length-n+1][n];
        for(int i=0; i<terms.length-n+1; i++){
            k=i;
            for(int j=0; j<n; j++){
                ngrams[i][j] = terms[k++];
            }
        }

        return (ngrams);
    }

    // Generate overlapping word ngrams with a given n
    public String[] getWordNGrams(String[] terms, int n){

        // If ngram length is greater than the no of words
        // assign no of words to ngram length
        if(n > terms.length){
            n = terms.length;
        }
        // No of ngrams will be
        // term length - ngram length + 1
        int k=0;
        String[] ngrams = new String[terms.length-n+1];
        StringBuilder s = new StringBuilder();
        
        for(int i=0; i<terms.length-n+1; i++){
            k=i;
            for(int j=0; j<n; j++){
                s.append(terms[k++] + " ");
            }
            ngrams[i] = s.toString().trim();
            s.setLength(0);
        }

        return (ngrams);
    }

    // Generate overlapping character ngrams with a given n
    public String[] getNGrams(String term, int n){

        // If ngram length is greater than term length
        // assign term length to ngram length
        if(n > term.length()){
            n = term.length();
        }
        // No of ngrams will be
        // term length - ngram length + 1
        String[] ngrams = new String[term.length()-n+1];
        int k=0;
        // For each ngram
        for(int i=0; i<ngrams.length; i++){
            k=i;
            char[] ngram = new char[n];
            for(int j=0; j<n; j++){
                ngram[j] = term.charAt(k++);
            }
            ngrams[i] = new String(ngram).trim();
        }

        return (ngrams);
    }

    public String[] getNGrams(String[] words, int n) {

        ArrayList ngramsArray = new ArrayList();
        String[] ngramsAll = null;

        for (int i = 0; i < words.length; i++) {
            String[] ngrams = getNGrams(words[i], n);
            for(int j=0; j<ngrams.length; j++){
                ngramsArray.add(ngrams[j]);
            }
        }

        ngramsAll = new String[ngramsArray.size()];
        ngramsArray.toArray(ngramsAll);

        System.out.println("No of ngrams = " + ngramsAll.length);
        return (ngramsAll);
    }

    public void processStrings(File f) {

        FileUtils fu = new FileUtils();
        String str = fu.readFile(f, "UTF-8");
        String[] words = extractWords(str);
        String str2 = "لنتائ";
        String displayString = new String();

        String text = normalize(str2);
        displayString = text;
//        String[] ngrams = getNGrams(words, 2);
//        for(int i=0;i<ngrams.length;i++){
//            displayString += i + " " + ngrams[i] + " ";
//        }

        new ShowString(displayString, "");

    }

    public void preprocessTextFile(File in, File out){

        FileUtils fu = new FileUtils();
        StringBuilder s = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String str = fu.readFile(in, "UTF-8");
        String[] terms = tokenize(str);
        terms = normalize(terms);
        terms = removeDiacritics(terms);
        terms = removeStopWords(terms);
        for(int i=0; i<terms.length; i++){
            s.append(terms[i] + newLine);
        }
        fu.writeFile(out, s.toString(), "UTF-8");
        

    }

    public HashMap<String, Integer> array2Hash(String[] array){

        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        for(int i=0; i<array.length; i++){
            hash.put(array[i], new Integer(0));
        }
        return (hash);

    }

    public String[] hash2Array(HashMap<String, Integer> hash){

        String[] array = new String[hash.size()];
        Set<Map.Entry<String, Integer>> set = hash.entrySet();
        Iterator itr = set.iterator();

        for (int i = 0; i < hash.size(); i++) {
            Map.Entry<String, Integer> entry = (Entry<String, Integer>) itr.next();
            array[i] = entry.getKey();
        }

        return (array);
    }

    protected void readInStaticFiles ( )
    {
        // create the vector composed of vectors containing the static files
        staticFiles = new Vector ( );
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "definite_article.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "duplicate.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "first_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "first_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "last_alif.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "last_hamza.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "last_maksoura.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "last_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "mid_waw.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "mid_yah.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "prefixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "punctuation.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "quad_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "stopwords.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "suffixes.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "tri_patt.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "tri_roots.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "diacritics.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( PATH_TO_STEMMER_FILES + "strange.txt" ).toString ( ) ) )
        {
            // the vector was successfully created
            System.out.println( "read in files successfully" );
        }
    }

    //--------------------------------------------------------------------------

    // read in the contents of a file, put it into a vector, and add that vector
    // to the vector composed of vectors containing the static files
    protected boolean addVectorFromFile ( String fileName )
    {
        boolean returnValue;
        try
        {
            // the vector we are going to fill
            Vector vectorFromFile = new Vector ( );

            // create a buffered reader
            File file = new File ( fileName );
            FileInputStream fileInputStream = new FileInputStream ( file );
            InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream, "UTF-16" );

            //If the bufferedReader is not big enough for a file, I should change the size of it here
            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader, 20000 );

            // read in the text a line at a time
            String part;
            StringBuffer word = new StringBuffer ( );
            while ( ( part = bufferedReader.readLine ( ) ) != null )
            {
                // add spaces at the end of the line
                part = part + "  ";

                // for each line
                for ( int index = 0; index < part.length ( ) - 1; index ++ )
                {
                    // if the character is not a space, append it to a word
                    if ( ! ( Character.isWhitespace ( part.charAt ( index ) ) ) )
                    {
                        word.append ( part.charAt ( index ) );
                    }
                    // otherwise, if the word contains some characters, add it
                    // to the vector
                    else
                    {
                        if ( word.length ( ) != 0 )
                        {
                            vectorFromFile.addElement ( word.toString ( ) );
                            word.setLength ( 0 );
                        }
                    }
                }
            }

            // trim the vector
            vectorFromFile.trimToSize ( );

            // destroy the buffered reader
            bufferedReader.close ( );
   	        fileInputStream.close ( );

            // add the vector to the vector composed of vectors containing the
            // static files
            staticFiles.addElement ( vectorFromFile );
            returnValue = true;
        }
        catch ( Exception exception )
        {
            returnValue = false;
        }
        return returnValue;
    }
    
    public String[] POSTagger(File in) throws IOException, ClassNotFoundException{
        
        MaxentTagger tagger = new MaxentTagger(PATH_TO_TAGGER_MODELS + "arabic-fast.tagger");
        @SuppressWarnings("unchecked")
        List<List<HasWord>> sentences = tagger.tokenizeText(new BufferedReader(new FileReader(in)));
        String[] taggedSentences = new String[sentences.size()];
        int i=0;        
        for (List<HasWord> sentence : sentences) {
          ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
          taggedSentences[i++] = Sentence.listToString(tSentence, false);
        }
        
        return (taggedSentences);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ArabicTextProcessing ATP = new ArabicTextProcessing();
            FileUtils fu = new FileUtils();
            StringBuilder s = new StringBuilder();
            String newLine = System.getProperty("line.separator");

            String list[] = ATP.POSTagger(
                    new File("/Research/Hadith/NER/Data/bukhari_sentences.txt"));
            System.out.println("Number of sentences read = " + list.length + "\n");
            for(int i=0;i<list.length;i++)
                s.append(list[i]+newLine); 
                //System.out.println(i + " " + list[i] + "\n");
            fu.writeFile(new File("/Research/Hadith/NER/Data/bukhari_sentences_pos_tagged.txt"), 
                    s.toString(), "UTF-8");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        } catch (ClassNotFoundException ex) {
            System.out.println("IO Exception");
        }
    
    }
}
