package de.uniwue.mk.kall.featuresCompound;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

class GoldCompoundFeatureGenerator extends AFeatureGenerator {


  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {
    
    if (instance.getSplitList().contains(instance.getIndex())) {
      return new String[] { "B-Word" };
    }

    return new String[] { "O" };

  }

}
