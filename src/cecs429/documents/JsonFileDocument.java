/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs429.documents;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;

/**
 * Reads contents of a json file
 *
 * @author dayanarios
 */
public class JsonFileDocument implements FileDocument {

    /**
     * if error occurs change path of gson library files on the left expand
     * "Libraries", right click GSON, click edit, add jar files for classpath,
     * sources, javdoc I've emailed you the files.
     */
    @SerializedName("title")
    private String mtitle;
    @SerializedName("body")
    private String mbody;

    private int mDocumentId;
    private Path mFilePath;

    public JsonFileDocument(int id, Path absoluteFilePath) {
        mDocumentId = id;
        mFilePath = absoluteFilePath;
    }

    /**
     * the content of a .json is the value of the content key. The library you
     * use in (e) can probably be used to read the content key of a file as a
     * String; you will need to find a way to construct a Reader/TextReader
     * around that string in memory.
     *
     */
    @Override
    public Reader getContent() {
        BufferedReader reader = null;

        File file = new File(mFilePath.toString());

        String contents = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            Gson gson = new GsonBuilder().create();
            JsonFileDocument document = gson.fromJson(reader, JsonFileDocument.class);
            this.mtitle = document.mtitle;
            this.mbody = "";
            contents = document.mbody;   //contians the contents of the article
            document.setBody(""); //doesnt keep it in memory 

        } catch (Exception ex) {
        }

        return new StringReader(contents);

    }

    public void setBody(String body) {
        this.mbody = body;
    }

    public void setTitle(String title) {
        this.mtitle = title;
    }

    public String getbody() {
        return mbody;
    }

    @Override
    public Path getFilePath() {
        return mFilePath;
    }

    @Override
    public int getId() {
        return mDocumentId;
    }

    @Override
    public String getTitle() {
        return mtitle;
    }

    public static FileDocument loadJsonFileDocument(Path absolutePath, int documentId) {
        return new JsonFileDocument(documentId, absolutePath);
    }
}
