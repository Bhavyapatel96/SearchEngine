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
 * Contains literals that will be used in a not query.
 *
 * @author bhavya
 */
public class NotQuery implements QueryComponent {

    //private List<QueryComponent> mComponents;
    private List<QueryComponent> mTerms = new ArrayList<>();
    private String component;

    public NotQuery(String terms) {
        //Checks if the NOT component is phrase literal or term.
        if (terms.charAt(1) == '"') {
            mTerms.add(new PhraseLiteral(terms.substring(2)));

        } else {
            mTerms.add(new TermLiteral(terms.substring(1)));
        }

    }

    /**
     * Gathers the postings for the query
     *
     * @param index supplied by program
     * @return list of postings that contain the literal
     */
    @Override
    public List<Posting> getPostings(Index index) {
        List<Posting> results = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();

        p0 = mTerms.get(0).getPostings(index);

        results = p0;

        return results;
    }

    private ArrayList<Object> toString(int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Used to determine whether component is a positive or negative literal
     *
     * @return false for negative
     */
    @Override
    public Boolean Component() {
        return false;
    }

}
