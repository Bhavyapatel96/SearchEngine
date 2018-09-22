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
 * @author bhavy
 */
public class Positional_posting {

    private int mDocumentId;
  
 //   private HashMap<Integer, List<Integer>> h = new HashMap<Integer, List<Integer>>();
    private List<Integer> h; //h == positions
    
    public Positional_posting(int docID, int pos) {

       mDocumentId = docID;
        h  = new ArrayList<>();
        h.add(pos);
        

    }

  /*  public Positional_posting(int docID) {
        System.out.println(h);
        mDocumentId = docID;

        mPosting = h.get(mDocumentId);
        h.putIfAbsent(docID, mPosting);
      //  h.put(mDocumentId, mPosting);
        h1.add(h);
    }*/

    

    public List getPositions(){
    //System.out.print(mDocumentId);
    return h;
    }

    public int getDocumentId() {

        return mDocumentId;
    }

    /*public List getPositions() {
        return mPosting;
    }*/
    
    
    public void addPosition(int position){
        
     h.add(position);
    
    
    }
}
