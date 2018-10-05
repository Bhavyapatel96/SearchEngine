/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Creates a positional inverted index
 *
 * @author bhavya
 */
public class Positional_inverted_index implements Index {

    HashMap<String, List<Posting>> mInvertedIndex;

    public Positional_inverted_index() {
        mInvertedIndex = new HashMap<String, List<Posting>>();

    }

    /**
     * Associates the given documentId with the given term in the index. adds an
     * association between the given term and docID in our inverted matrix. only
     * adds the docID (posting) once in the list for each given term
     */
    public void addTerm(String term, int position, int docID) {

        //k is the list containing hashmap of docid>positions
        if (mInvertedIndex.containsKey(term)) {
            //we have to check docID first, if its there, add positions only. If not, add docID>positions both

            List<Posting> list = mInvertedIndex.get(term);

            if (list.get(list.size() - 1).getDocumentId() == docID) {
                //List<Positional_posting> l1= new ArrayList<>();
                Posting p = list.get(list.size() - 1);
                p.addPosition(position);
            } else {
                Posting p = new Posting(docID, position);
                list.add(p);

            }

        } else {

            List<Posting> list = new ArrayList<>();
            Posting posting = new Posting(docID, position);
            list.add(posting);
            mInvertedIndex.put(term, list);

        }

    }

    public List<Posting> getPositional_posting(String term) { //returns all docs that contian the terms

        //HashMap<String,List<Posting>> h;
        List<Posting> results = new ArrayList<>();

        if (mInvertedIndex.containsKey(term)) {
            results = mInvertedIndex.get(term);

        }
        return results;
    }

    public List<String> getVocabulary() {

        Set<String> keys = mInvertedIndex.keySet();
        List<String> vocab = new ArrayList<String>(keys);

        Collections.sort(vocab);

        return vocab;
    }

    public void print() {

        System.out.println(mInvertedIndex.keySet());
    }

}
