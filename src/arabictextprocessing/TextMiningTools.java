/*
 * Tools for advanced arabic text operations
 * Uses basic arabic text processing
 * Supports:
 *
 * File read:
 * reading single files, file stored in a folder and
 * recursively reading files from subfolders for classification
 *
 * Doc Term Matrix (DTM) generation
 *
 * Unary variable removal from the DTM
 *
 * @author: Muazzam Siddiqui
 * @modified: July 11, 2010
 * @modified: Nov 13, 2013 // added document stats collection for sentences etc.
 */

package arabictextprocessing;

import utils.FileUtils;
import java.util.ArrayList;
import TF.tf;
import TF.IDSorter;
import java.io.File;
import java.util.HashMap;

public class TextMiningTools {

    ArabicTextProcessing ATP = new ArabicTextProcessing();

    // Generate and return a doc term matrix
    // Matrix only contains the terms that are given in the vocabulary list provided
    public Instance[] getDocumentTermMatrix(Instance[] instances, String[] vocabulary, String type){

        tf termFrequency = new tf();

        Integer freq = new Integer(0);

        System.out.println("Reading files ... ");
        // Generate the DTM
        // For each document
        for (int i = 0; i < instances.length; i++) {
            // Get the terms
            String[] termsFile = addFile(new File(instances[i].getFilename()));
            termsFile = ATP.normalize(termsFile);
            termsFile = ATP.removeDiacritics(termsFile);
            // Get the TF for each term in the document
            HashMap<String, Integer> TF = termFrequency.getTermFrequency(termsFile);
            // For each term in the vocabulary
            // Check if the vocabulary term is found in the document
            for(int j=0; j<vocabulary.length; j++){
                // If the term is found in the document
                // Assign its TF value to the appropriate DTM matrix entry
                if((freq = TF.get(vocabulary[j]))!= null){
                    if(type.equalsIgnoreCase("BOOL"))
                        instances[i].setData(j, 1);
                    else if(type.equalsIgnoreCase("TF"))
                        instances[i].setData(j, freq);
                }
            }
        }

        return (instances);

    }

    public String[] getDataForNERTagging(Instance[] instances){
        
        ArrayList data = new ArrayList();
        String[] oneTokenPerLine;
        int tokenNo = 0;

        // For each file
        for (int i = 0; i < instances.length; i++) {
            
            // Get the tokens
            String[] termsFile = addFile(new File(instances[i].getFilename()));
            // Preprocess the tokens
            termsFile = ATP.normalize(termsFile);
            termsFile = ATP.removeDiacritics(termsFile);

            // Add the tokens from this file to the list
            for(int j = 0; j < termsFile.length; j++){
                // Skip the spaces and save the filename and token no with each token
                if(!termsFile[j].trim().isEmpty())
                    data.add(instances[i].getFilename() + " " + tokenNo++ + " " + termsFile[j]);
            }
            
        }

        oneTokenPerLine = new String[data.size()];
        data.toArray(oneTokenPerLine);

        return (oneTokenPerLine);
        
    }
    
    public Instance[] readDir(File in){

        ArrayList instanceList = new ArrayList();

        // If the subfolder structure is considered
        // This is used for classification
        // Documents (files) for each class should be placed
        // under subfolders bearing the class name
        if(in.getPath().endsWith("*")){
            // Replace the last '*'
            in = new File(in.getPath().substring(0, in.getPath().lastIndexOf('*')));
            System.out.println("Root " + in.getName());
            // Get the subfolder names
            File[] dirs = in.listFiles();
            // For each subfolder
            for(int i=0; i<dirs.length; i++){
                System.out.println("Class " + dirs[i].getName());
                // Create the full path string for the subfolder
                File subDir = new File(dirs[i]+"/");
                // Get the files from this subfolder
                File[] subFiles = subDir.listFiles();
                // For each file in the subfolder
                for(int j=0; j<subFiles.length; j++){
//                    System.out.println("Document " + subFiles[j].toString());
                    Instance inst = new Instance();
                    inst.setFilename(subFiles[j].toString());
                    inst.setTarget(subDir.getName());
                    instanceList.add(inst);
                }
            }

        }

        // If no substructure is considered
        // This is used for clustering or just reading the documents
        // All the files under the given folder are considered to be
        // individual documents
        else{

            File[] files = in.listFiles();
            // Get the list of terms from each file
            for (int i = 0; i < files.length; i++) {
                Instance inst = new Instance();
                inst.setFilename(files[i].toString());
                instanceList.add(inst);
            }

        }

        Instance[] instances = new Instance[instanceList.size()];
        instanceList.toArray(instances);

        System.out.println("Total number of instances = " + instances.length);
        return (instances);

    }

