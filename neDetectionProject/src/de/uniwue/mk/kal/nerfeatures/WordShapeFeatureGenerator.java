package de.uniwue.mk.kal.nerfeatures;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class WordShapeFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {
    String text = instance.getToken().getCoveredText();

    // transform

    String shape = "";
    for (Character c : text.toCharArray()) {
      if (Character.isDigit(c)) {
        shape += "D";

      }

      else if (Character.isLowerCase(c)) {
        shape += "x";
      }

      else if (Character.isUpperCase(c)) {
        shape += "X";
      }
    }
    return new String[] { "shape=" + shape };
  }

}
