package de.uniwue.mk.kall.chunkfeatures;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class SuffixGenerator extends AFeatureGenerator {

  private int size;

  public SuffixGenerator(int size) {

    this.size = size;
  }

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    List<String> sufixFeatures = new ArrayList<String>();
    String text = instance.getToken().getCoveredText();
    String feature = "Suffix";
    for (int i = 1; i <= size; i++) {
      if (text.length() - i > 0) {
        String suffix = text.substring(text.length() - i);

        sufixFeatures.add(feature + i + "=" + suffix);
      }

    }

    return sufixFeatures.toArray(new String[0]);
  }

}
