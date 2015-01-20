package de.uniwue.mk.kall.chunkfeatures;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class GoldChunkFeatureGenerator extends AFeatureGenerator {

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    Type goldChunk = cas.getTypeSystem().getType("NounPhraseDetection.GoldNps");

    for (AnnotationFS a : cas.getAnnotationIndex(goldChunk)) {

      if (a.getBegin() <= instance.getToken().getBegin()
              && a.getEnd() >= instance.getToken().getEnd()) {

        // it is covered !

        if (a.getBegin() == instance.getToken().getBegin()) {
          return new String[] { "B-NP" };
        }

        else {
          return new String[] { "I-NP" };
        }
      }
    }

    return new String[] { "O" };
  }

}
