package ca.sfu.cmpt745.ex06.checker;

import soot.Body;
import soot.PackManager;
import soot.Transform;


public class KittenCheckerMain {
  public static void main(String[] args) {
    Transform checker = new Transform("jtp.KittenChecker", new KittenChecker());
    PackManager.v().getPack("jtp").add(checker);
    soot.Main.main(args);
  }
}

