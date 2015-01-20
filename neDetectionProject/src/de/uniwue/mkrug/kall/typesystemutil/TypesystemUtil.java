package de.uniwue.mkrug.kall.typesystemutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

/*
 * to add further methods just extend a class from this abstract class and add more methods
 */

public abstract class TypesystemUtil {

  /*
   * this whole project is just dedicated to provide a huge amount of helper methods
   */

  // has a cheap constructor - at least as cheap as possible

  private CAS cas;

  private long docLen;

  public TypesystemUtil(CAS cas) {

    this.cas = cas;

    this.docLen = cas.getDocumentText().length();

  }

  // ========UTIL FOR TYPE AND FEATURE ACCESS===========

  public Type getChapterType() {

    return cas.getTypeSystem().getType(TypesystemConstants.CHAPTER_TYPE);
  }

  public Type getParagraphType() {

    return cas.getTypeSystem().getType(TypesystemConstants.PARAGRAPH_TYPE);
  }

  public Feature getEnumerationFeature() {

    return getChapterType().getFeatureByBaseName(TypesystemConstants.CHAPTER_ENUEMRATION);
  }

  public Type getNamedEntityType() {

    return cas.getTypeSystem().getType(TypesystemConstants.NE_TYPE);
  }

  public Feature getNEFeatureCore() {
    return getNamedEntityType().getFeatureByBaseName(TypesystemConstants.NE_FEAT_CORE);
  }

  public Feature getNEFeatureNumerus() {
    return getNamedEntityType().getFeatureByBaseName(TypesystemConstants.NE_FEAT_NUMERUS);
  }

  public Type getSFordEntityType() {

    return cas.getTypeSystem().getType(TypesystemConstants.STANFORDNE_TYPE);
  }

  public Type getDirectSpeechType() {

    return cas.getTypeSystem().getType(TypesystemConstants.DIRECTSPEECH_TYPE);
  }

  public Type getGrammarType() {

    return cas.getTypeSystem().getType(TypesystemConstants.GRAMMAR_TYPE);
  }

  public Feature getGrammarFeature() {
    return getGrammarType().getFeatureByBaseName(TypesystemConstants.GRAMMARFUNCTION_FEATURE);
  }

  public Type getMorphType() {

    return cas.getTypeSystem().getType(TypesystemConstants.MORPH_TYPE);
  }

  public Feature getMorphGender() {
    return getMorphType().getFeatureByBaseName(TypesystemConstants.MORPH_FEAT_GENDER);
  }

  public Feature getSFFeature() {
    return getSFordEntityType().getFeatureByBaseName(TypesystemConstants.STANFORDNE_FEAT_TYPE);
  }

  public Feature getChunkFeature() {
    return getChunkType().getFeatureByBaseName(TypesystemConstants.CHUNK_FEAT_TYPE);
  }

  public Type getSentencePartType() {

    return cas.getTypeSystem().getType(TypesystemConstants.SENTENCE_PART_TYPE);
  }

  public Feature getSentencePartFeature() {
    return getSentencePartType().getFeatureByBaseName(
            TypesystemConstants.SENTENCE_PART_TYPE_FEATURE);
  }

  public Feature getMorphPerson() {
    return getMorphType().getFeatureByBaseName(TypesystemConstants.MORPH_FEAT_PERSON);
  }

  public Feature getMorphNumerus() {
    return getMorphType().getFeatureByBaseName(TypesystemConstants.MORPH_FEAT_NUMBER);
  }

  public Type getDependencyType() {

    return cas.getTypeSystem().getType(TypesystemConstants.DEP_TYPE);
  }
  
  public Type getSFParseType() {

    return cas.getTypeSystem().getType(TypesystemConstants.STANFORD_PARSE_TYPE);
  }

  public Feature getSFParseFeature() {
    return getSFParseType().getFeatureByBaseName(TypesystemConstants.STANFORD_PARSE_FEATURE);
  }

  public Feature getHeadWordFeature() {
    return getDependencyType().getFeatureByBaseName(TypesystemConstants.DEP_FEAT_HEADNAME);
  }

