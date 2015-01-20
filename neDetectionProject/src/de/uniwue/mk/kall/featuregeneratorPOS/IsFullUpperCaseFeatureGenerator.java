package de.uniwue.mk.kall.featuregeneratorPOS;

import org.apache.uima.cas.CAS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class IsFullUpperCaseFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    Boolean fullUpper = true;

    for (Character c : instance.getToken().getCoveredText().toCharArray()) {

      if (!Character.isUpperCase(c)) {
        fullUpper = false;
        break;
      }
    }

    return new String[] { ("FullUpperCase=" + fullUpper.toString()) };
  }

}
