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
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author dayanarios
 */
public class DocumentIndexer {
    
    public static void main(String[] args) {
        
        DocumentCorpus corpus = DirectoryCorpus.loadJsonTextDirectory(Paths.get("").toAbsolutePath(), ".json"); //prev. .txt
        InvertedIndex index = indexCorpus(corpus) ;
        
        
        // We aren't ready to use a full query parser; for now, we'll only support single-term queries.
//        String query = "whale"; // hard-coded search for "whale"
//       
//        for (Posting p : index.getPostings(query)) {
//                System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
//
//        }
        
         // TODO: fix this application so the user is asked for a term to search.
        boolean cont = true; 

        Scanner scan = new Scanner(System.in);
        String query; 

        while(cont)
        {
            System.out.println("\nEnter a term to search (single word only): ");
            query = scan.nextLine();
            query = query.toLowerCase(); 

            if (query.equals("quit")){
                cont = false;
                break;
            }

            if(index.getPostings(query).isEmpty())
            {
                System.out.println(query + " not found in vocabulary");
            }
            else
            {
                System.out.println("Documents that contain the query: " + query); 
               for (Posting p : index.getPostings(query)) {

                System.out.println("Document Title: " + corpus.getDocument(p.getDocumentId()).getTitle());
                } 
            }

        }
        
        //index.print(); //print out hashmap
//        Path path = Paths.get("/Users/dayanarios/NetBeansProjects/CECS529/NPSarticles/article001.json");
//               
//        JsonFileDocument doc = new JsonFileDocument(1, path); 
//
//        doc.getContent(); 
    }
    
    
    private static InvertedIndex indexCorpus(DocumentCorpus corpus) {
        
        
        BasicTokenProcessor processor = new BasicTokenProcessor();

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

                String word = processor.processToken(t);
                if (word.length() > 0) 
                {
             
                    index.addTerm(word, d.getId()); 
                    
                }

            }

        }
        
 
        return index; 
    }
    
}
