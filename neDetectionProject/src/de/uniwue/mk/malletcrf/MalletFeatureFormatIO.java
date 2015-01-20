package de.uniwue.mk.malletcrf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MalletFeatureFormatIO {

  // cna only read the Instances again

  public static List<MalletSequence> readMalletFeatures(File featureFile) throws IOException {

    List<MalletSequence> sequences = new ArrayList<MalletSequence>();

    List<String> lines = Files.readAllLines(featureFile.toPath(), Charset.defaultCharset());

    List<String> features = new ArrayList<String>();

    for (String line : lines) {

      if (!line.isEmpty()) {

        features.add(line);

      }

      else {

        // create Instance
        MalletSequence seq = new MalletSequence(features);
        sequences.add(seq);
        features = new ArrayList<String>();
      }

    }
    return sequences;

  }

  public static void SequencesToFile(List<MalletSequence> sequences, File toFile)
          throws IOException {

    FileWriter fw = new FileWriter(toFile);

    for (MalletSequence seq : sequences) {

      for (String feat : seq.getFeatures()) {
        fw.append(feat).append("\n");
      }

      fw.append("\n");
    }

    fw.flush();
    fw.close();

  }

  public static List<CrossFold> readFeaturesToFold(File featureFile, int numberOfFolds,
          boolean random) throws IOException {

    List<CrossFold> folds = new ArrayList<CrossFold>();

    List<MalletSequence> sequences = readMalletFeatures(featureFile);

    // random ???

    if (random) {
      // always use the same Seed to guaranteed reproducability
      Collections.shuffle(sequences, new Random(13374211));
    }

    // create all the folds

    int testSize = sequences.size() / numberOfFolds;

    int begTestInstances = 0;

    // for each wanted fold
    for (int k = 0; k < numberOfFolds; k++) {
      List<MalletSequence> testSequences = new ArrayList<MalletSequence>();
      List<MalletSequence> trainSequences = new ArrayList<MalletSequence>();

      // for each instance
      for (int i = 0; i < sequences.size(); i++) {

        // fill the instances into the fold

        if (i >= begTestInstances && i < begTestInstances + testSize) {

          testSequences.add(sequences.get(i));

        }

        else {
          trainSequences.add(sequences.get(i));
        }

      }

      // update the testindex
      begTestInstances += testSize;

      // create a fold

      CrossFold fold = new CrossFold(trainSequences, testSequences);
      folds.add(fold);
    }
    return folds;

  }

  public static List<CrossFold> readFeaturesToFoldWithSpecifiedSize(File featureFile,
          int numberOfFolds, boolean random, int maxTotalSize) throws IOException {

    // maxTotalSize is in tokens and not in sequences !!
    List<CrossFold> folds = new ArrayList<CrossFold>();

    List<MalletSequence> sequences = readMalletFeatures(featureFile);

    // random ???

    if (random) {
      // always use the same Seed to guaranteed reproducability
      Collections.shuffle(sequences, new Random(13374211));
    }

    sequences = reduceToMaxAllowedSize(sequences, maxTotalSize);

    // create all the folds

    int testSize = sequences.size() / numberOfFolds;

    int begTestInstances = 0;

    // for each wanted fold
    for (int k = 0; k < numberOfFolds; k++) {
      List<MalletSequence> testSequences = new ArrayList<MalletSequence>();
      List<MalletSequence> trainSequences = new ArrayList<MalletSequence>();

      // for each instance
      for (int i = 0; i < sequences.size(); i++) {

        // fill the instances into the fold

        if (i >= begTestInstances && i < begTestInstances + testSize) {

          testSequences.add(sequences.get(i));

        }

        else {
            trainSequences.add(sequences.get(i));
        }

      }

      // update the testindex
      begTestInstances += testSize;

      // create a fold

      CrossFold fold = new CrossFold(trainSequences, testSequences);
      folds.add(fold);
    }
    return folds;
  }

  private static List<MalletSequence> reduceToMaxAllowedSize(List<MalletSequence> sequences,
          int maxTotalSize) {

    List<MalletSequence> sequencesNew = new ArrayList<MalletSequence>();

    int insertedInstances = 0;
    for (MalletSequence seq : sequences) {

      if (insertedInstances < maxTotalSize) {
        sequencesNew.add(seq);

      }

      else {
        break;
      }

      insertedInstances += seq.features.size();

    }
    return sequencesNew;
  }

}
