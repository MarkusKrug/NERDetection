package de.uniwue.mk.kall.featuregeneratorPOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;

import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class SMORFFeatureGenerator extends AFeatureGenerator {

  HashMap<String, String> smorMap;

  private HashMap<String, Integer> mapping;
  public SMORFFeatureGenerator() throws IOException {

    smorMap = new HashMap<String, String>();
    List<String> readAllLines = Files.readAllLines(new File("resources/smorParsed.txt").toPath(),
            Charset.defaultCharset());

    for (String line : readAllLines) {

      String[] split = line.split(" ");

      String value = "";
      for (int i = 2; i < split.length; i++) {

        value += split[i] + " ";
      }

      smorMap.put(split[1], value);

    }

    mapping = new HashMap<String, Integer>();


    List<String> lines = Files.readAllLines(new File("resources/w2v250Improved.txt").toPath(),
            Charset.defaultCharset());

    for (String line : lines) {
      mapping.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
    }
  }

  public SMORFFeatureGenerator(InputStream in) throws IOException {

    smorMap = new HashMap<String, String>();

    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String line;
    while ((line = reader.readLine()) != null) {

      String[] split = line.split(" ");

      String value = "";
      for (int i = 2; i < split.length; i++) {

        value += split[i] + " ";
      }

      smorMap.put(split[1], value);

    }

    mapping = new HashMap<String, Integer>();

  }



  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    AnnotationFS token = instance.getToken();

    if (smorMap.containsKey(token.getCoveredText())) {

      String string = smorMap.get(token.getCoveredText());

      String[] parts = string.split(" ");

      // also add the w2v cluster for those words if they exist ???

      List<Integer> w2vCluster = new ArrayList<Integer>();

      for (String part : parts) {

        if (mapping.containsKey(part)) {
          w2vCluster.add(mapping.get(part));
        }
      }

      // int arrLen = w2vCluster.size() + parts.length;
      int arrLen = parts.length;
      String[] returner = new String[arrLen];
      for (int i = 0; i < parts.length; i++) {
        returner[i] = "SMOR" + "=" + parts[i];
        ;

      }

      // for (int i = parts.length; i < arrLen; i++) {
      // returner[i] = "SMORW2V=" + String.valueOf(w2vCluster.get(i - parts.length));
      // }

      // for (int i = 0; i < parts.length; i++) {
      //
      // parts[i] = "SMOR" + "=" + parts[i];
      // }

      return returner;

    }

    return new String[] { "SMOR=null" };

  }

}