  public Feature getDSId() {
    return getDirectSpeechType().getFeatureByBaseName(TypesystemConstants.DIRECTSPEECH_FEAT_ID);
  }

  public Feature getNEId() {
    return getNamedEntityType().getFeatureByBaseName(TypesystemConstants.NE_FEAT_ID);
  }

  public Feature getWordNumerFeature() {
    return getDependencyType().getFeatureByBaseName(TypesystemConstants.DEP_FEAT_WORDNUMBER);
  }

  public Feature depRelFeature() {
    return getDependencyType().getFeatureByBaseName(TypesystemConstants.DEP_FEAT_DEPREL);
  }

  public Type getMentionTypeType() {

    return cas.getTypeSystem().getType(TypesystemConstants.MENTION_TYPE);
  }

  public Feature getMentionTypeGender() {
    return getMentionTypeType().getFeatureByBaseName(TypesystemConstants.MENTION_FEAT_GENDER);
  }

  public Feature getMentionTypeNumerus() {
    return getMentionTypeType().getFeatureByBaseName(TypesystemConstants.MENTION_FEAT_NUMERUS);
  }

  public Feature getMentionTypeMentionType() {
    return getMentionTypeType().getFeatureByBaseName(TypesystemConstants.MENTION_FEAT_MENTIONTYPE);
  }

  public Feature getMentionTypePerson() {
    return getMentionTypeType().getFeatureByBaseName(TypesystemConstants.MENTION_FEAT_PERSON);
  }

  public Type getSentenceType() {

    return cas.getTypeSystem().getType(TypesystemConstants.SENT_TYPE);
  }

  public Type getNERuleType() {

    return cas.getTypeSystem().getType(TypesystemConstants.RULEALGO_NAMEDENTITY);
  }

  public Type getSceneType() {

    return cas.getTypeSystem().getType(TypesystemConstants.SCENE_TYPE);
  }

  public Feature getSceneFeature() {
    return getSceneType().getFeatureByBaseName(TypesystemConstants.SCENE_FEAT_TYPE);
  }

  public Type getPOSType() {

    return cas.getTypeSystem().getType(TypesystemConstants.POS_TYPE);
  }

  public Feature getLemmaFeature() {

    return getPOSType().getFeatureByBaseName(TypesystemConstants.POS_FEAT_LEMMA);

  }

  public Feature getPOSTagFeature() {

    return getPOSType().getFeatureByBaseName(TypesystemConstants.POS_FEAT_POS);

  }

  public Type getChunkType() {

    return cas.getTypeSystem().getType(TypesystemConstants.CHUNK_TYPE);
  }

  // ===============END UTIL FOR TYPE ACCESS===============

  // ====UTIL FOR THE ACCESS OF COVERED ANNOTATIONS=========

  // this method is extremely important - this has to be as fast as possible since its used alot
  // idea by Peter , use iterator.moveTo
  public List<AnnotationFS> getCovered(AnnotationFS cover, Type t) {

    List<AnnotationFS> covered = new ArrayList<AnnotationFS>();

    
    int beg = (cover.getBegin() - 1) > 0 ? cover.getBegin() : cover.getBegin();

    int end = (cover.getEnd() + 1 > docLen) ? cover.getEnd() : cover.getEnd();

    AnnotationFS dummyAnno = cas.createAnnotation(t, beg, end);

    FSIterator<AnnotationFS> subiterator = cas.getAnnotationIndex(t).subiterator(dummyAnno);

    while (subiterator.hasNext()) {
      covered.add(subiterator.next());
    }

    return covered;

  }

  // public List<AnnotationFS> getCovered(AnnotationFS cover, Type t) {
  //
  // List<AnnotationFS> covered = new ArrayList<AnnotationFS>();
  //
  // FSIterator<AnnotationFS> subiterator = cas.getAnnotationIndex(t).subiterator(cover);
  //
  // while (subiterator.hasNext()) {
  // covered.add(subiterator.next());
  // }
  //
  // return covered;
  //
  // }

  public List<AnnotationFS> getCoveredSlow(AnnotationFS cover, Type t) {

    List<AnnotationFS> covered = new ArrayList<AnnotationFS>();

    for (AnnotationFS a : cas.getAnnotationIndex(t)) {

      if (isCoveredBy(a, cover)) {
        covered.add(a);
      }
    }

    return covered;

  }

