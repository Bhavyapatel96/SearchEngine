package edu.csulb;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Index;
import cecs429.index.Posting;
import cecs429.index.TermDocumentIndex;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class BetterTermDocumentIndexer {
//	public static void main(String[] args) {
//		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
//		Index index = indexCorpus(corpus) ;
//		// We aren't ready to use a full query parser; for now, we'll only support single-term queries.
//		String query = "whale"; // hard-coded search for "whale"
//		for (Posting p : index.getPostings(query)) {
//			System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
//                        
//		}
//                
//                
//                // TODO: fix this application so the user is asked for a term to search.
//                boolean cont = true; 
//                
//                Scanner scan = new Scanner(System.in);
//                 
//                
//                while(cont)
//                {
//                    System.out.println("\nEnter a term to search (single word only): ");
//                    query = scan.nextLine();
//                    query = query.toLowerCase(); 
//                    
//                    if (query.equals("quit")){
//                        cont = false;
//                        break;
//                    }
//                    
//                    if(index.getPostings(query).isEmpty())
//                    {
//                        System.out.println(query + " not found in vocabulary");
//                    }
//                    else
//                    {
//                        System.out.println("Documents that contain the query: " + query); 
//                       for (Posting p : index.getPostings(query)) {
//                        
//			System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
//                        } 
//                    }
//                       
//                }
//	}
	
	private static Index indexCorpus(DocumentCorpus corpus) {
		HashSet<String> vocabulary = new HashSet<>();
		BasicTokenProcessor processor = new BasicTokenProcessor();
		
		// First, build the vocabulary hash set.
		// TODO:
		// Get all the documents in the corpus by calling GetDocuments().
                Iterable<Document> docs = corpus.getDocuments(); //call registerFileDocumentFactory first?
                
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
                            vocabulary.add(word);
                        }
                        
                    }
                    
                }
		
		// TODO:
		// Constuct a TermDocumentMatrix once you know the size of the vocabulary.
                //boolean[][] TermDocumentMatrix = new boolean[vocabulary.size()][corpus.getCorpusSize()]; 
                TermDocumentIndex index = new TermDocumentIndex(vocabulary, corpus.getCorpusSize());
                
		// THEN, do the loop again!
                // Iterate through the documents, and:
                for(Document d : docs)
                {
                    // Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
                    Reader reader = d.getContent(); 
                    EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream
                    
                    // Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
                    //		But instead of inserting into the HashSet, add terms to the index with addPosting.
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
