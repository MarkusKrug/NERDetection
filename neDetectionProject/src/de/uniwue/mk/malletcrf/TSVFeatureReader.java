package de.uniwue.mk.malletcrf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class TSVFeatureReader {

  public static InstanceList readFeatures(File input, Pipe pipe) {
    InstanceList list = new InstanceList(pipe);
    try {
      List<String> lines = Files.readAllLines(input.toPath(), Charset.defaultCharset());

      String instanceId = "idInstance";
      int index = 0;
      for (String line : lines) {
        if (line.isEmpty())
          continue;

        String[] split = line.split(" ");

        StringBuilder featureBuilder = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
          featureBuilder.append(split[i]).append(" ");
        }

        String string = featureBuilder.toString();

        // replace all "=" with EQUALS
        string = string.replaceAll("=", "");

        Instance i = new Instance(string, split[split.length - 1], instanceId + index, null);

        list.addThruPipe(i);

        index++;
      }

      // list.addThruPipe(new LineGroupIterator(new FileReader(input), Pattern.compile("^\\s*$"),
      // true));

    } catch (IOException e) {
      System.out.println("Error reading file");
      e.printStackTrace();
    }
    return list;
  }



  // 1 training//datapoint per line, WS separated last entry is the class




}