  public boolean haveEqualSpans(AnnotationFS one, AnnotationFS two) {

    if (one.getBegin() == two.getBegin() && one.getEnd() == two.getEnd()) {
      return true;
    }

    return false;

  }

  /*
   * is test smaller (or equal) in size than possibleCover?
   */
  public boolean isCoveredBy(AnnotationFS test, AnnotationFS possibleCover) {

    if (test.getBegin() >= possibleCover.getBegin() && test.getEnd() <= possibleCover.getEnd()) {
      return true;
    }

    return false;

  }

  public boolean isInsideOf(AnnotationFS test, AnnotationFS possibleCover) {
    if (test.getBegin() > possibleCover.getBegin() && test.getEnd() < possibleCover.getEnd()) {
      return true;
    }

    return false;
  }

  // ==============UTIL FOR THE ACCESS OF PREVIOUS AND NEXT ANNOTATIONS=========

  public AnnotationFS getPreviousAnnotation(AnnotationFS anno) {

    if (anno == null) {
      return null;
    }

    FSIterator<AnnotationFS> iterator = cas.getAnnotationIndex(anno.getType()).iterator();

    iterator.moveTo(anno);

    iterator.moveToPrevious();

    if (iterator.isValid()) {
      // System.out.println(anno.getBegin() + "\t" + iterator.get().getBegin());
      return iterator.get();
    }

    return null;

  }

  public List<AnnotationFS> getAllPreviousAnnotations(AnnotationFS anno) {

    // of the same type as anno

    List<AnnotationFS> previousList = new ArrayList<AnnotationFS>();

    for (AnnotationFS a : cas.getAnnotationIndex(anno.getType())) {
      if (a.getBegin() < anno.getBegin())
        previousList.add(a);
    }

    return previousList;
  }

  public AnnotationFS getNextAnnotation(AnnotationFS anno) {

    if (anno == null) {
      return null;
    }

    FSIterator<AnnotationFS> iterator = cas.getAnnotationIndex(anno.getType()).iterator();

    iterator.moveTo(anno);

    iterator.moveToNext();

    if (iterator.isValid()) {
      // System.out.println(anno.getBegin() + "\t" + iterator.get().getBegin());
      return iterator.get();
    }

    return null;

  }

  public AnnotationFS getNextAnnotationSlow(AnnotationFS anno) {

    if (anno == null) {
      return null;
    }

    boolean ret = false;
    for (AnnotationFS a : cas.getAnnotationIndex(anno.getType())) {

      if (ret) {
        return a;
      }

      if (a.getBegin() == anno.getBegin()) {
        ret = true;
      }

    }

    return null;

  }

  private int getIndexOfAnnotation(AnnotationFS anno) {
    int i = 0;
    for (AnnotationFS a : cas.getAnnotationIndex(anno.getType())) {
      if (a.equals(anno)) {
        return i;
      }
      i++;
    }

    return -1;
  }

  public List<AnnotationFS> getAllFollowingAnnotations(AnnotationFS anno) {

    // of the same type as anno TODO slow
    List<AnnotationFS> followingList = new ArrayList<AnnotationFS>();

    for (AnnotationFS a : cas.getAnnotationIndex(anno.getType())) {

    }

    return null;
  }

  public int getIndexOfTypeInDocument(AnnotationFS anno) {
    int index = 0;

    for (AnnotationFS a : cas.getAnnotationIndex(anno.getType())) {
      if (haveEqualSpans(anno, a)) {
        return index;
      }
      index++;
    }
    // should not happen...
    return Integer.MAX_VALUE;
  }

  // ===================END UTIL FOR THE ACCESS OF PREVIOUS AND NEXT=====

  // ==========UTIL FOR THE USE OF NAMEDENTITIES================

  // test whether an annotation represents a namedentity (tests the coveredText)

