package de.uniwue.mk.malletcrf;

import java.io.File;

import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.pipe.Pipe;
import cc.mallet.types.InstanceList;

public class DecisionTreeClassifierTrainer {

  public static void train(File trainFile, File modelFile) throws Exception {

    IPipeInitializer initializer = new ClassificationPipeInitializer();
    Pipe pipe = initializer.initializePipe();
    MalletIO ioHandler = new MalletIO();
    DecisionTreeTrainer trainer = new DecisionTreeTrainer();

    InstanceList instances = TSVFeatureReader.readFeatures(trainFile, pipe);

    cc.mallet.classify.DecisionTree model = trainer.train(instances);

    ioHandler.serializeModel(model, modelFile.getAbsolutePath());

  }

}
