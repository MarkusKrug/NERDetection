package de.uniwue.mkrug.nerapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

public class RomaneNERAnnotator extends JCasAnnotator_ImplBase {

  NamedEntityDetection det;

  public static final String PARAM_MODEL_LOCATION = "modelLocation";

  @ConfigurationParameter(name = "modelLocation", mandatory = true, description = "Location from which the model is read.")
  public String modelLocation;

  public static final String PARAM_FEATURE_FILE_LOCATION = "featureFileLocation";

  @ConfigurationParameter(name = "featureFileLocation", mandatory = true, description = "Location of a txt File to temporarily store features")
  public String featuresFile;

  // requires a model, a textfile, and already annotated POS tags + sentence annotations
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    modelLocation = (String) aContext.getConfigParameterValue(PARAM_MODEL_LOCATION);
    featuresFile = (String) aContext.getConfigParameterValue(PARAM_FEATURE_FILE_LOCATION);
    try {
      this.det = new NamedEntityDetection(new File(modelLocation), new File(featuresFile));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    CAS cas = aJCas.getCas();
    Type sentenceType = cas.getTypeSystem().getType(
            "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence");
    Type posType = cas.getTypeSystem().getType(
            "de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS");
    Type namnedEntity = cas.getTypeSystem().getType(
            "de.tudarmstadt.ukp.dkpro.core.api.ner.type.Person");
    // for each sentence

    int index = 0;
    int begIndex = -1337;
    boolean inPerson = false;
    int endIndex = 0;
    int lastEndIndex = -1;
    for (AnnotationFS sentence : cas.getAnnotationIndex(sentenceType)) {
      index = 0;

      List<TokenInstance> sequence = new ArrayList<TokenInstance>();

      for (AnnotationFS token : CasUtil.selectCovered(posType, sentence)) {

        sequence.add(new TokenInstance(token));
      }
      String[] results = null;
      try {
        results = this.det.tagSentence(sequence.toArray(new TokenInstance[0]), cas);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // annotate



      for (AnnotationFS token : CasUtil.selectCovered(posType, sentence)) {

          // /Todo only in that sentence
          String result = results[index];

          if (result.contains("B-PER")) {

            if (inPerson == true) {
              // if it was already in a Chunk close that other chunk

              endIndex = lastEndIndex;
              // create annotation
              AnnotationFS fs = cas.createAnnotation(namnedEntity, begIndex, endIndex);

              cas.addFsToIndexes(fs);

            }
            inPerson = true;
            begIndex = token.getBegin();
          }

          else if (result.contains("O")) {

            if (inPerson) {

              // create Annotation
              endIndex = lastEndIndex;
              AnnotationFS fs = cas.createAnnotation(namnedEntity, begIndex, endIndex);

              cas.addFsToIndexes(fs);

            }
            inPerson = false;

          }
          lastEndIndex = token.getEnd();
          index++;
        }
    }

  }
}
