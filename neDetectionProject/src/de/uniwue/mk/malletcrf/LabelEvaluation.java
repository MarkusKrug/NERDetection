package de.uniwue.mk.malletcrf;

public class LabelEvaluation {

  // this class has the required statistics for 1 Label

  private int tp;

  private int fp;

  private int fn;


  private String label;

  public LabelEvaluation(String label) {

    this.label = label;
  }

  public void addEvaluationPair(String gold, String compare) {

    if (gold.equals(label) && compare.equals(label)) {
      tp++;
    }

    else if (!gold.equals(label) && compare.equals(label)) {
      fp++;

    }

    else if (gold.equals(label) && !compare.equals(label)) {
      fn++;
    }
  }

  public int getTp() {
    return tp;
  }

  public void setTp(int tp) {
    this.tp = tp;
  }

  public int getFp() {
    return fp;
  }

  public void setFp(int fp) {
    this.fp = fp;
  }

  public int getFn() {
    return fn;
  }

  public void setFn(int fn) {
    this.fn = fn;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public double getF1() {

    double r = getRecall();
    double p = getPrecision();

    if (r == 0 || p == 0) {
      return 0;
    }

    return (2 * r * p / (r + p));
  }

  public double getRecall() {
    if (tp == 0)
      return 0.0;

    return (tp / (0.0 + tp + fn));
  }

  public double getPrecision() {
    if (tp == 0)
      return 0.0;

    return (0.0 + tp / (0.0 + tp + fp));
  }

  public String toString(){
    
    String s = "";
    
    // tps und so
    s += "Label: " + label + "\n";
    s += "True-positivies: " + tp + "\n";
    s += "False-negatives: " + fn + "\n";
    s += "False-positives: " + fp + "\n";

    // scores

    s += "Recall: " + getRecall() + "\n";
    s += "Precision: " + getPrecision() + "\n";
    s += "F1-Score: " + getF1() + "\n";

    return s;
    
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LabelEvaluation other = (LabelEvaluation) obj;
    if (label == null) {
      if (other.label != null)
        return false;
    } else if (!label.equals(other.label))
      return false;
    return true;
  }

}
