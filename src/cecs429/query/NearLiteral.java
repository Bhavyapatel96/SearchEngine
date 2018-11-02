/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * positional merge between the terms to the left and right of the NEAR operator,
 * selecting documents where the second term appears at most k positions
 * away from thefirst term. 
 * @author dayanarios
 */
public class NearLiteral implements QueryComponent {
    PhraseLiteral nliteral; 
    int k; 
    List<QueryComponent> tokens; 
    
    public NearLiteral(String terms ){

        int startIndex = 0; 
        
        Pattern p = Pattern.compile("\bNEAR/.\b"); //. represents single character  
        Matcher m = p.matcher(terms);
       if(m.lookingAt())
       {
           int near_startIndex = m.start();
           int near_endIndex = m.end(); 
           
           String near_keyword = terms.substring(near_startIndex, near_endIndex);
           k = Integer.parseInt(near_keyword.substring(near_keyword.length()-1)); 
           
        
           //classifies the query's tokens as specific QueryComponents 
            while (startIndex < 8){

                if (terms.charAt(startIndex) == '"') {
                    String subStr = terms.substring(startIndex + 1);
                    int posOfQuote = subStr.indexOf('"', 0);
                    tokens.add(new PhraseLiteral(terms.substring(2,posOfQuote), 1));
                    startIndex = posOfQuote +1; 
                }
                else if (terms.charAt(startIndex) == '[') {
                    String subStr = terms.substring(startIndex + 1);
                    int posOfQuote = subStr.indexOf(']', 0);
                    tokens.add(new NearLiteral(terms.substring(2,posOfQuote)));
                    startIndex = posOfQuote +1; 
                }
                else{
                    String subStr = terms.substring(startIndex + 1);
                    int posOfQuote = subStr.indexOf(' ', 0);
                    tokens.add(new TermLiteral(terms.substring(2,posOfQuote)));
                    startIndex = posOfQuote +1; 
                }
            }
       }
       
       nliteral = new PhraseLiteral(tokens.get(0) + " " + tokens.get(1), k); 
        
        
            
    }

    @Override
    public List<Posting> getPostings(Index index) {
        List<Posting> results = new ArrayList<>();
        
        results = nliteral.getPostings(index); 
        
        return results; 
        
    }

    @Override
    public Boolean Component() {
        return true;
    }
}
