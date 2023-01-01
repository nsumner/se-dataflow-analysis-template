
package ca.sfu.cmpt745.ex06.examples;

import ca.sfu.cmpt745.ex06.kittens.Kitten;


public class Test_12_MultipleDistinctErrors {
  public void test(boolean c) {
    Kitten kitten1 = new Kitten();
    kitten1.pet();
    kitten1.scare();

    Kitten kitten2 = new Kitten();
    Kitten kitten3 = new Kitten();
    kitten3.pet();
    kitten3.scare();

    Kitten kitten4 = new Kitten();

    kitten3.feed();
    kitten3.scare();

    kitten1.feed();
    kitten1.scare();

    kitten4.pet();

    kitten2.pet();

    kitten3.ignore();
    kitten3.tease();

    kitten4.scare();
    kitten4.feed();
    kitten4.scare();

    kitten1.ignore();

    kitten4.ignore();
    kitten4.feed();
    kitten4.pet();
    kitten4.tease();

    kitten1.tease();

    kitten2.scare();
    kitten2.feed();
    kitten2.scare();
    kitten2.ignore();
    kitten2.tease();
    kitten2.ignore();
  }
}

