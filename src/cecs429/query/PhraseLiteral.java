package cecs429.query;

import cecs429.index.Posting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cecs429.index.Index;
import cecs429.text.NewTokenProcessor;
import cecs429.text.TokenProcessor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a phrase literal consisting of one or more terms that must occur in sequence.
 */
public class PhraseLiteral implements QueryComponent {
	// The list of individual terms in the phrase.
	private List<String> mTerms = new ArrayList<>();
	
	/**
	 * Constructs a PhraseLiteral with the given individual phrase terms.
	 */
	public PhraseLiteral(List<String> terms) {
		mTerms.addAll(terms);
	}
	
	/**
	 * Constructs a PhraseLiteral given a string with one or more individual terms separated by spaces.
	 */
	public PhraseLiteral(String terms) {
		mTerms.addAll(Arrays.asList(terms.split(" ")));
                
                
	}
	
	@Override
	public List<Posting> getPostings(Index index) {
            TokenProcessor processor = new NewTokenProcessor(); 
            List<String> queries = new ArrayList(); 
            List<String> temp;
            
            try {
                for(String term: mTerms)
                {
                    temp = new ArrayList(processor.processToken(term));
                    for(String t: temp)
                    {
                        queries.add(t); 
                    }
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // TODO: program this method. Retrieve the postings for the individual terms in the phrase,
            // and positional merge them together.
		
            return null;
		
	}
	
	@Override
	public String toString() {
		return "\"" + String.join(" ", mTerms) + "\"";
	}
}
