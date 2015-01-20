package de.uniwue.mk.malletcrf;

import java.io.File;

import de.uniwue.mk.kall.algorithm.SimpleTagger;

public class CRFTrainer {

  public static void train(File trainFile, File modelFile) throws Exception {

    SimpleTagger.main(new String[] { "--threads", "4", "--train", "true", "--model-file",
        modelFile.getAbsolutePath(), trainFile.getAbsolutePath() });




  }

}
