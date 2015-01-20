package de.uniwue.mk.kall.featuresCompound;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class PreviousCharacterGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    String origin = instance.getOriginal();

    String prev = "Neighbour";

    List<String> neihgbOursList = new ArrayList<String>();

    int index = 0;
    for (Character c : origin.toCharArray()) {

      int diff = index - instance.getIndex();

      if (diff != 0) {

        neihgbOursList.add(prev + diff + "=" + c.toString());
      }

      index++;

    }
    return neihgbOursList.toArray(new String[0]);
  }

}
