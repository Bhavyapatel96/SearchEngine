/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Posting;
import cecs429.query.AndQuery;
import cecs429.query.BooleanQueryParser;
import cecs429.query.OrQuery;
import cecs429.text.EnglishTokenStream;
import cecs429.query.QueryComponent;
import cecs429.query.TermLiteral;
import cecs429.text.NewTokenProcessor;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Used to run the search engine through the output window, no GUI Follows the
 * documentation in DocumentIndexder.java.
 *
 * @author bhavy
 */
public class DocumentIndexer_noGUI {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //DocumentCorpus corpus = DirectoryCorpus.loadJsonTextDirectory(Paths.get("").toAbsolutePath(), ".json"); //to run json files
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files
        Positional_inverted_index index = posindexCorpus(corpus);
        index.print();
        List<Posting> result = new ArrayList<>();
        List<QueryComponent> l = new ArrayList<>();

        boolean cont = true;

        Scanner scan = new Scanner(System.in);
        String query;

        while (cont) {
            System.out.println("\nEnter your query: ");
            query = scan.nextLine();
            query = query.toLowerCase();

            if (query.equals("quit")) {
                cont = false;
                break;
            }
            BooleanQueryParser b = new BooleanQueryParser();
            QueryComponent c = b.parseQuery(query);
            result = c.getPostings(index);
            System.out.println(result);
            if (result.isEmpty()) {
                System.out.println("No results");
            } else {
                for (Posting p : result) {
                    //   System.out.println(result);
                    System.out.println("Doctument: " + p.getDocumentId() + "\t Positions:  " + p.getPositions());;
                }
            }

        }

    }

    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        NewTokenProcessor processor = new NewTokenProcessor();
        Iterable<Document> docs = corpus.getDocuments(); //call registerFileDocumentFactory first?

        Positional_inverted_index index = new Positional_inverted_index();

        // Iterate through the documents, and:
        for (Document d : docs) {
            // Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
            Reader reader = d.getContent();
            EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream

            // Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
            //		and adding them to the HashSet vocabulary.
            Iterable<String> tokens = stream.getTokens();
            int i = 0;

            for (String token : tokens) {

                List<String> word = new ArrayList<String>();
                word = processor.processToken(token);

                if (word.size() > 0) {

                    index.addTerm(word.get(0), i, d.getId());

                }
                i = i + 1;
            }

        }

        return index;
    }

}
