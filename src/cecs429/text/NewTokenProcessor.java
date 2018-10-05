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
 * Advanced token processor.
 *
 * @author dayanarios
 */
public class NewTokenProcessor implements TokenProcessor {

    /**
     * Removes all nonalphanumeric characters from the beginning and end of
     * token Removes all \' and \" found anywhere in the token Removes all
     * hypens and splits token based on hypens Lowercases the token Stems the
     * token
     *
     * @param token token found in current doc being indexed
     * @return a normalized token to store in the vocab of the index
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<String> processToken(String token) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String temp = token.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", ""); //removes nonalphanumeric chars from beginning and end
        temp = temp.replaceAll("\'+", "").replaceAll("\"+", "");  //removes all apostropes and quotations

        String modifiedString = temp; //copy of string to split 

        List<String> terms = new ArrayList<String>();

        if (!modifiedString.equals(temp.replaceAll("\\-", ""))) //hypens exist in string
        {
            modifiedString = modifiedString.replaceAll("\\-", " "); //replace all hypens with spaces

            String[] splitString = modifiedString.split("\\s+"); //split string around whitespace
            String concatenatedString = "";

            for (int i = 0; i < splitString.length; i++) {
                terms.add(splitString[i].toLowerCase());
                concatenatedString += splitString[i];
            }

            terms.add(0, concatenatedString.toLowerCase());
        } else {
            terms.add(modifiedString.toLowerCase());
        }

        for (int i = 0; i < terms.size(); i++) {
            terms.set(i, PorterStemmer(terms.get(i))); //replaces the term at i with its stemmed version
        }

        return terms;
    }

    /**
     * Stems a token according to Porter Stemmer
     *
     * @param token normalized token supplied by the processToken method above
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public String PorterStemmer(String token) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class stemClass = Class.forName("cecs429.text.Porter2Stemmer.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        stemmer.setCurrent(token);
        stemmer.stem();

        return stemmer.getCurrent();
    }

}
