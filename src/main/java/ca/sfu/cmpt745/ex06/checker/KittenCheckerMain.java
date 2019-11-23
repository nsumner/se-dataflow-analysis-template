package ca.sfu.cmpt745.ex06.checker;

import soot.Body;
import soot.PackManager;
import soot.Transform;
import soot.options.Options;


public class KittenCheckerMain {
  public static void main(String[] args) {
    Options.v().set_keep_line_number(true);
    Transform checker = new Transform("jtp.KittenChecker", new KittenChecker());
    PackManager.v().getPack("jtp").add(checker);
    soot.Main.main(args);
  }
}

