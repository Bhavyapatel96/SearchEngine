/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.text;


import cecs429.text.Porter2Stemmer.SnowballProgram;
import cecs429.text.Porter2Stemmer.SnowballStemmer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dayanarios
 */
public class NewTokenProcessor implements TokenProcessor {
    @Override
	public List<String> processToken(String token) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            
            
            String temp = token.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", ""); //removes nonalphanumeric chars from beginning and end
            temp = temp.replaceAll("\'+", "").replaceAll("\"+", "");  //removes all apostropes and quotations
            
            String modifiedString = temp; //copy of string to split 

            List<String> terms = new ArrayList<String>();
            
            if(!modifiedString.equals(temp.replaceAll("\\-", ""))) //hypens exist in string
                {
                    modifiedString = modifiedString.replaceAll("\\-", " "); //replace all hypens with spaces
                   
                    String[] splitString = modifiedString.split("\\s+"); //split string around whitespace
                    String concatenatedString = ""; 

                    for(int i = 0; i<splitString.length; i++)
                    {
                        terms.add(splitString[i].toLowerCase()); 
                        concatenatedString+= splitString[i]; 
                    }

                    terms.add(0, concatenatedString.toLowerCase());
                }
                else{
                    terms.add(modifiedString.toLowerCase()); 
                }
                   
            
             for(int i = 0; i< terms.size(); i++)
             {
                 terms.set(i, PorterStemmer(terms.get(i))); //replaces the term at i with its stemmed version
             }
            
            

            
            /**
             * for testing purposes.
             */
            
            /*
            
            List<String> test = new ArrayList<String>();
            test.add("-#Hello8()-"); 
            test.add("'this--is\"\"a.test\"");
            test.add("^hypenation-test-past,"); 
            test.add("192.168.1.1");
            test.add("consolidating"); //stemmer should give consolid
            test.add("knocking"); //stemmer should give knock
            
            List<String> terms = new ArrayList<String>(); 
            
            for(int j = 0; j< test.size(); j++)
            {
                String temp = test.get(j).replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", "");//token.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", ""); //removes nonalphanumeric chars from beginning and end
                
                temp = temp.replaceAll("\'", "").replaceAll("\"", "");  //removes all apostropes and quotations

                //System.out.println("modified string: " + temp); 
                
                
                String modifiedString = temp; //copy of string to split 
                
                if(!modifiedString.equals(temp.replaceAll("\\-", ""))) //hypens exist in string
                {
                    modifiedString = modifiedString.replaceAll("\\-", " ");
                    //terms.add(modifiedString); //adds original term without any hypens
                    
                    

                    String[] splitString = modifiedString.split("\\s+"); 
                    String concatenatedString = ""; 

                    for(int i = 0; i<splitString.length; i++)
                    {
                        terms.add(splitString[i].toLowerCase()); 
                        //System.out.println("split string at " + i + " " + splitString[i]); 
                        concatenatedString+= splitString[i]; 
                    }

                    terms.add(concatenatedString.toLowerCase());
                }
                else{
                    terms.add(modifiedString.toLowerCase().toLowerCase()); 
                }
                
            }
            
            
            System.out.println("\n");
           
           for(int i = 0; i<terms.size(); i++)
           {
               System.out.println("result after rules 1-4: " + terms.get(i));
               System.out.println("porter stemmer result: " + PorterStemmer(terms.get(i))); 
           }
         
            */
            
           /** 
            * End testing.
            */ 
           
            
            return terms; 
        }
        
        public String PorterStemmer(String token) throws ClassNotFoundException, InstantiationException, IllegalAccessException
        {
            Class stemClass = Class.forName("cecs429.text.Porter2Stemmer.englishStemmer"); 
            SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
            stemmer.setCurrent(token);
            stemmer.stem();
            
            return stemmer.getCurrent(); 
        }
    
}
