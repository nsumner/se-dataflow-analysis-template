
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_02_BasicError {
  public void test() {
    Kitten kitten = new Kitten();
    kitten.pet();
    kitten.scare();
    kitten.pet();
  }
}

