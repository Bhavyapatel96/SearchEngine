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
public class Positional_inverted_index implements Index2{
    HashMap<String, List<Positional_posting>> mInvertedIndex;
    private List<String> mVocabulary;
  
    
    public Positional_inverted_index() 
    {
        mInvertedIndex = new HashMap<String, List<Positional_posting>>();
        mVocabulary = new ArrayList<String>();
        
    }
    
    /**
	 * Associates the given documentId with the given term in the index.
         * adds an association between the given term and docID in our inverted
         * matrix. only adds the docID (posting) once in the list for each 
         * given term
	 */
        public void addTerm(String term, int position , int docID){
            
            //k is the list containing hashmap of docid>positions
            if(mInvertedIndex.containsKey(term)){
               //we have to check docID first, if its there, add positions only. If not, add docID>positions both
               
               
               
                List<Positional_posting> list = mInvertedIndex.get(term);
               
                
                if (list.get(list.size()-1).getDocumentId()==docID){
                    //List<Positional_posting> l1= new ArrayList<>();
                    Positional_posting p=list.get(list.size()-1);
                    p.addPosition(position);
                }
                else{
                    Positional_posting p=new Positional_posting(docID,position);
                    list.add(p);
                   
                
                }
             
                
                 
                 
            }
               
            
            else{
                
                List<Positional_posting> list = new ArrayList<>();
                Positional_posting posting= new Positional_posting(docID, position);
                list.add(posting);
                mInvertedIndex.put(term, list);
               
            }
        
        }
        /*
	public void addTerm(String term, int documentId) {  //assumes all keys are present
		//int vIndex = Collections.binarySearch(mVocabulary, term);
                List<Positional_posting> results = new ArrayList<>();  //will store results
                
		        
                if(mInvertedIndex.containsKey(term)) //if term is already a key just add values
                {
                        results = mInvertedIndex.get(term);
                        
                        List l2 = null;
                        
                        int lastIndex = results.size()-1; //index of last element
                        if(results.get(lastIndex).getDocumentId() != documentId)
                        {
                            Positional_posting temp = new Positional_posting(documentId,l2); //docId is index in mVocab
                            results.add(temp);
                        }
                   
                }
                else    //key doesnt exsist must add it
                {
                    List l2=null;
                    Positional_posting temp = new Positional_posting(documentId,l2); //make a list to hold posting to add to index 
                    results.add(temp); 
                    mInvertedIndex.put(term, results); //second argument is a list thus need to use results
                }
                       
	
	}
    */
	public List<Positional_posting> getPositional_posting (String term) { //returns all docs that contian the terms
		
                HashMap<String,List<Positional_posting>> h;
                List<Positional_posting> results = new ArrayList<>();
		
		
                
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

    

   

        
        
    
    
}