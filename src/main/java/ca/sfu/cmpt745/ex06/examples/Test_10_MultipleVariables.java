
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_10_MultipleVariables {
  public void test(boolean c) {
    Kitten kitten1 = new Kitten();
    kitten1.pet();
    kitten1.scare();

    Kitten kitten2 = new Kitten();

    kitten1.feed();
    kitten1.scare();

    kitten2.pet();

    kitten1.ignore();
    kitten1.tease();

    kitten2.scare();
    kitten2.feed();
    kitten2.scare();
    kitten2.ignore();
    kitten2.tease();
    kitten2.ignore();
  }
}

