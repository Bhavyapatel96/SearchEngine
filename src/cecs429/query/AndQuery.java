package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.stream.Collectors;
import java.util.ArrayList;

import java.util.List;

/**
 * An AndQuery composes other QueryComponents and merges their postings in an
 * intersection-like operation.
 *
 * @author: bhavya
 */
public class AndQuery implements QueryComponent {

    private List<QueryComponent> mComponents;

    /**
     * Constructor for AndQuery initializes private variables
     *
     * @param components
     */
    public AndQuery(List<QueryComponent> components) {
        mComponents = components;
    }

    /**
     * Returns the posting list of an AND merge by returning the intersection of
     * postings.
     *
     * @param index index to retrieve postings from
     * @return list of postings after the AND merge
     */
    @Override
    public List<Posting> getPostings(Index index) {

        List<Posting> results = new ArrayList<>();
        List<QueryComponent> notcomponent = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();
        List<Posting> p1 = new ArrayList<>();
        //number of times we want to perform merge operation.
        int count = mComponents.size() - 1;

        //get postings for first 2 components and check if they are positive or negative
        p0 = mComponents.get(0).getPostings(index);
        p1 = mComponents.get(1).getPostings(index);
        if (mComponents.get(0).Component() == true && mComponents.get(1).Component() == true) {
            results = merge(p0, p1);
        } else {
            if (mComponents.get(0).Component() == false) {
                results = ANDNOTmerge(p1, p0);
            } else {
                results = ANDNOTmerge(p0, p1);
            }
        }

        count = count - 1;
        int k = 2;
        //get next posting and merge it with previous result.
        while (count > 0) {
            List<Posting> p2 = new ArrayList<>();
            p2 = mComponents.get(k).getPostings(index);
            if (mComponents.get(k).Component() == true) {
                results = merge(results, p2);
            } else {
                results = ANDNOTmerge(results, p2);

            }
            k++;
            count--;

        }

        mComponents.clear();

        return results;
    }

    /**
     * Merges two postings list based on their intersection Only merges when
     * docIds match Only merges positive literals
     *
     * @param a list 1 to merge
     * @param b list 2 to merge
     * @return merged list of postings from list 1 and 2
     */
    public List<Posting> merge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();

        int m = a.size();
        int n = b.size();

        int i = 0;
        int j = 0;

        while (i < m && j < n) {
            if (a.get(i).getDocumentId() == b.get(j).getDocumentId()) {

                result.add(b.get(j));
                i++;
                j++;
            } else if (a.get(i).getDocumentId() < b.get(j).getDocumentId()) {
                i++;
            } else {
                j++;
            }

        }

        return result;
    }

    /**
     * Similar to the merge function above instead merges a negative and
     * positive literal
     *
     * @param a list 1 to merge
     * @param b list 2 to merge
     * @return
     */
    public List<Posting> ANDNOTmerge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();

        //'a' contains the positive term while 'b' contains negative term.
        //we want those postings which contains a but not b.
        int m = a.size();
        int n = b.size();
        int i = 0;
        int j = 0;
        while (i < m) {
            if (a.get(i).getDocumentId() == b.get(j).getDocumentId()) {
                i++;
                j++;
                if (j == n) {
                    while (i < m) {
                        result.add(a.get(i));
                        i++;
                    }
                }
            } else if (a.get(i).getDocumentId() < b.get(j).getDocumentId()) {
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

    /**
     * Used to determine whether component is a positive or negative literal
     *
     * @return true for positive
     */
    @Override
    public Boolean Component() {
        return true;
    }

}
