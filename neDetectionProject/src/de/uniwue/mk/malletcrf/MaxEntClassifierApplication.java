package de.uniwue.mk.malletcrf;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import cc.mallet.classify.Classification;

public class MaxEntClassifierApplication {

  public static String[] apply(File testFile, File modelFile) throws Exception {

    MalletIO ioHandler = new MalletIO();
    // read the Model from modelFile
    cc.mallet.classify.MaxEnt model = (cc.mallet.classify.MaxEnt) ioHandler.deserialize(modelFile);


    List<String> resultList = new ArrayList<String>();

    List<String> lines = Files.readAllLines(testFile.toPath(), Charset.defaultCharset());

    for (String line : lines) {
      if (line.isEmpty())
        continue;

      String[] split = line.split(" ");

      StringBuilder featureBuilder = new StringBuilder();
      for (int i = 0; i < split.length - 1; i++) {
        featureBuilder.append(split[i]).append(" ");
      }

      // classify

      String instance = featureBuilder.toString().replaceAll("=", "");
      Classification classify = model.classify(instance);
      resultList.add(classify.getLabeling().getBestLabel().toString());

    }

    return resultList.toArray(new String[0]);

  }

}
