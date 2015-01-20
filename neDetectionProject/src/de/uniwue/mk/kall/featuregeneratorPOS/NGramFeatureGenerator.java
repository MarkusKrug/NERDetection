package de.uniwue.mk.kall.featuregeneratorPOS;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class NGramFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    String text = instance.getToken().getCoveredText();

    List<String> nGramList = new ArrayList<String>();

    for (int startPosition = 0; startPosition < text.length() - 1; startPosition++) {

      for (int endposition = startPosition + 2; endposition <= text.length(); endposition++) {

        String substring = text.substring(startPosition, endposition);

        if (startPosition != 0) {
          substring = "-" + substring;
        }

        if (endposition != text.length()) {
          substring += "-";
        }
        if (substring.contains("-")) {
          nGramList.add("NGram=" + substring);
        }
      }
    }
    return nGramList.toArray(new String[0]);
  }

}
