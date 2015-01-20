package de.uniwue.mkrug.nerapplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kal.nerfeatures.WordShapeFeatureGenerator;
import de.uniwue.mk.kall.algorithm.Algorithm_Impl;
import de.uniwue.mk.kall.chunkfeatures.POSAtPosChunkFeatureGenerator;
import de.uniwue.mk.kall.chunkfeatures.SuffixGenerator;
import de.uniwue.mk.kall.chunkfeatures.WordAtPosChunkFeatureGenerator;
import de.uniwue.mk.kall.featuregeneratorPOS.FeatureGeneratorCollection;
import de.uniwue.mk.kall.featuregeneratorPOS.IsUpperCaseFeatureGenerator;
import de.uniwue.mk.kall.featuregeneratorPOS.SMORFFeatureGenerator;
import de.uniwue.mk.kall.featuregeneratorPOS.W2VFeatureGen;
import de.uniwue.mk.kall.featuregeneratorPOS.WordFeatureGenerator;
import de.uniwue.mk.kall.featuregeneratorPOS.WordNetFeatureGenerator;
import de.uniwue.mk.malletcrf.MaxEntClassifierApplication;

public class NamedEntityDetection {

  private File model;

  private File featuresFile;
  private FeatureGeneratorCollection coll;

  public NamedEntityDetection(File model, File featuresFile) throws IOException {
    this.model = model;

    // Read required ressources
    InputStream isw2vRomane = NamedEntityDetection.class
            .getResourceAsStream("w2v250Improved.txt");

    InputStream isNN = NamedEntityDetection.class
            .getResourceAsStream("nounCategories.txt");
    InputStream isVerb = NamedEntityDetection.class
            .getResourceAsStream("VerbCategories.txt");
    InputStream isAdj = NamedEntityDetection.class
            .getResourceAsStream("ADJCategories.txt");

    InputStream isSmor = NamedEntityDetection.class
            .getResourceAsStream("smorParsed.txt");

    coll = createCollection(isNN, isVerb, isAdj, isSmor, isw2vRomane);
    this.featuresFile = featuresFile;

  }

  private static FeatureGeneratorCollection createCollection(InputStream isNN, InputStream isVerb,
          InputStream isAdj, InputStream isSmor, InputStream isw2vRomane) throws IOException {
    FeatureGeneratorCollection coll = new FeatureGeneratorCollection();

    // word - dkpro compatible
    coll.addFeatureGenerator(new WordFeatureGenerator());

    // isUppercase compatible
    coll.addFeatureGenerator(new IsUpperCaseFeatureGenerator());

    // neighbouring tokens dkpro compatible
    coll.addFeatureGenerator(new WordAtPosChunkFeatureGenerator(1));
    coll.addFeatureGenerator(new WordAtPosChunkFeatureGenerator(-1));

    coll.addFeatureGenerator(new POSAtPosChunkFeatureGenerator(1));
    coll.addFeatureGenerator(new POSAtPosChunkFeatureGenerator(-1));
    coll.addFeatureGenerator(new POSAtPosChunkFeatureGenerator(0));

    // germanet and smor dkpro compatible
    coll.addFeatureGenerator(new WordNetFeatureGenerator(isNN, "nouns"));

    coll.addFeatureGenerator(new WordNetFeatureGenerator(isVerb, "verbs"));

    coll.addFeatureGenerator(new WordNetFeatureGenerator(isAdj, "adjs"));

    // SMOR


    coll.addFeatureGenerator(new SMORFFeatureGenerator(isSmor));

    // w2vec
    // word2vec Romane
    coll.addFeatureGenerator(new W2VFeatureGen(isw2vRomane, "Romane"));

    // ngramme
    // coll.addFeatureGenerator(new NGramFeatureGenerator());

    coll.addFeatureGenerator(new SuffixGenerator(3));

    // shape
    coll.addFeatureGenerator(new WordShapeFeatureGenerator());

    // gold

    // coll.addFeatureGenerator(new NERGoldFeatureGenerator());

    // TODO gold

    return coll;
  }

  public String[] tagSentence(TokenInstance[] instances, CAS cas) throws Exception {

    Algorithm_Impl algo = new Algorithm_Impl(cas, coll);

    algo.generateDKProFeatures(instances, this.featuresFile);

    String[] results = MaxEntClassifierApplication.apply(this.featuresFile, model);

    return results;

  }
}