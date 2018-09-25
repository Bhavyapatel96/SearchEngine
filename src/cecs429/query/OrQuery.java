package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An OrQuery composes other QueryComponents and merges their postings with a
 * union-type operation.
 */
public class OrQuery implements QueryComponent {
    // The components of the Or query.

    private List<QueryComponent> mComponents;

    public OrQuery(List<QueryComponent> components) {
        mComponents = components;
    }

      @Override
    public List<Posting> getPostings(Index index) {
        List<Posting> results = new ArrayList<>();
        //List<Positional_posting> result = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();
        List<Posting> p1 = new ArrayList<>();
        
        
        //how many times we want to perform merge.
        int count = mComponents.size() - 1;

        TermLiteral t0 = new TermLiteral(mComponents.get(0).toString());
        TermLiteral t1 = new TermLiteral(mComponents.get(1).toString());
        //List<Integer> p0 = new ArrayList<>();
        //List<Integer> p1 = new ArrayList<>();

      
        
        p0=t0.getPostings(index);
        p1=t1.getPostings(index);
        
        
        results = merge(p0,p1);
        count = count - 1;
        int k = 2;

        while (count > 0) {
            TermLiteral t = new TermLiteral(mComponents.get(k).toString());
           
            List<Posting> p2 = new ArrayList<>();
            p2=t.getPostings(index);
           
            results = merge(results,p2);
            count = count - 1;
            k = k + 1;

            //System.out.println(results);
        }
        mComponents.clear();
        // TODO: program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
        // intersecting the resulting postings.
        return results;
    }

    public List<Posting> merge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();
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
        while (i < m || j < n) {
            if (p0.get(i) == p1.get(j)) {
                result.add(b.get(j));
                i++;
                j++;
                if(i==m){
                    while(j<n){
                    result.add(b.get(j));
                    j++;
                    }
                }
                else if(j==n){
                    while(i<m){
                    result.add(a.get(i));
                    i++;
                    }
                }
            } else if (p0.get(i) < p1.get(j)) {
                result.add(a.get(i));
                i++;
                if(i==m){
                    while(j<n){
                    result.add(b.get(j));
                    j++;
                    }
                }
            } else {
                result.add(b.get(j));
                j++;
                if(j==n){
                    while(i<m){
                    result.add(a.get(i));
                    i++;
                    }
                }
            }

        }

        return result;
    }

            @Override
            public String toString
            
                () {
		// Returns a string of the form "[SUBQUERY] + [SUBQUERY] + [SUBQUERY]"
		return "("
                        + String.join(" + ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()))
                        + " )";
            }
        }
