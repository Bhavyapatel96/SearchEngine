/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.InvertedIndex;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Positional_posting;
import cecs429.index.Posting;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author dayanarios
 */
public class DocumentIndexer {
    protected static DocumentCorpus corpus;
    //private static InvertedIndex index; 
    private static Positional_inverted_index index;
    protected static String query;
    protected static List<Positional_posting> postings; 
   
    
    /**
     * 
     * @param path supplied by user in GUI.SearchDirectoriesButtonActionPerformed
     * 
     */
    protected static void startIndexing(Path path) throws ClassNotFoundException, InstantiationException, IllegalAccessException
   {
       
       corpus = DirectoryCorpus.loadJsonTextDirectory(path.toAbsolutePath(), ".json");
        
       index = posindexCorpus(corpus);
       //index = indexCorpus(corpus); 
        
   }
    
   /**
    *Used to compare results of positional index with for Inverted Index.  
    */ 
    
   /*
   protected static void startSearchEngine() throws ClassNotFoundException, InstantiationException, IllegalAccessException 
   {
         // TODO: fix this application so the user is asked for a term to search.
        boolean cont = true; 

        Scanner scan = new Scanner(System.in);
         
        List<String> queries; 
        
        NewTokenProcessor processor = new NewTokenProcessor();  

        
        //System.out.println("User is inputting a query"); 
        queries = new ArrayList(processor.processToken(query)); 

        if (query.equals("quit")){
            cont = false;
            System.exit(0);

        }

        for(String q : queries)
        {
            if(index.getPostings(q).isEmpty())
            {
                //clears list and repopulates it 
                String notFound = "Your search " + query + " is not found in any documents";
                GUI.JListModel.clear();
                GUI.JListModel.addElement(notFound); 
                
            }
            else
            {
               //clears list and repopulates it 
               String docInfo;
               GUI.JListModel.clear();
               
               
               for (Posting p : index.getPostings(q)) 
               { 
                   docInfo = "Document Title: " + corpus.getDocument(p.getDocumentId()).getTitle();
                   GUI.JListModel.addElement(docInfo); 
                
                } 
            }
        }
        
        GUI.SearchBarTextField.setText("Enter a new search or 'quit' to exit");
        GUI.SearchBarTextField.selectAll();
                
            
            
        
        
   }
   */
   
   protected static void startSearchEngine() throws ClassNotFoundException, InstantiationException, IllegalAccessException 
   {
       
         // TODO: fix this application so the user is asked for a term to search.
        boolean cont = true; 

        Scanner scan = new Scanner(System.in);
        
        List<String> queries; 
        
        NewTokenProcessor processor = new NewTokenProcessor();  

        
            //System.out.println("User is inputting a query"); 
            queries = new ArrayList(processor.processToken(query)); 

            if (query.equals("quit")){
                cont = false;
                System.exit(0);
            }

            for(String q : queries)
            {
                if(index.getPositional_posting(query).isEmpty()) //might be giving the error
                {
                    //clears list and repopulates it 
                    String notFound = "Your search " + query + " is not found in any documents";
                    GUI.JListModel.clear();
                    GUI.JListModel.addElement(notFound); 
                }
                else
                {
                    //clears list and repopulates it 
                   String docInfo;
                   GUI.JListModel.clear();
                   postings = index.getPositional_posting(query); 
                   
                   for (Positional_posting p : postings)
                   {
                       docInfo = corpus.getDocument(p.getDocumentId()).getTitle(); //+ " positions: "+ p.getPositions();
                       GUI.JListModel.addElement(docInfo); 

                    } 
                }
            }

        
        GUI.SearchBarTextField.setText("Enter a new search or 'quit' to exit");
        GUI.SearchBarTextField.selectAll();
        
   }
   
   
    
    
    private static InvertedIndex indexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        
        NewTokenProcessor processor = new NewTokenProcessor(); 

        // First, build the vocabulary hash set.
        // TODO:
        // Get all the documents in the corpus by calling GetDocuments().
        Iterable<Document> docs = corpus.getDocuments(); //call registerFileDocumentFactory first?
        
        InvertedIndex index = new InvertedIndex(); 

        // Iterate through the documents, and:
        for(Document d : docs)
        {
            // Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
            Reader reader = d.getContent(); 
            EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream

            // Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
            //		and adding them to the HashSet vocabulary.
            Iterable<String> tokens = stream.getTokens();

            for(String t : tokens)
            {

                List<String> terms = new ArrayList(processor.processToken(t));   
                
                
                for(String term: terms)
                {
                    index.addTerm(term, d.getId()); 
                }
                
                

            }

        }
        
 
        
        return index; 
    }
    
    
    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
       
        NewTokenProcessor processor = new NewTokenProcessor(); 
        
        Iterable<Document> docs = corpus.getDocuments(); 
        
        Positional_inverted_index index = new Positional_inverted_index(); 

        // Iterate through the documents, and:
        for(Document d : docs)
        {
            // Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
            Reader reader = d.getContent(); 
            EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream

            // Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
            //		and adding them to the HashSet vocabulary.
            Iterable<String> tokens = stream.getTokens();
            int i=0;
            for(String token : tokens)
            {

                List<String> word = new ArrayList(processor.processToken(token)); 
                
                if (word.size() > 0) {

                    index.addTerm(word.get(0), i, d.getId());

                }
                
                
                i=i+1;
    
            }

        }
        
 
        return index; 
    }
    
    /**
     * Use this to run the program without the GUI.
     * Comment out all of main in GUI.java 
     * fix imports
     * Use main below
     * 
     */
    
    /*
     public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        DocumentCorpus corpus = DirectoryCorpus.loadJsonTextDirectory(Paths.get("").toAbsolutePath(), ".json"); //to run json files
        //DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");// To run .txt files

        Positional_inverted_index index = posindexCorpus(corpus);

        boolean cont = true;

        Scanner scan = new Scanner(System.in);
        String query;

        while (cont) {
            System.out.println("\nEnter a term to search (single word only): ");
            query = scan.nextLine();
            query = query.toLowerCase();

            if (query.equals("quit")) {
                cont = false;
                break;
            }

            if (index.getPositional_posting(query).isEmpty()) {
                System.out.println(query + " not found in vocabulary");
            } else {
                System.out.println("Documents that contain the query: " + query);

                for (Positional_posting p4 : index.getPositional_posting(query)) {

                    System.out.println("Document Number: " + p4.getDocumentId() + " Positions: " + p4.getPositions());
                }
            }

        }

    }
    */
    
}