    // Return the terms from the entire corpus
    // Iteratively tokenizes each document in the corpus and adds the tokens
    // (terms) to a list
    public String[] getAllTerms(Instance[] instances){

        ArrayList termsAll = new ArrayList();

        System.out.println("Reading files ... ");

        // Get the list of terms from each file
        for (int i = 0; i < instances.length; i++) {
            String[] termsFile = addFile(new File(instances[i].getFilename()));
            // Add the terms from each file to a global list
            for(int j=0; j<termsFile.length; j++){
                termsAll.add(termsFile[j]);
            }
        }

        String[] termsOriginal = new String[termsAll.size()];
        termsAll.toArray(termsOriginal);

//        System.out.println("Total number of terms = " + termsOriginal.length);
        return (termsOriginal);

    }

    // Return the ngrans from the entire corpus
    // Iteratively tokenizes each document in the corpus and adds the tokens
    // (terms) to a list
    // Same as getAllTerms but includes ngrams
    public String[] getNGrams(Instance[] instances, int n){

        ArrayList termsAll = new ArrayList();

        System.out.println("Reading files ... ");

        // Get the ngrams from each file
        for (int i = 0; i < instances.length; i++) {
            String[] ngrams = ATP.getWordNGrams(
                    addFile(new File(instances[i].getFilename())), n);
            // Add the ngrams from each file to a global list
            for(int j=0; j<ngrams.length; j++){
                termsAll.add(ngrams[j]);
            }
        }

        String[] termsOriginal = new String[termsAll.size()];
        termsAll.toArray(termsOriginal);

//        System.out.println("Total number of terms = " + termsOriginal.length);
        return (termsOriginal);

    }

    // Return distinct terms with their frequencies
    public String[] getVocabulary(String[] termsAll){

        tf termFrequency = new tf();
        HashMap<String, Integer> TF = termFrequency.getTermFrequency(termsAll);
        return (ATP.hash2Array(TF));

    }

    // Writes the document term matrix to a file
    public void writeDTM(File out, Instance[] instances, String[] vocabulary, boolean header) {

        FileUtils fu = new FileUtils();
        String newLine = System.getProperty("line.separator");
        String sep = "\t";
        StringBuilder str = new StringBuilder();

        // Header
        str.append("filename");
        if (header) {
            for (int j = 0; j < vocabulary.length; j++) {
                str.append(sep + (vocabulary[j]).trim());
            }
            str.append(sep + "target");
            str.append(newLine);
        }
        for (int i = 0; i < instances.length; i++) {
            String filename = instances[i].getFilename().
                    substring(instances[i].getFilename().
                    lastIndexOf('/')+1);
            str.append(filename);
            for (int j = 0; j < vocabulary.length; j++) {
                str.append(sep + instances[i].getData(j));
                
            }
            str.append(sep + instances[i].getTarget());
            str.append(newLine);
        }

        fu.writeFile(out, str.toString(),"UTF-8");

    }

    public void writeSortedTerms(File out, IDSorter[] sortedTerms, String[] terms) {

        FileUtils fu = new FileUtils();
        StringBuilder strToWrite = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        Double freq = new Double(0);

        for (int i = 0; i < sortedTerms.length; i++) {
            freq = sortedTerms[i].getWeight();
            strToWrite.append(terms[sortedTerms[i].getID()] + "\t" +
                    freq.intValue() + newLine);
        }

        fu.writeFile(out, strToWrite.toString(), "UTF-8");

    }

    public void writeSortedTerms(File out, HashMap<String, Integer> TF){

        tf termFrequency = new tf();

        int[] freq = termFrequency.getFrequenciesFromHash(TF);
        String[] terms = termFrequency.getTermsFromHash(TF);
        IDSorter[] sortedTF = termFrequency.sortTerms(freq);
        writeSortedTerms(out, sortedTF, terms);

    }

    public void writeDocumentSummaryStats(File out, Instance[] instances, String[] vocabulary) {

        FileUtils fu = new FileUtils();
        String newLine = System.getProperty("line.separator");
        String sep = ",";
        StringBuilder str = new StringBuilder();
        int totalTerms = 0;

        for (int i = 0; i < instances.length; i++) {
            double[] data = instances[i].getData();
            String filename = instances[i].getFilename().
                    substring(instances[i].getFilename().
                    lastIndexOf('\\') + 1);

            int totalDocTerms = 0;
            int distinctDocTerms = 0;
            for (int j = 0; j < data.length; j++) {
                totalDocTerms += data[j];
                if (data[j] > 0) {
                    distinctDocTerms++;
                }
            }
            totalTerms += totalDocTerms;
            str.append(filename + sep + totalDocTerms + sep + distinctDocTerms + newLine);
            System.out.println(filename + " " + totalDocTerms + " " + distinctDocTerms);
        }

        System.out.println(totalTerms + " " + vocabulary.length);
        fu.writeFile(out, str.toString(),"UTF-8");
    }

