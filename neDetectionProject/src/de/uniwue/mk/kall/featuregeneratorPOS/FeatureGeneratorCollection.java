package de.uniwue.mk.kall.featuregeneratorPOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureGeneratorCollection {

  private List<AFeatureGenerator> collection;

  public FeatureGeneratorCollection(AFeatureGenerator... generators) {

    collection = new ArrayList<AFeatureGenerator>();

    for (AFeatureGenerator gen : generators) {
      collection.add(gen);
    }

  }

  public List<AFeatureGenerator> getCollection() {
    return collection;
  }

  public void setCollection(List<AFeatureGenerator> collection) {
    this.collection = collection;
  }

  public void addFeatureGenerator(AFeatureGenerator gen) {

    this.collection.add(gen);
  }

}
