package cecs429.query;

import cecs429.index.Posting;

import java.util.List;
import cecs429.index.Index;
import cecs429.text.NewTokenProcessor;
import cecs429.text.TokenProcessor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A TermLiteral represents a single term in a subquery.
 */
public class TermLiteral implements QueryComponent {
	private String mTerm;
	
	public TermLiteral(String term) {
		mTerm = term;
	}
	
	public String getTerm() {
		return mTerm;
	}
	
	@Override
	public List<Posting> getPostings(Index index) {
            TokenProcessor processor = new NewTokenProcessor(); 
            List<String> queries = new ArrayList(); 
            
            try {
                queries = new ArrayList(processor.processToken(mTerm));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(TermLiteral.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            return index.getPositional_posting(queries.get(0)); //for hypens only care about first item in queries
		
	}
	
	@Override
	public String toString() {
		return mTerm;
	}
}