    public HashMap<String, Integer> removeUnary(HashMap<String, Integer> TF, String[] vocabulary){

        //Integer freq = new Integer(0);
        HashMap<String, Integer> nonUnaryTF = new HashMap<String, Integer>();

        for(int i=0; i<vocabulary.length; i++){
            //if((freq = TF.get(vocabulary[i])) > 1){
            //    nonUnaryTF.put(vocabulary[i], freq);
            //}
            nonUnaryTF.put(vocabulary[i], TF.get(vocabulary[i]));
        }

//        System.out.println("No of terms after unary removal = " + nonUnaryTF.size());
        return(nonUnaryTF);
        
    }

    public Integer[] getNonUnaryIndices(Instance[] instances){

        int numCols = instances[0].getData().length;
        ArrayList colList = new ArrayList();
        double colSum = 0;
        for (int j=0; j<numCols; j++) {
            for(int i=0; i<instances.length; i++){
                colSum += instances[i].getData(j);
            }
            if(colSum > 1){
                colList.add(j);
            }
            colSum = 0;
        }
        Integer[] indices = new Integer[colList.size()];
        colList.toArray(indices);

        return (indices);

    }

    public Instance[] removeUnaryVariables(Instance[] instances, Integer[] indices){

        double[] data = new double[indices.length];

        for(int i=0; i<instances.length; i++){
            for(int j=0; j<data.length; j++){
                data[j] = instances[i].getData(indices[j]);
            }
            instances[i].setData(data);
        }

        return (instances);

    }

    public String[] removeUnaryVocabulary(String[] vocabulary, Integer[] indices){

        String[] terms = new String[indices.length];

        for(int i=0; i<terms.length; i++){
            terms[i] = vocabulary[indices[i]];
        }

//        System.out.println("Number of distinct terms after unary removal = " +
//                indices.length);
        return (terms);

    }
    
    public int getTotalNonUnaryTerms(String[] termsAll, String[] vocabulary){
        
        tf termFrequency = new tf();
        HashMap<String, Integer> TF = termFrequency.getTermFrequency(termsAll);
        HashMap<String, Integer> nonUnaryTF = removeUnary(TF, vocabulary);
        int sum = 0;
        for(Integer val : nonUnaryTF.values())
            sum += val;

        return sum;
 
    }

    public String[] addFile(File in){

        FileUtils fu = new FileUtils();
        
        String str = fu.readFile(in, "UTF-8");
        String[] termsOriginal = ATP.tokenize(str, true);
        return(termsOriginal);

    }

    public void run2(File in, File out){

        FileUtils fu = new FileUtils();
        Instance[] instances = readDir(in);
        String[] tokens = getDataForNERTagging(instances);
        fu.writeList(out, tokens, "UTF-8");

    }
    
    public String[] getAllSentences(Instance[] instances){
        
        ArrayList sentencesAll = new ArrayList();
        int numTokens = 0;

        System.out.println("Reading files ... ");

        // Get the list of sentences from each file
        for (int i = 0; i < instances.length; i++) {
            String[] sentencesFile = addFileForSentences(new File(instances[i].getFilename()));
            for(int j=0; j<sentencesFile.length; j++){
                String[] tokens = ATP.tokenize(sentencesFile[j], true);
                for(int k=0; k<tokens.length; k++){
                    if(!tokens[k].trim().isEmpty())
                        numTokens++;
                }
                //System.out.println("No of actual tokens in sentence " + j + " = " + numTokens);
            }

            System.out.println(instances[i].getFilename() + "," + sentencesFile.length + 
                    "," + ((double)numTokens)/sentencesFile.length);
            numTokens = 0;
            
            // Add the sentences from each file to a global list
            for(int j=0; j<sentencesFile.length; j++){
                sentencesAll.add(sentencesFile[j]);
            }
        }

        String[] sentencesOriginal = new String[sentencesAll.size()];
        sentencesAll.toArray(sentencesOriginal);

        return (sentencesOriginal);

    }
    
    public String[] addFileForSentences(File in){

        FileUtils fu = new FileUtils();
        
        String[] sentencesOriginal = ATP.segmentSentences(fu.readFile(in, "UTF-8"));
        return(sentencesOriginal);

    }
    
