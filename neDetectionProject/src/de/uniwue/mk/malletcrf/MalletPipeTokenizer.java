package de.uniwue.mk.malletcrf;

import java.io.Serializable;

import cc.mallet.extract.StringTokenization;
import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class MalletPipeTokenizer extends Pipe implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public Instance pipe(Instance carrier) {



    CharSequence string = (CharSequence) carrier.getData();

    TokenSequence ts = new StringTokenization(string);

    // first is the token

    Token tok = new Token(string.toString().split(" ")[0]);

    // all others are features

    String[] sarr = string.toString().split(" ");
    for (int i = 1; i < sarr.length; i++) {

      tok.setFeatureValue(sarr[i], 1.0);

    }
    ts.add(tok);

    // ts.add(string.toString());

    carrier.setData(ts);
    return carrier;
  }

}
