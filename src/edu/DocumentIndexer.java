/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Index;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Posting;
import cecs429.query.BooleanQueryParser;
import cecs429.query.QueryComponent;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import cecs429.text.TokenProcessor;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author dayanarios
 */
public class DocumentIndexer {
    protected static DocumentCorpus corpus;
    private static Index index;
    protected static String query;
    protected static List<Posting> postings; 
    protected static Boolean clickList = false; 
   
    
    /**
     * 
     * @param path supplied by user in GUI.SearchDirectoriesButtonActionPerformed
     * 
     */
    protected static void startIndexing(Path path) throws ClassNotFoundException, InstantiationException, IllegalAccessException
   {
       corpus = DirectoryCorpus.loadJsonTextDirectory(path.toAbsolutePath(), ".json");
       index = posindexCorpus(corpus);
        
   }
  
   protected static void startSearchEngine() throws ClassNotFoundException, InstantiationException, IllegalAccessException 
   {
         
        
        if(!specialQueries(query))
        {
            BooleanQueryParser bParser = new BooleanQueryParser();
            QueryComponent qComponent = bParser.parseQuery(query);
            postings = qComponent.getPostings(index);
            
            if(postings.isEmpty()) //might be giving the error
            {
                clickList = false; 
                //clears list and repopulates it 
                String notFound = "Your search " + query + " is not found in any documents";
                GUI.JListModel.clear();
                GUI.JListModel.addElement(notFound); 
            }
            else
            {
                clickList = true; 
                //clears list and repopulates it 
               String docInfo;
               //GUI.ResultsJList.setEnabled(true);
               GUI.JListModel.clear();
               
               for (Posting p : postings)
               {
                   docInfo = corpus.getDocument(p.getDocumentId()).getTitle(); 
                   GUI.JListModel.addElement(docInfo); 

                } 
            }

        }
        
        GUI.SearchBarTextField.setText("Enter a new search or 'q' to exit");
        GUI.SearchBarTextField.selectAll();
        

       
        
   }
   
   
    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
       
        TokenProcessor processor = new NewTokenProcessor(); 
        
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
                
                for(String w: word)
                {
                    index.addTerm(w, i, d.getId());
                    //System.out.println("word added to inded: " + w);
                }
                
                i=i+1;
    
            }

        }
        
 
        return index; 
    }
    
    private static Boolean specialQueries(String query) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        clickList = false; 
        
        if (query.equals("q")){

            System.exit(0);
            return true; 
        }
        
        String[] subqueries = query.split("\\s+"); //split around white space
        
        if(subqueries[0].equals("stem")) //first term in suqueries tells computer what to do 
        {
            TokenProcessor processor = new NewTokenProcessor(); 
            if(subqueries.length > 1)   //user meant to stem the token not to search stem
            {
                List<String> stems = processor.processToken(subqueries[1]);
                
                //clears list and repopulates it 
                String notFound = "Stem of query '" + subqueries[1] + "' is '" + stems.get(0) + "'";
                //GUI.ResultsJList.setEnabled(false);
                GUI.JListModel.clear();
                GUI.JListModel.addElement(notFound);
                
                return true; 
            }
            
            
        }
        else if(subqueries[0].equals("index"))
        {
            
        }
            
        
        
        
        
        
        return false; 
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
        List<Posting> result = new ArrayList<>();
        List<QueryComponent> l = new ArrayList<>();
        
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
             BooleanQueryParser b = new BooleanQueryParser();
             QueryComponent c = b.parseQuery(query);
             result=c.getPostings(index);
             if(result.isEmpty()){
                 System.out.println("No results");
             }
             else{
                 for (Posting p : result)
                 {
                 
                     System.out.println("results: " + p.getDocumentId());
                 }
             }
                
           
    }

   
//        BooleanQueryParser temp = new BooleanQueryParser(); 
//        temp.parseQuery("-hello");
//        temp.parseQuery("\"hello\"");
//
//        System.out.println("");



    
    }
*/
}

