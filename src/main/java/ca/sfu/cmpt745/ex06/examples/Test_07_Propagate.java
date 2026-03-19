
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_07_Propagate {
  public void test(boolean c, int count) {
    Kitten kitten = new Kitten();
    kitten.pet();
    Kitten kitten2 = kitten;
    kitten2.scare();
    Kitten kitten3 = kitten2;
    kitten3.feed();
  }
}

