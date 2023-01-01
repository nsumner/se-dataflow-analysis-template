package ca.sfu.cmpt745.ex06.checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import soot.Body;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.Transform;
import soot.options.Options;


public class KittenCheckerMain {
  public static void main(String[] args) {
    Options.v().set_keep_line_number(true);
    final var reporter  = new JsonKittenReporter();
    final var checker   = new KittenChecker(reporter);
    final var transform = new Transform("jtp.KittenChecker", checker);
    PackManager.v().getPack("jtp").add(transform);
    Scene.v().addBasicClass("ca.sfu.cmpt745.ex06.kittens.Kitten",
                            SootClass.SIGNATURES);
    soot.Main.main(args);

    final var file = new File("kitten-errors.json");
    try (final PrintStream out = new PrintStream(file)) {
      reporter.dumpErrors(out);
    } catch (java.io.FileNotFoundException fnfe) {
      System.err.println("*** COULD NOT OPEN JSON FILE FOR LOGGING! ***");
      throw new RuntimeException(fnfe);
    }
  }
}

