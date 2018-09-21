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
import cecs429.index.Posting;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author dayanarios
 */
public class DocumentIndexer {
    
   public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
       
        DocumentCorpus corpus = DirectoryCorpus.loadJsonTextDirectory(Paths.get("").toAbsolutePath(), ".json");
        //DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt"); //prev. .txt
        InvertedIndex index = indexCorpus(corpus) ;
      
        
         // TODO: fix this application so the user is asked for a term to search.
        boolean cont = true; 

        Scanner scan = new Scanner(System.in);
        String query; 
        List<String> queries; 
        
        NewTokenProcessor processor = new NewTokenProcessor();  

        while(cont)
        {
            System.out.println("\nEnter a term to search (single word only): ");
            query = scan.nextLine();
            queries = new ArrayList(processor.processToken(query)); 

            if (query.equals("quit")){
                cont = false;
                break;
            }

            for(String q : queries)
            {
                if(index.getPostings(q).isEmpty())
                {
                    System.out.println(q + " not found in vocabulary");
                }
                else
                {
                    System.out.println("Documents that contain the query: " + query); 
                   for (Posting p : index.getPostings(q)) {

                    System.out.println("Document Title: " + corpus.getDocument(p.getDocumentId()).getTitle());
                    } 
                }
            }

        }
        
        //index.print(); //print out hashmap
//        Path path = Paths.get("/Users/dayanarios/NetBeansProjects/CECS529/NPSarticles/article001.json");
//               
//        JsonFileDocument doc = new JsonFileDocument(1, path); 
//
//        doc.getContent(); 

     
     //NewTokenProcessor processor = new NewTokenProcessor(); 
     //processor.processToken(""); 
     //System.out.println(processor.PorterStemmer("consolidating")); 
    }
    
    
    private static InvertedIndex indexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        
        //BasicTokenProcessor processor = new BasicTokenProcessor();
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
                //Collections.copy(terms,processor.processToken(t));  //copies the result of normalization into a new list
                
                for(String term: terms)
                {
                    index.addTerm(term, d.getId()); 
                }
                
                

            }

        }
        
 
        return index; 
    }
    
}
