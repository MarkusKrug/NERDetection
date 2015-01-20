package de.uniwue.mkrug.nerapplication;

import java.util.List;

import org.apache.uima.cas.text.AnnotationFS;

public class TokenInstance {
  public AnnotationFS token;

  private String text;

  // tells if AFTER that charcter the word is split
  private boolean splitHere;

  private List<Integer> splitList;

  private String original;

  private int index;


  public TokenInstance(AnnotationFS token2) {
    this.token = token2;
  }

  public TokenInstance(String text, Boolean split, List<Integer> splitIndexes, String original,
          int Index) {

    this.text = text;
    this.splitHere = split;
    this.splitList = splitIndexes;
    this.index = Index;

    this.original = original;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public boolean isSplitHere() {
    return splitHere;
  }

  public void setSplitHere(boolean splitHere) {
    this.splitHere = splitHere;
  }

  public List<Integer> getSplitList() {
    return splitList;
  }

  public void setSplitList(List<Integer> splitList) {
    this.splitList = splitList;
  }

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public AnnotationFS getToken() {
    return token;
  }

  public void setToken(AnnotationFS token) {
    this.token = token;
  }

}
