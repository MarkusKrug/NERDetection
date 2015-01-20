package de.uniwue.mk.kall.featuregeneratorPOS;

import org.apache.uima.cas.CAS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class IsUpperCaseFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    boolean isUpp = Character.isUpperCase(instance.getToken().getCoveredText().charAt(0));

    if (isUpp) {
      return new String[] { "isUppercase=true" };
    }

    return new String[] { "isUppercase=false" };
  }

}
