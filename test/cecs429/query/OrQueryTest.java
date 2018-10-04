/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.query;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.index.Index;
import cecs429.index.Positional_inverted_index;
import cecs429.index.Posting;
import cecs429.text.EnglishTokenStream;
import cecs429.text.NewTokenProcessor;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bhavy
 */
public class OrQueryTest {

    public OrQueryTest() {
    }

    @Test
    public void testOr() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String query = "beach long + buggati";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(query);
        List<Posting> postings = qComponent.getPostings(index);

        String expResult = "023";
        String results = "";
        for (Posting p : postings) {
            results = results + p.getDocumentId();

        }
        assertEquals(expResult.trim(), results.trim());
    }

    @Test
    public void testOr2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String query = "rainfall + compared + States of + students";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(query);
        List<Posting> postings = qComponent.getPostings(index);

        String expResult = "0123";
        String results = "";
        for (Posting p : postings) {
            results = results + p.getDocumentId();

        }
        assertEquals(expResult.trim(), results.trim());
    }

    @Test
    public void testOr3() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String query = "mango + -country this";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(query);
        List<Posting> postings = qComponent.getPostings(index);

        String expResult = "34";
        String results = "";
        for (Posting p : postings) {
            results = results + p.getDocumentId();

        }
        assertEquals(expResult.trim(), results.trim());
    }

    @Test
    public void testOr4() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("merge");
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String query = "boy + vegas";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(query);
        List<Posting> postings = qComponent.getPostings(index);

        String expResult = "01234";
        String results = "";
        for (Posting p : postings) {
            results = results + p.getDocumentId();

        }
        assertEquals(expResult.trim(), results.trim());
    }

    @Test
    public void testOr5() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String query = "vegas + this -boy";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(query);
        List<Posting> postings = qComponent.getPostings(index);

        String expResult = "";
        String results = "";
        for (Posting p : postings) {
            results = results + p.getDocumentId();

        }
        assertEquals(expResult.trim(), results.trim());
    }

    private Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        NewTokenProcessor processor = new NewTokenProcessor();
        Iterable<Document> docs = corpus.getDocuments();

        Positional_inverted_index index = new Positional_inverted_index();
        for (Document d : docs) {
            Reader reader = d.getContent();
            EnglishTokenStream stream = new EnglishTokenStream(reader);
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
