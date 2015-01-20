package de.uniwue.mk.malletcrf;

import java.util.ArrayList;
import java.util.List;

public class CrossFold {

  // represents one Fold;

  List<MalletSequence> trainSequences;

  List<MalletSequence> testSequences;

  public CrossFold(List<MalletSequence> trainSequences, List<MalletSequence> testSequences) {
    super();
    this.trainSequences = trainSequences;
    this.testSequences = testSequences;
  }

  public List<MalletSequence> getTrainSequences() {
    return trainSequences;
  }

  public void setTrainSequences(List<MalletSequence> trainSequences) {
    this.trainSequences = trainSequences;
  }

  public List<MalletSequence> getTestSequences() {
    return testSequences;
  }

  public void setTestSequences(List<MalletSequence> testSequences) {
    this.testSequences = testSequences;
  }

  public String[] getTestGoldLabels() {

    List<String> goldlabels = new ArrayList<String>();
    for (MalletSequence seq : this.testSequences) {

      for (String s : seq.getFeatures()) {

        // last feature is the gold Feature
        goldlabels.add(s.split(" ")[s.split(" ").length - 1]);
      }

    }

    return goldlabels.toArray(new String[0]);
  }

}
