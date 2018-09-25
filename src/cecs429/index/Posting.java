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
public class Posting {

    private int mDocumentId;
    private List<Integer> h; //h = positions
    
    public Posting(int docID, int pos) {

       mDocumentId = docID;
        h  = new ArrayList<>();
        h.add(pos);
        

    }

    public List getPositions(){
    //System.out.print(mDocumentId);
    return h;
    }

    public int getDocumentId() {

        return mDocumentId;
    }

    
    
    
    public void addPosition(int position){
        
     h.add(position);
    
    
    }
}
