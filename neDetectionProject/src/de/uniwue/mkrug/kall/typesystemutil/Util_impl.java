package de.uniwue.mkrug.kall.typesystemutil;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.apache.uima.cas.CAS;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;

public class Util_impl extends TypesystemUtil {

  public Util_impl(CAS cas) {
    super(cas);
  }

  public static CAS createCas() {

    // Bundle bundle = Platform.getBundle("de.uniwue.mkrug.kallimachos.typesystemutil");
    // System.out.println(bundle);
    // URL fileURL = bundle.getEntry("typesystem/CorefTypeSystem.xml");
    // File file = null;
    // try {
    // file = new File(FileLocator.resolve(fileURL).toURI());
    // } catch (URISyntaxException e1) {
    // e1.printStackTrace();
    // } catch (IOException e1) {
    // e1.printStackTrace();
    // }

    File file = new File(
            "C:\\Users\\mrkug\\workspace\\de.uniwue.mkrug.kallimachos.typesystemutil\\typesystem\\CorefTypeSystem.xml");

    Properties prop = new Properties();
    URL resource = Util_impl.class.getResource(
            "CorefTypeSystem.xml");








    TypeSystemDescription tsd;
    try {
      tsd = TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(resource.toString()
              .toString());

      return CasCreationUtils.createCas(tsd, null, null);
    } catch (ResourceInitializationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

}
