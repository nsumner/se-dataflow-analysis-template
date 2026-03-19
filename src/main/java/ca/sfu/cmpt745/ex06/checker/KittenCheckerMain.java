// 4e91f891e8c74607adf501dbeaa18a1d
package ca.sfu.cmpt745.ex06.checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import sootup.core.types.ClassType;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.views.JavaView;
import sootup.java.core.JavaSootClass;


public class KittenCheckerMain {
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new RuntimeException("Must pass the class to analyze on the command line.");
    }

    final Path classPath = Paths.get(System.getProperty("user.dir"), "target", "classes");
    final var inputLocation = new JavaClassPathAnalysisInputLocation(classPath.toString());
    final var view = new JavaView(Collections.singletonList(inputLocation));

    final var identifierFactory = view.getIdentifierFactory();
    final var reporter = new JsonKittenReporter();
    final var checker = new KittenChecker(reporter);
    final ClassType classType = identifierFactory.getClassType(args[0]);

    view.getClass(classType).ifPresent(sootClass -> {
      if (sootClass instanceof JavaSootClass javaClass) {
        javaClass.getMethods().forEach(method -> {
          if (method.hasBody()) {
            try {
              checker.transform(method.getBody(), view);
            } catch (Exception e) {
              System.err.println("Error analyzing method: " + method.getSignature() + " - " + e.getMessage());
              throw new RuntimeException(e);
            }
          }
        });
      }
    });

    final var file = new File("kitten-errors.json");
    try (final PrintStream out = new PrintStream(file)) {
      reporter.dumpErrors(out);
    } catch (java.io.FileNotFoundException fnfe) {
      System.err.println("*** COULD NOT OPEN JSON FILE FOR LOGGING! ***");
      throw new RuntimeException(fnfe);
    }
  }
}

