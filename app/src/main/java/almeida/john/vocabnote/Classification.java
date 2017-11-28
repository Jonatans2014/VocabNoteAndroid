package almeida.john.vocabnote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 28/11/2017.
 */

public class Classification{

    private String Classification;
    private List<WordsList> Word;

    public Classification(String classification, List<WordsList> Word) {
        this.Classification = classification;
        this.Word =  Word;
    }

    public String getClassification() {
        return Classification;
    }

    public List<WordsList> getWord() {
        return Word;
    }
}


class WordsList
{
    private  String Word;

    public WordsList(String word) {
        Word = word;
    }

    public String getWord() {
        return Word;
    }
}