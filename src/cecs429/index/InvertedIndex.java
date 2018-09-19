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
import java.util.List;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author dayanarios
 */
public class InvertedIndex implements Index{
    HashMap<String, List<Posting>> mInvertedIndex; 
    private List<String> mVocabulary;
  
    
    public InvertedIndex() //Collection<String> vocabulary)  might have to add  vocab and vocab size as arguemnts
    {
        mInvertedIndex = new HashMap<String, List<Posting>>();
        mVocabulary = new ArrayList<String>();
        
    }
    
    /**
	 * Associates the given documentId with the given term in the index.
         * adds an association between the given term and docID in our inverted
         * matrix. only adds the docID (posting) once in the list for each 
         * given term
	 */
	public void addTerm(String term, int documentId) {  //assumes all keys are present
		//int vIndex = Collections.binarySearch(mVocabulary, term);
                List<Posting> results = new ArrayList<>();  //will store results
                
		        
                if(mInvertedIndex.containsKey(term)) //if term is already a key just add values
                {
                    results = mInvertedIndex.get(term);

                        int lastIndex = results.size()-1; //index of last element
                        if(results.get(lastIndex).getDocumentId() != documentId)
                        {
                            Posting temp = new Posting(documentId); //docId is index in mVocab
                            results.add(temp);
                        }
                   
                }
                else    //key doesnt exsist must add it
                {
                    Posting temp = new Posting(documentId); //make a list to hold posting to add to index 
                    results.add(temp); 
                    mInvertedIndex.put(term, results); //second argument is a list thus need to use results
                }
                       
	
	}
    
    @Override
	public List<Posting> getPostings(String term) { //returns all docs that contian the term
		List<Posting> results = new ArrayList<>();
		
		// TODO: implement this method.
		// Binary search the mVocabulary array for the given term.
                //int vIndex = Collections.binarySearch(mVocabulary, term);
              
		// Walk down the mMatrix row for the term and collect the document IDs (column indices)
                // of the "true" entries.
                //if(vIndex >= 0){        //term exist in vocab
                    
                   // results = mInvertedIndex.get(term); 
                  
                //}
                
                if(mInvertedIndex.containsKey(term))
                {
                   results = mInvertedIndex.get(term);  
                }
		return results;
	}
        
        
        public List<String> getVocabulary() {
            
            Collections.sort(mVocabulary);
		return Collections.unmodifiableList(mVocabulary);
	}
        
        public void print()
        {
            
            for (String key : mInvertedIndex.keySet()) 
            {
                List<Posting> list = mInvertedIndex.get(key);
                System.out.print("list size: " + list.size() + " ");
                
                System.out.print(key + ": ");
                for(int i = 0; i< list.size(); i++)
                {
                     
                    System.out.print(list.get(i).getDocumentId() + ", "); 
                }
                
                System.out.println(); 
                
              
            }

        }
    
    
}
