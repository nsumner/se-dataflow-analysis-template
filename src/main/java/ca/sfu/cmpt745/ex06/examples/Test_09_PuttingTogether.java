
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_09_PuttingTogether {
  public void test(boolean c, int count) {
    Kitten kitten = new Kitten();
    kitten.pet();
    kitten.scare();
    Kitten kitten2 = null;
    if (c) {
      kitten2 = kitten;
    } else {
      kitten2 = new Kitten();
    }
    kitten2.pet();

    Object o = new Object();
    System.out.println(o);
    
    kitten2.feed();
  }
}

