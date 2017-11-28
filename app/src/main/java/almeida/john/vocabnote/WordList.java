package almeida.john.vocabnote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 28/11/2017.
 */

public class WordList{
    private String Word;
    private String Classification;

    public WordList(String word, String classification) {
        Word = word;
        Classification = classification;
    }

    public String getWord() {
        return Word;
    }

    public String getClassification() {
        return Classification;
    }
}