  public boolean isNamedEntity(AnnotationFS token) {

    boolean isNamedEntity = false;
    for (AnnotationFS name : cas.getAnnotationIndex(getNamedEntityType())) {

      if (name.getBegin() <= token.getBegin() && name.getEnd() >= token.getEnd()) {
        isNamedEntity = true;
        break;
      }
    }

    return isNamedEntity;

  }

  public String getGender(AnnotationFS anno) {

    // TODO
    return null;

  }

  // ================UTIL FOR THE USE OF NAMEDENTITIES========

  // ==UTIL FOR THE USE OF SENTENCES==============

  public AnnotationFS getCoveringSentence(AnnotationFS anno) {

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {

      // if (sent.getBegin() > anno.getEnd()) {
      // return null;
      // }

      if (sent.getBegin() <= anno.getBegin() && sent.getEnd() >= anno.getEnd()) {
        return sent;
      }
    }

    // get the sentence which coveres most of that annotation

    AnnotationFS covSent = null;
    int cov = 0;

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {

      int tempEnd = sent.getEnd() <= anno.getEnd() ? sent.getEnd() : anno.getEnd();
      int tempBeg = sent.getBegin() >= anno.getBegin() ? sent.getBegin() : anno.getBegin();

      if (tempEnd - tempBeg > cov) {
        cov = tempEnd - tempBeg;
        covSent = sent;
      }

    }

    return covSent;

  }

