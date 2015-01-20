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

public class WordNetFeatureGenerator extends AFeatureGenerator {

  HashMap<String, List<String>> map;

  private String id;

  public WordNetFeatureGenerator(String wnPath, String id) {

    this.id = id;
    try {
      map = readFile(wnPath);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public WordNetFeatureGenerator(InputStream in, String id) {

    this.id = id;
    try {
      map = readFile(in);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private HashMap<String, List<String>> readFile(String filePath) throws IOException {

    map = new HashMap<String, List<String>>();
    List<String> lines = Files.readAllLines(new File(filePath).toPath(), Charset.defaultCharset());

    for (String line : lines) {

      String[] sarr = line.split("\t");
      if (map.containsKey(sarr[0])) {

        List<String> list = map.get(sarr[0]);

        list.add(sarr[1]);
      }

      else {

        ArrayList<String> list = new ArrayList<String>();

        list.add(sarr[1]);
        map.put(sarr[0], list);

      }

    }
    return map;
  }

  private HashMap<String, List<String>> readFile(InputStream in) throws IOException {

    map = new HashMap<String, List<String>>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String line;
    while ((line = reader.readLine()) != null) {

 

      String[] sarr = line.split("\t");
      if (map.containsKey(sarr[0])) {

        List<String> list = map.get(sarr[0]);

        list.add(sarr[1]);
      }

      else {

        ArrayList<String> list = new ArrayList<String>();

        list.add(sarr[1]);
        map.put(sarr[0], list);

      }

    }
    return map;
  }



  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {
    AnnotationFS token = instance.getToken();
    if (map.containsKey(token.getCoveredText())) {

      List<String> list = map.get(token.getCoveredText());

      String s = "";

      List<String> gnFeats = new ArrayList<String>();

      for (String cat : list) {

        gnFeats.add(id + "=" + cat);
      }
      gnFeats.add(id + "=" + "true");

      return gnFeats.toArray(new String[0]);
    }

    else {

      return new String[] { id + "=false" };
    }
  }

}
