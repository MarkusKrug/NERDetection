package de.uniwue.mk.kall.chunkfeatures;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class POSAtPosChunkFeatureGenerator extends AFeatureGenerator {

  private int pos;

  public POSAtPosChunkFeatureGenerator(int pos) {

    this.pos = pos;
  }

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    // get Covering Sentence
    Type sentenceType = cas.getTypeSystem().getType(
            "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence");
    AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(sentenceType);

    AnnotationFS covSent = null;
    for (AnnotationFS sent : annotationIndex) {

      if (sent.getBegin() <= instance.getToken().getBegin()
              && sent.getEnd() >= instance.getToken().getEnd()) {
        covSent = sent;
        break;
      }

    }

    int posTemp = pos;
    AnnotationFS annoAtPos = instance.getToken();
    if (pos > 0) {

      while (posTemp > 0 && annoAtPos != null) {
        annoAtPos = util.getNextAnnotation(annoAtPos);
        posTemp--;
      }
    }

    else {

      while (posTemp < 0 && annoAtPos != null) {
        annoAtPos = util.getPreviousAnnotation(annoAtPos);
        posTemp++;
      }

    }
    String feature = "POSAtPosition_" + pos + "=";
    
    if(annoAtPos==null){
      feature += "null";
      
      return new String[] { feature };
    }

    else if (!util.isCoveredBy(annoAtPos, covSent)) {
      // if its not in the same sentence => null
      feature += "null";

      return new String[] { feature };
    }

    return new String[] { (feature + annoAtPos.getFeatureValueAsString(annoAtPos.getType()
            .getFeatureByBaseName("PosValue")))

    };
  }

}
