
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_03_Conditional {
  public void test(boolean c) {
    Kitten kitten = new Kitten();
    kitten.pet();
    if (c) {
      kitten.feed();
    }
    kitten.pet();
  }
}

