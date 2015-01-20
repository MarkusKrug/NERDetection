package de.uniwue.mk.malletcrf;

import java.io.File;

import de.uniwue.mk.kall.algorithm.SimpleTagger;

public class CRFApplication {

  public static String[] apply(File testFile, File modelFile) throws Exception {

    SimpleTagger.main(new String[] { "--train", "false", "--model-file",
        modelFile.getAbsolutePath(), testFile.getAbsolutePath() });

    return SimpleTagger.output.toString().split(" ");

  }

}