  public AnnotationFS getCoveringSentencePart(AnnotationFS anno) {

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentencePartType())) {

      // if (sent.getBegin() > anno.getEnd()) {
      // return null;
      // }

      if (sent.getBegin() <= anno.getBegin() && sent.getEnd() >= anno.getEnd()) {
        return sent;
      }
    }

    // get the sentence which coveres most of that annotation

    AnnotationFS covSent = null;
    int cov = 0;

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentencePartType())) {

      int tempEnd = sent.getEnd() <= anno.getEnd() ? sent.getEnd() : anno.getEnd();
      int tempBeg = sent.getBegin() >= anno.getBegin() ? sent.getBegin() : anno.getBegin();

      if (tempEnd - tempBeg > cov) {
        cov = tempEnd - tempBeg;
        covSent = sent;
      }

    }

    return covSent;

  }

  public AnnotationFS getCoveringParagraph(AnnotationFS anno) {

    for (AnnotationFS par : cas.getAnnotationIndex(getParagraphType())) {

      if (par.getBegin() > anno.getEnd()) {
        return null;
      }

      if (par.getBegin() <= anno.getBegin() && par.getEnd() >= anno.getEnd()) {
        return par;
      }
    }

    return null;

  }

  public AnnotationFS getCoveringDS(AnnotationFS anno) {

    for (AnnotationFS par : cas.getAnnotationIndex(getDirectSpeechType())) {

      if (par.getBegin() > anno.getEnd()) {
        return null;
      }

      if (par.getBegin() <= anno.getBegin() && par.getEnd() >= anno.getEnd()) {
        return par;
      }
    }

    return null;

  }

  public AnnotationFS getCoveringScene(AnnotationFS anno) {

    for (AnnotationFS par : cas.getAnnotationIndex(getSceneType())) {

      if (par.getBegin() > anno.getEnd()) {
        return null;
      }

      if (par.getBegin() <= anno.getBegin() && par.getEnd() >= anno.getEnd()) {
        return par;
      }
    }

    return null;

  }

  // ==============UTIL FOR THE USE OF POS-TAGS============

  public String getPosTag(AnnotationFS anno) {
    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() > 1) {
      return null;
    }

    String posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());

    return posTag;
  }

  public String getLemma(AnnotationFS anno) {

    if (anno.getType() != getPOSType()) {
      List<AnnotationFS> covered = getCovered(anno, getPOSType());

      if (covered.size() > 1) {
        return null;
      }
      String lemma = covered.get(0).getFeatureValueAsString(getLemmaFeature());

      if (lemma.equals("<unknown>") || lemma == null) {
        lemma = covered.get(0).getCoveredText();
      }

      return lemma;
    }
 else {

      String lemma = anno.getFeatureValueAsString(getLemmaFeature());
      if (lemma.equals("<unknown>") || lemma == null) {
        lemma = anno.getCoveredText();
      }

      return lemma;
    }
  }

  public boolean isLemmaKnown(AnnotationFS anno) {

    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() > 1) {
      return false;
    }

    String lemma = covered.get(0).getFeatureValueAsString(getLemmaFeature());

    return !lemma.equals("<unknown>");
  }

  public boolean isPronoun(AnnotationFS anno) {

    String posTag;
    if (!anno.getType().equals(getPOSType())) {

      List<AnnotationFS> covered = getCovered(anno, getPOSType());
      if (covered.size() > 1 || covered.size() == 0) {
        return false;
      }
      posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());
    }

    else {
      posTag = anno.getFeatureValueAsString(getPOSTagFeature());
    }

    if (posTag.matches("PIS|PIAT|PIDAT|PPER|PPOSS|PPOSAT|PRELS|PRELAT|PRF|PWS|PWAT|PWAV")) {
      return true;
    }

    else {
      return false;
    }

  }

  public boolean isVerb(AnnotationFS anno) {

    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() != 1) {
      return false;
    }


    String posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());

    if (posTag.startsWith("V")) {
      return true;
    }

    else {
      return false;
    }

  }

  public boolean isProperNoun(AnnotationFS anno) {

    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() > 1) {
      return false;
    }

    String posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());

    if (posTag.matches("NE")) {
      return true;
    }

    else {
      return false;
    }

  }

  public boolean isAdjective(AnnotationFS anno) {

    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() > 1 || covered.size() == 0) {
      return false;
    }

    String posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());

    if (posTag.matches("ADJA|ADJD")) {
      return true;
    }

    else {
      return false;
    }

  }

  public boolean isCommonNoun(AnnotationFS anno) {

    List<AnnotationFS> covered = getCovered(anno, getPOSType());

    if (covered.size() > 1) {
      return false;
    }

    String posTag = covered.get(0).getFeatureValueAsString(getPOSTagFeature());

    if (posTag.matches("NN")) {
      return true;
    }

    else {
      return false;
    }

  }

  // =============END UTIL POS====================

  // /==============UTIL DEPENDENCY PARSE================

  /*
   * return all Annotations where anno is the head
   */
  public List<AnnotationFS> getParseChildren(AnnotationFS anno) {

    List<AnnotationFS> children = new ArrayList<AnnotationFS>();

    AnnotationFS coveringSentence = getCoveringSentence(anno);

    if (coveringSentence != null) {

      List<AnnotationFS> covered = getCovered(coveringSentence, getDependencyType());

      for (AnnotationFS a : covered) {

        AnnotationFS father = getFather(a);
        if (father != null && isCoveredBy(father, anno)) {
          children.add(a);
        }
      }

    }

    return children;

  }

  public AnnotationFS getFather(AnnotationFS anno) {
    return getHeadAnno(anno);
  }

  public AnnotationFS getFather(AnnotationFS anno, boolean useSentenceParts) {
    return getHeadAnno(anno, useSentenceParts);
  }

  public boolean isDepRootNode(AnnotationFS anno) {

    if (anno.getType().equals(getDependencyType())) {
      return anno.getFeatureValueAsString(getHeadWordFeature()).equals(null);
    }

    else {

      List<AnnotationFS> covered = getCovered(anno, getDependencyType());

      if (covered.size() == 1) {

        // TODO not sure if it is null or "null"
        return covered.get(0).getFeatureValueAsString(getHeadWordFeature()).equals(null);
      }

    }
    return false;

  }

  public boolean isSubject(AnnotationFS anno) {

    if (anno.getType().equals(getDependencyType())) {
      return anno.getFeatureValueAsString(depRelFeature()).equals("SB");
    }

    else {

      List<AnnotationFS> covered = getCovered(anno, getDependencyType());

      if (covered.size() == 1) {

        return covered.get(0).getFeatureValueAsString(depRelFeature()).equals("SB");
      }

      else {

      }


    }
    return false;

  }

  /*
   * return all the Annotations that are below anno in the parsetree
   */
  public List<AnnotationFS> getSuccessors(AnnotationFS anno) {

    List<AnnotationFS> children = new ArrayList<AnnotationFS>();
    children.add(anno);

    for (AnnotationFS child : getParseChildren(anno)) {
      children.addAll(getSuccessors(child));
    }

    return children;

  }

  public AnnotationFS getHeadAnno(AnnotationFS anno) {

    AnnotationFS depAnno = null;
    if (anno.getType().equals(getDependencyType())) {
      depAnno = anno;
    }

    else {

      List<AnnotationFS> covered = getCovered(anno, getDependencyType());

      if (covered.size() == 1) {
        depAnno = covered.get(0);
      }

    }

    AnnotationFS coveringSentence = getCoveringSentence(anno);

    if (coveringSentence != null && depAnno != null) {

      List<AnnotationFS> covered = getCovered(coveringSentence, getDependencyType());

      int wordIndex = Integer.parseInt(depAnno.getFeatureValueAsString(getWordNumerFeature())) - 1;

      if (covered.size() > wordIndex && wordIndex > 0) {

        return covered.get(wordIndex);
      }

    }

    return null;
  }

  public AnnotationFS getHeadAnno(AnnotationFS anno, boolean sentParts) {


    AnnotationFS depAnno = null;
    if (anno.getType().equals(getDependencyType())) {
      depAnno = anno;
    }

    else {

      List<AnnotationFS> covered = getCovered(anno, getDependencyType());

      if (covered.size() == 1) {
        depAnno = covered.get(0);
      }

    }

    AnnotationFS coveringSentence = null;

    if (sentParts) {
      coveringSentence = getCoveringSentencePart(anno);
    }

    else {
      coveringSentence = getCoveringSentence(anno);
    }


    if (coveringSentence != null && depAnno != null) {


      List<AnnotationFS> covered = getCovered(coveringSentence, getDependencyType());

      int wordIndex = Integer.parseInt(depAnno.getFeatureValueAsString(getWordNumerFeature())) - 1;

      if (covered.size() > wordIndex && wordIndex > 0) {

        return covered.get(wordIndex);
      }

    }

    return null;
  }

  public ArrayList<AnnotationFS> getDepPath(AnnotationFS from, AnnotationFS to) {

    // TODO
    return null;
  }

  // =====UTIL OF DIRECT SPEECH AND DIALOGUES=======

  public boolean isInsideDirectSpeech(AnnotationFS anno) {

    return getCoveringDS(anno) != null;
  }

  public boolean isInDialogue(AnnotationFS anno) {
    // TODO
    return false;
  }

  public boolean containsDirectSpeech(AnnotationFS paragraph) {

    for (AnnotationFS a : cas.getAnnotationIndex(getDirectSpeechType())) {
      if (isCoveredBy(a, paragraph)) {
        return true;
      }
    }
    return false;
  }

  // =======UTIL OF CHUNKS=================

  public boolean isInChunk(AnnotationFS anno) {

    return (getCoveringChunk(anno) != null);

  }

  public String getChunkType(AnnotationFS anno) {
    // TODO
    return null;
  }

  public AnnotationFS getCoveringChunk(AnnotationFS anno) {

    for (AnnotationFS a : cas.getAnnotationIndex(getChunkType())) {
      if (isCoveredBy(anno, a)) {
        return a;
      }
    }

    return null;
  }

  public List<AnnotationFS> getTokensInChunk(AnnotationFS anno) {

    AnnotationFS coveringChunk = getCoveringChunk(anno);

    if (coveringChunk == null) {
      return new ArrayList<AnnotationFS>();
    }

    return getCovered(coveringChunk, getPOSType());

  }

  // =============SPEAKER DETECTION UTIL=============

  public AnnotationFS getTokenBeforeAnno(AnnotationFS ds) {

    AnnotationFS lastToken = null;

    for (AnnotationFS token : cas.getAnnotationIndex(getPOSType())) {

      if (token.getBegin() >= ds.getBegin()) {
        // that ones already too much
        return lastToken;
      }
      // update last sentence
      lastToken = token;
    }

    // no idea if this can ever happen
    return null;
  }

  public AnnotationFS getSentenceBeforeDs(AnnotationFS ds) {

    // if a sent starts with the DS it returns exactly this sentence, else
    // it returns the sentence with the largest beg-index, smaller than ds.getBegin

    if (sentenceStartsWithDs(ds)) {
      // we return this sentence
      for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {

        if (sent.getBegin() == ds.getBegin()) {
          return sent;
        }
      }

    }

    // if not find the sentence with the largest beg index smaller than ds.getBegin()

    AnnotationFS lastSentence = null;

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {
      // there can never be an equal in the begin index cause of the check beforehand

      if (sent.getBegin() > ds.getBegin()) {
        // that ones already too much
        return lastSentence;
      }
      // update last sentence
      lastSentence = sent;
    }

    // no idea if this can ever happen
    return null;

  }

  public boolean sentenceStartsWithDs(AnnotationFS ds) {

    for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {

      if (sent.getBegin() == ds.getBegin()) {
        return true;
      }
    }

    return false;
  }

  public boolean dsIsDirectlyFollowedByAnotherDs(AnnotationFS ds) {

    boolean instanceFound = false;

    for (AnnotationFS anno : cas.getAnnotationIndex(getDirectSpeechType())) {

      if (instanceFound) {
        if (anno.getBegin() - ds.getEnd() < 3) {
          return true;
        }
      }
      if (isCoveredBy(anno, ds)) {
        instanceFound = true;
      }
    }
    return false;
  }

  public boolean sentenceEndsWithDs(AnnotationFS ds) {

    AnnotationFS sentenceCoveringDS = getCoveringSentence(ds);
    if (sentenceCoveringDS != null) {
      if (sentenceCoveringDS.getEnd() == ds.getEnd()) {
        return true;
      }
    }
    return false;
  }

  public boolean nextSentenceStartsCapitalized(AnnotationFS ds) {

    AnnotationFS covSent = getCoveringSentence(ds);
    if (covSent == null) {
      return false;
    }

    AnnotationFS nextSent = getNextAnnotation(covSent);

    if (nextSent == null) {
      return true;
    }

    else {

      return Character.isUpperCase(nextSent.getCoveredText().charAt(0));
    }
  }

  public List<AnnotationFS> getRemainingTokensInSentence(AnnotationFS ds) {
    // TODO könnte bugs enthalten - ich hoffe allerdings nicht :)

    List<AnnotationFS> remainingTokens = new ArrayList<AnnotationFS>();
    // get the sentence first that covers the Ds

    AnnotationFS sentCov = null;
    for (AnnotationFS sent : cas.getAnnotationIndex(getSentenceType())) {
      if (ds.getBegin() >= sent.getBegin() && ds.getEnd() <= sent.getEnd()) {
        sentCov = sent;
        break;
      }
    }
    if (sentCov == null) {
      // System.out.println("null");
    }

    else {
      for (AnnotationFS token : cas.getAnnotationIndex(getPOSType())) {
        if (token.getEnd() <= sentCov.getEnd() && token.getBegin() > ds.getEnd()) {
          remainingTokens.add(token);
        }
      }
    }

    // also check the next Sentence, if that one starts lowercase the senternce detetction probably
    // made a mistake

    AnnotationFS covSent = getCoveringSentence(ds);
    AnnotationFS nextSent = getNextAnnotation(covSent);

    if (nextSent != null && Character.isUpperCase(nextSent.getCoveredText().charAt(0))) {
      return remainingTokens;
    }
    if (nextSent == null) {
      return remainingTokens;
    }

    // we found a next sentence with a lowercase start

    FSIterator<AnnotationFS> subiterator = cas.getAnnotationIndex(getPOSType()).subiterator(
            nextSent);

    while (subiterator.hasNext()) {
      AnnotationFS nextTok = subiterator.next();

      // only add until we found another ds or the sentence ends
      if (nextTok.getCoveredText().equals("»")) {
        break;
      }
      remainingTokens.add(nextTok);
    }

    // add all tokens until we find another direct speech

    return remainingTokens;
  }

  public AnnotationFS getCoveringMention(AnnotationFS child) {

    for (AnnotationFS ne : cas.getAnnotationIndex(getNamedEntityType())) {
      if (isCoveredBy(child, ne)) {
        return ne;
      }
    }
    return null;
  }

}