    public void writeSentences(File out, String[] sentences){
        FileUtils fu = new FileUtils();
        String newLine = System.getProperty("line.separator");
        for(int i=0; i<sentences.length; i++){
            String[] tokens = ATP.tokenize(sentences[i], true);
            int noTokens = 0;
            for(int j=0; j<tokens.length; j++){
                 for(int k=0; k<tokens.length; k++){
                    if(!tokens[k].trim().isEmpty())
                        noTokens++;
                }
                String str = i + "\t" + j + "\t" + tokens[j] + newLine;
                fu.writeExistingFile(out, str, "UTF-8");
            }
            //String str = i + "=====" + sentences[i].trim() + newLine;
            //fu.writeExistingFile(out, str, "UTF-8");
        }
    }
    
    // to get sentence stats
    public void run3(File in, File out){

        FileUtils fu = new FileUtils();
        Instance[] instances = readDir(in);
        String[] sentences = getAllSentences(instances);
        System.out.println("Total no of sentences in the corpus = " + sentences.length);
        System.out.println("Average no of sentences in each file = " + ((double)sentences.length)/instances.length);

        //writeSentences(out, sentences);

    }
    
    //-------------------------------------------------------------------
    // This part is only to check the functions provided
    // For any proper implementation and the inclusion of this file into a 
    // bigger project, the functions run and main will be deleted
    public void run(File in, File out) {

        tf termFrequency = new tf();
        FileUtils fu = new FileUtils();

        Instance[] instances = readDir(in);

//        String[] termsAll = getNGrams(instances, 1);
        String[] termsAll = getAllTerms(instances);
        System.out.println("Total no of terms = " + termsAll.length);
        String[] vocabulary = getVocabulary(termsAll);
        System.out.println("Total no of distinct terms = " + vocabulary.length);
        termsAll = ATP.removeDiacritics(termsAll);
        vocabulary = getVocabulary(termsAll);
        System.out.println("No of distinct terms after dediacritization = " + vocabulary.length);
        termsAll = ATP.normalize(termsAll);
        vocabulary = getVocabulary(termsAll);
        System.out.println("No of distinct terms after normalization = " + vocabulary.length);
//        termsAll = ATP.toLowerCase(termsAll);
//        termsAll = ATP.getNGrams(termsAll, 2);
//        termsAll = ATP.stemmer(termsAll);
//        vocabulary = getVocabulary(termsAll);        
//        System.out.println("No of distinct terms after stemming = " + vocabulary.length);
//        termsAll = ATP.removeEnglishStopWords(termsAll);
//        termsAll = ATP.removeEndDiacritics(termsAll);
//        termsAll = ATP.removeDiacritics(termsAll);
//        termsAll = ATP.removeQuranStopWords(termsAll);
//        String[] stopwords = fu.readList(new File("/Research/Urdu NLP/Data/urdu_stop_words.txt"), "UTF-8");
//        termsAll = ATP.removeUrduStopWords(termsAll, stopwords);
//        termsAll = ATP.removeStopWords(termsAll);
//        vocabulary = getVocabulary(termsAll);
//        System.out.println("Total no of terms after stopword removal = " + termsAll.length);
//        System.out.println("No of distinct terms after stopword removal = " + vocabulary.length);
//        termsAll = ATP.removePunctuations(termsAll);
//        vocabulary = getVocabulary(termsAll);
//        System.out.println("Total no of terms after stopword punctuations = " + termsAll.length);
//        System.out.println("No of distinct terms after punctuations removal = " + vocabulary.length);
 //       termsAll = ATP.getWordNGrams(termsAll, 3);
        
        HashMap<String, Integer> TF = termFrequency.getTermFrequency(termsAll);
        vocabulary = getVocabulary(termsAll);

        TF = removeUnary(TF, vocabulary);
        System.out.println("No of distinct terms after unary removal = " + TF.size());
       writeSortedTerms(out, TF);

        // Initialize the instances data first
        for(int i=0; i<instances.length; i++)
            instances[i].setData(vocabulary.length);
        instances = getDocumentTermMatrix(instances, vocabulary, "tf");
        Integer[] indices = getNonUnaryIndices(instances);
        //instances = removeUnaryVariables(instances, indices);
        vocabulary = removeUnaryVocabulary(vocabulary, indices);
        System.out.println("Total no of terms after unary removal = " +  getTotalNonUnaryTerms(termsAll, vocabulary));
        System.out.println("No of distinct terms after unary removal = " + vocabulary.length);

        writeDTM(out, instances, vocabulary, true);

//        writeDocumentSummaryStats(out, instances, vocabulary);

    }

    public static void main(String[] args) {
        TextMiningTools ATM = new TextMiningTools();
        ATM.run(new File("/Research/Wikipedia/english/temp/annotated/"),
                new File("/Research/Wikipedia/data/tf_temp_annotated.txt"));
    }

}
