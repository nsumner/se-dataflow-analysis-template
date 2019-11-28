
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_04_ConditionalError {
  public void test(boolean c) {
    Kitten kitten = new Kitten();
    kitten.scare();
    if (c) {
      kitten.feed();
    }
    kitten.pet();
  }
}

