package de.uniwue.mk.kall.featuregeneratorPOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.apache.uima.cas.CAS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class W2VFeatureGen extends AFeatureGenerator {

  private HashMap<String, Integer> mapping;

  public W2VFeatureGen(String path) throws IOException {

    mapping = new HashMap<String, Integer>();

    List<String> lines = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());

    for (String line : lines) {
      mapping.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
    }

  }

  private String name;
  public W2VFeatureGen(InputStream in, String featureName) throws IOException {

    this.name = featureName;
    mapping = new HashMap<String, Integer>();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String line;
    while ((line = reader.readLine()) != null) {

      mapping.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
    }

  }




  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {
    if (mapping.containsKey(instance.getToken().getCoveredText())) {
      return new String[] { "W2VCluster" +name +"=" + mapping.get(instance.getToken().getCoveredText()) };
    }
    return new String[] { "W2VCluster" + name + "=" + "-1" };
  }

}
