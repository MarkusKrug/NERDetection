package de.uniwue.mk.malletcrf;

import java.util.HashSet;

public class CRFEvaluator {

  // does the evaluation of the CRF Result

  // the id of the Evaluator
  String id;

  private String[] gold;

  private String[] result;
  
  HashSet<LabelEvaluation> labelEvals;

  public CRFEvaluator(String[] gold, String[] result) {
    // compares the labels, both arrays should have the same size !!

    labelEvals = new HashSet<LabelEvaluation>();
    this.gold = gold;
    this.result = result;
    
    // add all labels
    for(String s : gold){
      LabelEvaluation le = new LabelEvaluation(s);
      labelEvals.add(le);
    }

    for (String s : result) {
      LabelEvaluation le = new LabelEvaluation(s);
      labelEvals.add(le);
    }

  }

  public CRFEvaluator(String id) {

    this.id = id;
    labelEvals = new HashSet<LabelEvaluation>();
  }

  public void addEvaluator(CRFEvaluator other) {
    
    for (LabelEvaluation lEval : other.getLabelEvals()) {

      if (this.labelEvals.contains(lEval)) {

        for (LabelEvaluation eval : this.labelEvals) {

          if (eval.equals(lEval)) {
            // combine the tps etc ...
            eval.setTp(eval.getTp() + lEval.getTp());
            eval.setFp(eval.getFp() + lEval.getFp());
            eval.setFn(eval.getFn() + lEval.getFn());

          }
        }

      }

      else {

        this.labelEvals.add(lEval);
      }
    }

  }

  public HashSet<LabelEvaluation> getLabelEvals() {
    return labelEvals;
  }

  public void setLabelEvals(HashSet<LabelEvaluation> labelEvals) {
    this.labelEvals = labelEvals;
  }

  public void evaluate() {

    
    for(int i =0;i<gold.length;i++){
      
      for (LabelEvaluation le : labelEvals) {
        // add tp or fp or fn if necessary
        le.addEvaluationPair(gold[i], result[i]);
      }
    }
  }

  public String getEvaluationScore() {

    StringBuilder sb = new StringBuilder(id + "\n");

    for (LabelEvaluation le : this.labelEvals) {
      sb.append(le.toString());
      sb.append("\n");
    }

    return sb.toString();
  }

}
