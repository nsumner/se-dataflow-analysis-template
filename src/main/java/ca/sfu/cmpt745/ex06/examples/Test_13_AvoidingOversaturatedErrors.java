
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_13_AvoidingOversaturatedErrors {
  public void test(boolean c) {
    Kitten kitten1 = new Kitten();
    kitten1.pet();
    kitten1.scare();
  }
}

