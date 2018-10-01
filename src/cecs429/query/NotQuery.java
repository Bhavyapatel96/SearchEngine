/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author bhavy
 */
public class NotQuery implements QueryComponent {

    //private List<QueryComponent> mComponents;
    private List<QueryComponent> mTerms = new ArrayList<>();
    private String component;

        //-explore
        //-"explore park"
        
       
	
	public NotQuery(String terms) {
                //terms = -explore the park
		if(terms.charAt(1)=='"'){
                    mTerms.add(new PhraseLiteral(terms.substring(2)));
                    
                }
                else{
                    mTerms.add(new TermLiteral(terms.substring(1)));
                }
                
                
                
	}

    @Override
    public List<Posting> getPostings(Index index) {
        List<Posting> results = new ArrayList<>();
        //List<Positional_posting> result = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();
        //List<Posting> p1 = new ArrayList<>();
        
        
        //how many times we want to perform merge.
       // int count = mComponents.size() - 1;

        
        p0=mTerms.get(0).getPostings(index);
        
        //p1=mComponents.get(1).getPostings(index);
        
        results = p0;
        
        
        // TODO: program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
        // intersecting the resulting postings.
        return results;
    }

    /*public List<Posting> merge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();
        
        //a contains the positive term while b contains negative term.
        //we want those postings which contains a but not b.
        List<Integer> p0 = new ArrayList<>();
        List<Integer> p1 = new ArrayList<>();
        for(Posting a1:a){
        
            p0.add(a1.getDocumentId());
        }
        for(Posting b1:b){
        
            p1.add(b1.getDocumentId());
        }
        
        int m = p0.size();
        int n = p1.size();
        int i = 0;
        int j = 0;
        while (i < m ) {
            if (p0.get(i) == p1.get(j)) {
                i++;
                j++;
                if (j==n){
                    while(i<m){
                        result.add(a.get(i));
                        i++;
                    }
                }
            } else if (p0.get(i) < p1.get(j)) {
                result.add(a.get(i));
                i++;
                
            } else {
                j++;
                if (j==n){
                    while(i<m){
                        result.add(a.get(i));
                        i++;
                    }
                }
            }

        }

        return result;
    }
*/
    

    private ArrayList<Object> toString(int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
@Override
    public Boolean Component() {
        return false;
    }

    

}

