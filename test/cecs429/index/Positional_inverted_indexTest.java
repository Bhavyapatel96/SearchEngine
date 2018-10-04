/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.index;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.query.BooleanQueryParser;
import cecs429.query.QueryComponent;
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
 * @author bhavya
 */
public class Positional_inverted_indexTest {

    public Positional_inverted_indexTest() {
    }

    @Test
    public void testGetPositional_posting() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "is";
        Positional_inverted_index index = posindexCorpus(corpus);

        String expResult = "0[1]1[4, 12]2[1, 9]3[5]4[10, 16]";
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    @Test
    public void testGetPositional_posting2() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "this";
        Positional_inverted_index index = posindexCorpus(corpus);

        String expResult = "1[11]3[0]4[0, 13]";
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    @Test
    public void testGetPositional_posting3() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "expensive";
        Positional_inverted_index index = posindexCorpus(corpus);
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        String expResult = "2[6]";

        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    @Test
    public void testGetPositional_posting4() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "little";
        Positional_inverted_index index = posindexCorpus(corpus);

        String expResult = "0[3, 6]1[9]2[11]";
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        // List<Posting> results = index.getPositional_posting(term);
        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    @Test
    public void testGetPositional_posting5() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "boy";
        Positional_inverted_index index = posindexCorpus(corpus);

        String expResult = "0[13]1[22]2[28]3[24]4[18]";
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    @Test
    public void testGetPositional_posting6() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("C:\\Users\\bhavy\\Desktop\\SET\\Testfiles").toAbsolutePath(), ".txt");// To run .txt files

        String term = "Vegas";
        Positional_inverted_index index = posindexCorpus(corpus);

        String expResult = "";
        BooleanQueryParser bParser = new BooleanQueryParser();
        QueryComponent qComponent = bParser.parseQuery(term);
        List<Posting> results = qComponent.getPostings(index);

        String result = "";
        for (Posting p : results) {
            result = result + p.getDocumentId() + p.getPositions();

        }

        assertEquals(expResult.trim(), result.trim());

    }

    private static Positional_inverted_index posindexCorpus(DocumentCorpus corpus) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        NewTokenProcessor processor = new NewTokenProcessor();
        Iterable<Document> docs = corpus.getDocuments();
        Positional_inverted_index index = new Positional_inverted_index();

        for (Document d : docs) {
            Reader reader = d.getContent();
            EnglishTokenStream stream = new EnglishTokenStream(reader); //can access tokens through this stream
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
