package de.uniwue.mk.kall.featuresCompound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.uima.cas.CAS;

import de.uniwue.mk.kall.featuregeneratorPOS.AFeatureGenerator;
import de.uniwue.mkrug.kall.typesystemutil.Util_impl;
import de.uniwue.mkrug.nerapplication.TokenInstance;

public class InThesaurusFeatureGenerator extends AFeatureGenerator {

  private HashSet<String> lexicon;

  private String listName;

  public InThesaurusFeatureGenerator(InputStream is, String listname) throws IOException {

    this.lexicon = new HashSet<String>();
    this.listName = listname;

    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    String line = br.readLine();

    while (line != null) {

      for (String word : line.split(";")) {

        for (String subword : word.split(" ")) {

          // if (subword.trim().length() > 1) {
            this.lexicon.add(subword.trim().toLowerCase());
          // }
        }

      }
      line = br.readLine();
    }

  }

  @Override
  public String[] generateFeature(TokenInstance instance, CAS cas, Util_impl util) {

    String first = instance.getOriginal().substring(0, instance.getIndex());

    String last = instance.getOriginal().substring(instance.getIndex());

    String fBefore = "BeforeInThesaurus" + listName + "=";
    String fAfter = "AfterInThesaurus" + listName + "=";

    if (this.lexicon.contains(first.toLowerCase())) {

      fBefore += "true";
    }

    else {
      fBefore += "false";
    }

    if (this.lexicon.contains(last.toLowerCase())) {
      fAfter += "true";
    }

    else {

      fAfter += "false";
    }
    return new String[] { fBefore, fAfter };
  }

}
