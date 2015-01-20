package de.uniwue.mk.kall.featuregeneratorPOS;

import org.apache.uima.cas.CAS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class WordLEngthFeaturegenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    Integer length = instance.getToken().getCoveredText().length();

    return new String[] { "Wordlength=" + length.toString() };
  }

}
