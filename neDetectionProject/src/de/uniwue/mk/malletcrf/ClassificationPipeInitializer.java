package de.uniwue.mk.malletcrf;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;

public class ClassificationPipeInitializer implements IPipeInitializer {

  public Pipe initializePipe() {
    Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");

    ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

    // It is a Pipe that can read data from various kinds of sources
    // and convert the given input into character sequence.
    pipeList.add(new Input2CharSequence("UTF-8"));

    // It is pipe that tokenizes a character sequence. It expects a character sequence and converts
    // into tokens. For e.g. after passing through Input2CharSequence you have following data.
    pipeList.add(new CharSequence2TokenSequence(tokenPattern));

    // It is the pipe which is used to remove the stop words from the tokens. The program contains a
    // list of stopword(which are basically useless). If tokens contains these stopwords than that
    // tokens are removed from the TokenList.
    pipeList.add(new TokenSequenceRemoveStopwords(true, true));

    // It converts the Token Sequence to FeatureSequence.It indexes the each token.
    pipeList.add(new TokenSequence2FeatureSequence());

    // Convert Object in the target field into the label.
    pipeList.add(new Target2Label());
    

    // Convert a data field from Feature Sequence to Feature vector. This class does not insist on
    // getting its own Alphabet because it rely on getting from the FeatureSequence input.
    pipeList.add(new FeatureSequence2FeatureVector(false));
    return new SerialPipes(pipeList);
  }
  
  

}
