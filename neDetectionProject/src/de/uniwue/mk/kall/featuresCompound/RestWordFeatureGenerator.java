package de.uniwue.mk.kall.featuresCompound;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class RestWordFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    String first = instance.getOriginal().substring(0, instance.getIndex());

    String last = instance.getOriginal().substring(instance.getIndex());
    return new String[] { "FirstPart=" + first, "LastPart=" + last };
  }

}
