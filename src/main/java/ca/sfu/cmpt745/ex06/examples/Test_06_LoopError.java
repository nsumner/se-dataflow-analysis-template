
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_06_LoopError {
  public void test(boolean c, int count) {
    Kitten kitten = new Kitten();
    kitten.scare();
    while (count > 0) {
      kitten.ignore();
      kitten.tease();
      kitten.feed();
      kitten.scare();
      --count;
      if (c) {
        kitten.feed();
      }
    }
    kitten.feed();
  }
}

