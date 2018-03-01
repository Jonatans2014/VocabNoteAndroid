package almeida.john.vocabnote.almieda.john.fragments;

/**
 * Created by John on 27/02/2018.
 */

public class DicInfo {

   private String definitions;
   private  String example;
   private String pronuncation;


    DicInfo(String definitions, String example, String pronuncation)
    {
        this.definitions = definitions;
        this.example = example;
        this.pronuncation = pronuncation;
    }


    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getPronuncation() {
        return pronuncation;
    }

    public void setPronuncation(String pronuncation) {
        this.pronuncation = pronuncation;
    }
}
