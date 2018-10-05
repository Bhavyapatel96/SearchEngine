package cecs429.query;

import cecs429.index.Index;
import cecs429.index.Posting;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An OrQuery composes other QueryComponents and merges their postings with a
 * union-type operation.
 *
 * @author bhavya
 */
public class OrQuery implements QueryComponent {
    // The components of the Or query.

    private List<QueryComponent> mComponents;

    /**
     * initializes private variables
     *
     * @param components
     */
    public OrQuery(List<QueryComponent> components) {
        mComponents = components;
    }

    /**
     * generates the merged postings
     *
     * @param index index to retrieve postings from
     * @return merged list of postings for an OR operation
     */
    @Override
    public List<Posting> getPostings(Index index) {
        List<Posting> results = new ArrayList<>();
        List<Posting> p0 = new ArrayList<>();
        List<Posting> p1 = new ArrayList<>();

        //number of times we want to perform merge.
        int count = mComponents.size() - 1;

        p0 = mComponents.get(0).getPostings(index);
        p1 = mComponents.get(1).getPostings(index);

        results = merge(p0, p1);
        count = count - 1;
        int k = 2;

        while (count > 0) {

            List<Posting> p2 = new ArrayList<>();

            p2 = mComponents.get(k).getPostings(index);

            results = merge(results, p2);
            count--;
            k++;

        }
        mComponents.clear();
        // TODO: program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
        // intersecting the resulting postings.
        return results;
    }

    /**
     * generates the merged postings using a union like operation
     *
     * @param a list 1 to merge
     * @param b list 2 to merge
     * @return
     */
    public List<Posting> merge(List<Posting> a, List<Posting> b) {
        List<Posting> result = new ArrayList<>();

        int m = a.size();
        int n = b.size();
        int i = 0;
        int j = 0;
        while (i < m || j < n) {
            if (m == 0) {
                while (j < n) {
                    result.add(b.get(j));
                    j++;
                }
            } else if (n == 0) {
                while (i < m) {
                    result.add(a.get(i));
                    i++;
                }
            } else {
                if (a.get(i).getDocumentId() == b.get(j).getDocumentId()) {
                    result.add(b.get(j));
                    i++;
                    j++;
                    if (i == m) {
                        while (j < n) {
                            result.add(b.get(j));
                            j++;
                        }
                    } else if (j == n) {
                        while (i < m) {
                            result.add(a.get(i));
                            i++;
                        }
                    }
                } else if (a.get(i).getDocumentId() < b.get(j).getDocumentId()) {
                    result.add(a.get(i));
                    i++;
                    if (i == m) {
                        while (j < n) {
                            result.add(b.get(j));
                            j++;
                        }
                    }
                } else {
                    result.add(b.get(j));
                    j++;
                    if (j == n) {
                        while (i < m) {
                            result.add(a.get(i));
                            i++;
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        // Returns a string of the form "[SUBQUERY] + [SUBQUERY] + [SUBQUERY]"
        return "("
                + String.join(" + ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()))
                + " )";
    }

    @Override
    public Boolean Component() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
