package de.uniwue.mk.kall.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.cas.text.AnnotationFS;
import org.xml.sax.SAXException;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mk.kall.featuregeneratorPOS.FeatureGeneratorCollection;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class Algorithm_Impl {

  private CAS cas;

  private File folder;

  private File featureFile;

  private FeatureGeneratorCollection featureCollection;

  private List<TokenInstance> instances;

  private Util_impl util;

  public Algorithm_Impl(File folder, File featureFile, FeatureGeneratorCollection coll) {

    this.cas = Util_impl.createCas();
    this.folder = folder;
    this.featureFile = featureFile;
    this.featureCollection = coll;

    // delete Featurefile
    this.featureFile.delete();

    // create Instances

  }

  public Algorithm_Impl(CAS cas, FeatureGeneratorCollection coll) {
    this.cas = cas;
    this.featureCollection = coll;
  }


  public void generateChunkFeaturesFrom1File(File toFile, File f, boolean dummies, boolean append)
          throws IOException, SAXException {

    // that file is "f"
    // always append
    FileWriter fw = new FileWriter(toFile, append);

    // TODO

    cas.reset();
    XmiCasDeserializer.deserialize(new FileInputStream(f), cas, true);

    util = new Util_impl(cas);

    // for each sentence

    for (AnnotationFS sent : cas.getAnnotationIndex(util.getSentenceType())) {

      if (dummies) {
        // AddDummy
        fw.append("DummyStart S").append("\n");
      }
      // get all Tokens within that sentence

      for (AnnotationFS token : util.getCovered(sent, util.getPOSType())) {

        // generate all Features for this token

        for (AFeatureGenerator gen : this.featureCollection.getCollection()) {

          String[] features = gen.generateFeature(new TokenInstance(token), cas, util);

          // append them

          for (String feature : features) {

            fw.append(feature + " ");
          }
        }

        fw.append("\n");
      }
      if (dummies) {
        fw.append("DummyEnd ES").append("\n");
      }
      fw.append("\n");
    }

    fw.flush();
    fw.close();

  }

  public void generateChunkFeatures(File toFile, boolean dummies) throws IOException, SAXException {

    // always append
    FileWriter fw = new FileWriter(featureFile, true);

    // TODO

    for (File f : folder.listFiles()) {
      if (!f.getName().endsWith(".xmi")) {
        continue;
      }

      cas.reset();
      XmiCasDeserializer.deserialize(new FileInputStream(f), cas, true);

      util = new Util_impl(cas);

      // for each sentence

      for (AnnotationFS sent : cas.getAnnotationIndex(util.getSentenceType())) {

        if (dummies) {
          // AddDummy
          fw.append("DummyStart S").append("\n");
        }

        // get all Tokens within that sentence

        for (AnnotationFS token : util.getCovered(sent, util.getPOSType())) {

          // generate all Features for this token

          for (AFeatureGenerator gen : this.featureCollection.getCollection()) {

            String[] features = gen.generateFeature(new TokenInstance(token), cas, util);

            // append them

            for (String feature : features) {

              fw.append(feature + " ");
            }
          }

          fw.append("\n");
        }

        if (dummies) {
          fw.append("DummyEnd ES").append("\n");
        }
        fw.append("\n");
      }

    }

    fw.flush();
    fw.close();

  }

  public void generateCompoundFeatures(File featuresFile) throws Exception {

    // always append
    FileWriter fw = new FileWriter(featureFile, true);

    // TODO

    for (File f : folder.listFiles()) {
      if (!f.getName().endsWith(".txt")) {
        continue;
      }

      // read the file

      List<String> lines = new ArrayList<String>();

      // BufferedReader in = new BufferedReader(
      // new InputStreamReader(new FileInputStream(f), "Cp1252"));
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));

      for (String x = in.readLine(); x != null; x = in.readLine()) {

        lines.add(x);

      }

      in.close();
      // each line represents 1 word

      for (String word : lines) {

        String original = word.replaceAll(" ", "");

        // get all Split indexes
        List<Integer> splitIndexes = new ArrayList<Integer>();

        int index = 0;
        for (Character c : word.toCharArray()) {

          if (c == ' ') {
            splitIndexes.add(index);
            // spaces dont count as charaters
            index--;
          }
          index++;
        }

        // a sequence consists of all the characters of the word

        int charIndex = 0;
        for (Character c : original.toCharArray()) {
          // generate all Features for this token

          for (AFeatureGenerator gen : this.featureCollection.getCollection()) {

            boolean split = false;
            if (splitIndexes.contains(charIndex - 1)) {
              split = true;
            }
            String[] features = gen.generateFeature(new TokenInstance(c.toString(), split,
                    splitIndexes, original, charIndex), cas, util);

            // append them

            for (String feature : features) {

              fw.append(feature + " ");
            }
          }
          // end of all features
          fw.append("\n");
          charIndex++;
        }
        // end of sequence
        fw.append("\n");
      }

    }

    fw.flush();
    fw.close();

  }

  public void generateDKProFeatures(TokenInstance[] instances, File featuresFile)
          throws IOException {

    // generate all required features for NER recognition into the FIle featureFIle

    FileWriter fw = new FileWriter(featuresFile, false);


    // a sequence consists of all the characters of the word

    for (TokenInstance instance : instances) {

      for (AFeatureGenerator gen : featureCollection.getCollection()) {

        String[] features = gen.generateFeature(instance, cas, new Util_impl(cas));
        
        for (String feature : features) {

          fw.append(feature + " ");
        }
      }
      // end of all features
      fw.append("\n");
    }



    fw.flush();
    fw.close();

  }
}
