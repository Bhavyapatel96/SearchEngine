package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.List;
import java.util.stream.Collectors;
import cecs429.index.Positional_inverted_index;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

/**
 * An AndQuery composes other QueryComponents and merges their postings in an
 * intersection-like operation.
 *
 * @author: bhavy
 */
public class AndQuery implements QueryComponent {

    private List<QueryComponent> mComponents;

    public AndQuery(List<QueryComponent> components) {
        mComponents = components;

        //if query is "the park is big", mComponents contain [the, park, is, big}
        //it is an array of term literals
    }

    @Override
    public List<Posting> getPostings(Index index) {

        List<Posting> results = new ArrayList<>();
        List<QueryComponent> notcomponent = new ArrayList<>();
        List<String> phrase = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();
        List<Posting> p1 = new ArrayList<>();

        //how many times we want to perform merge.
        int count = mComponents.size() - 1;
        
        
        p0=mComponents.get(0).getPostings(index);
        p1=mComponents.get(1).getPostings(index);
        if( mComponents.get(0).Component() ==true && mComponents.get(1).Component()==true ){
            results=merge(p0, p1);
        }
        else{
            if(mComponents.get(0).Component()==false){
            results=ANDNOTmerge(p1, p0);
            }
            else{
            results=ANDNOTmerge(p0, p1);
            }
        }
        
        count = count -1;
        int k=2;
        while (count > 0){
            List<Posting> p2 = new ArrayList<>();
            p2=mComponents.get(k).getPostings(index);
            if( mComponents.get(k).Component() ==true ){
            results=merge(results, p2);
        }
        else{
            results=ANDNOTmerge(results, p2);
            
        }
                k++;
                count--;
        
        }
        /*
        if (term1.charAt(0) == '-') {
            
            //1st term is negative component and is phrase literal
            
            
            if(term1.charAt(1)=='"'){
               
                p0=mComponents.get(0).getPostings(index);
                p1 = mComponents.get(1).getPostings(index);
                results = ANDNOTmerge(p1, p0);
            }
            else{
            //notcomponent.add(mComponents.get(0));
            //NotQuery notQuery = new NotQuery(notcomponent);
            //p0 = notQuery.getPostings(index);
            p0=mComponents.get(0).getPostings(index);
            p1 = mComponents.get(1).getPostings(index);
            results = ANDNOTmerge(p1, p0);
            }
        } else if (term2.charAt(0) == '-') {
            //2nd term is negative component
            
            if(term2.charAt(1)=='"'){
                //String t2=term2.substring(1);
                //phrase.add(t2);
                //PhraseLiteral p = new PhraseLiteral(phrase.get(1));
                //p1=p.getPostings(index);
                p1=mComponents.get(1).getPostings(index);
                p0= mComponents.get(0).getPostings(index);
                results = ANDNOTmerge(p0, p1);
            }
            else{
            //notcomponent.add(mComponents.get(1));
            //NotQuery notQuery = new NotQuery(notcomponent);
            //p1 = notQuery.getPostings(index);
            p1=mComponents.get(1).getPostings(index);
            p0 = mComponents.get(0).getPostings(index);
            //positive term goes first
            results = ANDNOTmerge(p0, p1);
            }
        } else {
            //no negative component, perform simply AND
            p0 = mComponents.get(0).getPostings(index);
            p1 = mComponents.get(1).getPostings(index);

            results = merge(p0, p1);
        }
            count = count - 1;
            int k = 2;

            while (count > 0) {

                List<Posting> p2 = new ArrayList<>();
                String termk = mComponents.get(k).toString();
                if (termk.charAt(0)== '-'){
                    //next term is negative phrase literal
                    if(termk.charAt(1)=='"'){
                        String t=termk.substring(1);
                        phrase.add(t);
                        PhraseLiteral p_k = new PhraseLiteral(phrase.get(k));
                        p2=p_k.getPostings(index);
                        results = ANDNOTmerge(results, p2);
                    }
                    //else its normal negative component
                    else{
                    notcomponent.add(mComponents.get(k));
                    NotQuery notQuery = new NotQuery(notcomponent);
                    p2 = notQuery.getPostings(index);
                    //positive term goes first
                    results = ANDNOTmerge(results, p2);
                    }
                }
                //else next term is not negative
                else{
                    p2 = mComponents.get(k).getPostings(index);
                
                    results = merge(results, p2);
                }
                
                count = count - 1;
                k = k + 1;

                //System.out.println(results);
            }
        */
        
        mComponents.clear();
        // TODO: program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
        // intersecting the resulting postings.
        return results;
    }

    public List<Posting> merge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();
        List<Integer> p0 = new ArrayList<>();
        List<Integer> p1 = new ArrayList<>();
        for (Posting a1 : a) {

            p0.add(a1.getDocumentId());
        }
        for (Posting b1 : b) {

            p1.add(b1.getDocumentId());
        }

        int m = p0.size();
        int n = p1.size();
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (p0.get(i) == p1.get(j)) {
                result.add(b.get(j));
                i++;
                j++;
            } else if (p0.get(i) < p1.get(j)) {

                i++;
            } else {
                j++;
            }

        }

        return result;
    }

    public List<Posting> ANDNOTmerge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();

        //a contains the positive term while b contains negative term.
        //we want those postings which contains a but not b.
        List<Integer> p0 = new ArrayList<>();
        List<Integer> p1 = new ArrayList<>();
        for (Posting a1 : a) {

            p0.add(a1.getDocumentId());
        }
        for (Posting b1 : b) {

            p1.add(b1.getDocumentId());
        }

        int m = p0.size();
        int n = p1.size();
        int i = 0;
        int j = 0;
        while (i < m) {
            if (p0.get(i) == p1.get(j)) {
                i++;
                j++;
                if (j == n) {
                    while (i < m) {
                        result.add(a.get(i));
                        i++;
                    }
                }
            } else if (p0.get(i) < p1.get(j)) {
                result.add(a.get(i));
                i++;

            } else {
                j++;
                if (j == n) {
                    while (i < m) {
                        result.add(a.get(i));
                        i++;
                    }
                }
            }

        }

        return result;
    }

    @Override
    public String toString() {
        return String.join(" ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()));
    }

    private ArrayList<Object> toString(int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Component() {
        return true;
    }

}
