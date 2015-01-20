package de.uniwue.mk.malletcrf;

import java.io.File;

import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.pipe.Pipe;
import cc.mallet.types.InstanceList;

public class MaxEntClassifierTrainer {

  public static void train(File trainFile, File modelFile) throws Exception {

    IPipeInitializer initializer = new ClassificationPipeInitializer();
    Pipe pipe = initializer.initializePipe();
    MalletIO ioHandler = new MalletIO();
    MaxEntTrainer trainer = new MaxEntTrainer();

    InstanceList instances = TSVFeatureReader.readFeatures(trainFile, pipe);

    cc.mallet.classify.MaxEnt model = trainer.train(instances);

    ioHandler.serializeModel(model, modelFile.getAbsolutePath());

  }

}
