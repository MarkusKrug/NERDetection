# NERDetection
Project NE Detection as DK Pro Component

This (plain Java Project-no Maven - no Ant - no Eclipse plugin) can be used as a UIMA component to detect names in German
novels. It uses a MaxEnt-Classifier ( which showed to perform better than a Linear Chain CRF ) to do so.

Basic usage ( in accordance with DKPro):
==========================================

 public static void main(String[] args) throws Exception {
 
    CollectionReaderDescription cr = createReaderDescription(TextReader.class,
            TextReader.PARAM_PATH,
            "C:\\Users\\mrkug\\Desktop\\testDkPro\\Sack,-Gustav_Paralyse.txt",
            TextReader.PARAM_LANGUAGE, "de");

    AnalysisEngineDescription segmenter = createEngineDescription(OpenNlpSegmenter.class);

    AnalysisEngineDescription tagger = createEngineDescription(OpenNlpPosTagger.class);

    // ========PARAMS FOR THIS ANALYSIS ENGINE it requires to have POS-tags and Sentences!! ======

    String modelLocation = "resources\\modelNERRegular.bin";
    String featuresFile = "resources\\features.txt";

    AnalysisEngineDescription neDetection = createEngineDescription(RomaneNERAnnotator.class,
            RomaneNERAnnotator.PARAM_FEATURE_FILE_LOCATION, featuresFile,
            RomaneNERAnnotator.PARAM_MODEL_LOCATION, modelLocation);

    // =========

    AnalysisEngineDescription cc = createEngineDescription(CasDumpWriter.class,
            CasDumpWriter.PARAM_OUTPUT_FILE, "C:\\Users\\mrkug\\Desktop\\testDkPro\\Bier2.xmi");

    runPipeline(cr, segmenter, tagger, neDetection, cc);
  }


