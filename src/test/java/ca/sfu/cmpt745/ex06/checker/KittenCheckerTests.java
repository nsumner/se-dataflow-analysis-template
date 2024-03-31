package ca.sfu.cmpt745.ex06.checker;

import ca.sfu.cmpt745.ex06.examples.*;
import ca.sfu.cmpt745.ex06.kittens.Kitten;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import soot.Body;
import soot.G;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.Transform;
import soot.options.Options;


class KittenCheckerTests {
  static JsonKittenReporter reporter = null;

  @BeforeEach
  final void
  initializeSoot() {
    reporter  = new JsonKittenReporter();

    soot.G.v().reset();
    Options.v().set_keep_line_number(true);
    Options.v().set_soot_classpath(".:target/classes:VIRTUAL_FS_FOR_JDK");
    final var checker   = new KittenChecker(reporter);
    final var transform = new Transform("jtp.KittenChecker", checker);
    PackManager.v().getPack("jtp").add(transform);
    Scene.v().addBasicClass("ca.sfu.cmpt745.ex06.kittens.Kitten",
                            SootClass.SIGNATURES);
  }

  @Test
  @DisplayName("Check that basic error free behavior can be correct")
  final void
  basicCorrectness() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_01_Basic";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that trivially broken code is found")
  final void
  basicErrors() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_02_BasicError";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 12;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r0", error.variable);
    assertEquals("running", error.source);
    assertEquals("sleeping", error.target);
  }

  @Test
  @DisplayName("Check that error free `if`s are correct")
  final void
  branchingCorrectness() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_03_Conditional";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken `if`s are reported")
  final void
  branchingError() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_04_ConditionalError";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 14;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r0", error.variable);
    assertEquals("running", error.source);
    assertEquals("sleeping", error.target);
  }

  @Test
  @DisplayName("Check that error free loops are correct")
  final void
  loopCorrectness() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_05_Loop";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken loops are reported")
  final void
  loopError() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_06_LoopError";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 12;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r2", error.variable);
    assertEquals("eating", error.source);
    assertEquals("plotting", error.target);
  }

  @Test
  @DisplayName("Check that error free value propagation are correct")
  final void
  propagationCorrectness() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_07_Propagate";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken value propagation is reported")
  final void
  propagationError() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_08_PropagateError";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 14;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r0", error.variable);
    assertEquals("running", error.source);
    assertEquals("sleeping", error.target);
  }

  @Test
  @DisplayName("Check multiple scenarios together.")
  final void
  multipleScenariosTogether() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_09_PuttingTogether";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 18;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("r5", error.variable);
    assertEquals("running", error.source);
    assertEquals("sleeping", error.target);
  }

  @Test
  @DisplayName("Check independent states for different variables.")
  final void
  multipleIndependentStates() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_10_MultipleVariables";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 28;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r1", error.variable);
    assertEquals("playing", error.source);
    assertEquals("plotting", error.target);
  }

  @Test
  @DisplayName("Check merging with multiple variables")
  final void
  mergingWithMultipleVariables() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_11_MultipleVariablesMerge";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(1, allErrors.size());

    final int LINE = 31;
    final int NUMBER_OF_ERRORS = 1;
    final var onLine = allErrors.get(LINE);
    assertNotNull(onLine);
    assertEquals(NUMBER_OF_ERRORS, onLine.size());

    // With those, we can safely check the specific errors involved
    final var error = onLine.get(0);
    assertEquals("$r1", error.variable);
    assertEquals("playing", error.source);
    assertEquals("plotting", error.target);
  }

  @Test
  @DisplayName("Check multiple distinct errors in one test.")
  final void
  multipleDistinctErrors() {
    final String exampleName = "ca.sfu.cmpt745.ex06.examples.Test_12_MultipleDistinctErrors";
    final String[] args = new String[] { exampleName };

    soot.Main.main(args);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(2, allErrors.size());

    {
      final int LINE = 42;
      final int NUMBER_OF_ERRORS = 1;
      final var onLine = allErrors.get(LINE);
      assertNotNull(onLine);
      assertEquals(NUMBER_OF_ERRORS, onLine.size());

      // With those, we can safely check the specific errors involved
      final var error = onLine.get(0);
      assertEquals("$r3", error.variable);
      assertEquals("sleeping", error.source);
      assertEquals("playing", error.target);
    }

    {
      final int LINE = 51;
      final int NUMBER_OF_ERRORS = 1;
      final var onLine = allErrors.get(LINE);
      assertNotNull(onLine);
      assertEquals(NUMBER_OF_ERRORS, onLine.size());

      // With those, we can safely check the specific errors involved
      final var error = onLine.get(0);
      assertEquals("$r1", error.variable);
      assertEquals("playing", error.source);
      assertEquals("plotting", error.target);
    }
  }
}
