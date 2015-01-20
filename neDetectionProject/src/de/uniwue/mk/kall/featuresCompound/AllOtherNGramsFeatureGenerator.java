package de.uniwue.mk.kall.featuresCompound;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class AllOtherNGramsFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    // all wordrests up to that point and After

    String original = instance.getOriginal();

    int index = instance.getIndex();

    List<String> ngramList = new ArrayList<String>();

    String before = "NGramBefore";
    String after = "NGramAfter";

    for (int i = 0; i < index; i++) {
      String sub = original.substring(i, index);

      // if (sub.length() <= 4) {
        ngramList.add(before + "=" + sub);
      // }
    }

    for (int i = index; i <= original.length(); i++) {
      String sub = original.substring(index, i);
      // if (sub.length() <= 4) {
        ngramList.add(after + "=" + sub);
      // }
    }
    return ngramList.toArray(new String[0]);
  }

}
