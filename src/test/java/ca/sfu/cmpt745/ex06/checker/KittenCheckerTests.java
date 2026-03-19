// 4e91f891e8c74607adf501dbeaa18a1d
package ca.sfu.cmpt745.ex06.checker;

import ca.sfu.cmpt745.ex06.examples.*;
import ca.sfu.cmpt745.ex06.kittens.Kitten;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.types.ClassType;
import sootup.java.bytecode.frontend.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.views.JavaView;
import sootup.java.core.JavaSootClass;


class KittenCheckerTests {
  private JsonKittenReporter reporter;
  private JavaView view;

  @BeforeEach
  final void initializeSootUp() {
    reporter = new JsonKittenReporter();
    
    String classPath = System.getProperty("user.dir") + "/target/classes";
    AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation(classPath);
    
    view = new JavaView(Collections.singletonList(inputLocation));
  }


  /**
   * Analyze a specific class
   */
  private void analyzeClass(String className) {
    KittenChecker checker = new KittenChecker(reporter);
    var identifierFactory = view.getIdentifierFactory();
    ClassType classType = identifierFactory.getClassType(className);


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
  }


  @Test
  @DisplayName("Check that basic error free behavior can be correct")
  final void basicCorrectness() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_01_Basic.class.getName();
    
    analyzeClass(exampleName);

    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }


  @Test
  @DisplayName("Check that trivially broken code is found")
  final void
  basicErrors() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_02_BasicError.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack2", error.variable());
    assertEquals("running", error.source());
    assertEquals("sleeping", error.target());
  }

  @Test
  @DisplayName("Check that error free `if`s are correct")
  final void
  branchingCorrectness() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_03_Conditional.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken `if`s are reported")
  final void
  branchingError() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_04_ConditionalError.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack3", error.variable());
    assertEquals("running", error.source());
    assertEquals("sleeping", error.target());
  }

  @Test
  @DisplayName("Check that error free loops are correct")
  final void
  loopCorrectness() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_05_Loop.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken loops are reported")
  final void
  loopError() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_06_LoopError.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack4", error.variable());
    assertEquals("eating", error.source());
    assertEquals("plotting", error.target());
  }

  @Test
  @DisplayName("Check that error free value propagation are correct")
  final void
  propagationCorrectness() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_07_Propagate.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

    // First check broad constraints that errors were found on the right lines
    final var allErrors = reporter.getErrors();
    assertEquals(0, allErrors.size());
  }

  @Test
  @DisplayName("Check that broken value propagation is reported")
  final void
  propagationError() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_08_PropagateError.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack6", error.variable());
    assertEquals("running", error.source());
    assertEquals("sleeping", error.target());
  }

  @Test
  @DisplayName("Check multiple scenarios together.")
  final void
  multipleScenariosTogether() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_09_PuttingTogether.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("kitten2#1", error.variable());
    assertEquals("running", error.source());
    assertEquals("sleeping", error.target());
  }

  @Test
  @DisplayName("Check independent states for different variables.")
  final void
  multipleIndependentStates() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_10_MultipleVariables.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack5", error.variable());
    assertEquals("playing", error.source());
    assertEquals("plotting", error.target());
  }

  @Test
  @DisplayName("Check merging with multiple variables")
  final void
  mergingWithMultipleVariables() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_11_MultipleVariablesMerge.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
    assertEquals("$stack5", error.variable());
    assertEquals("playing", error.source());
    assertEquals("plotting", error.target());
  }

  @Test
  @DisplayName("Check multiple distinct errors in one test.")
  final void
  multipleDistinctErrors() {
    final String exampleName = ca.sfu.cmpt745.ex06.examples.Test_12_MultipleDistinctErrors.class.getName();
    final String[] args = new String[] { exampleName };

    analyzeClass(exampleName);

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
      assertEquals("$stack9", error.variable());
      assertEquals("sleeping", error.source());
      assertEquals("playing", error.target());
    }

    {
      final int LINE = 51;
      final int NUMBER_OF_ERRORS = 1;
      final var onLine = allErrors.get(LINE);
      assertNotNull(onLine);
      assertEquals(NUMBER_OF_ERRORS, onLine.size());

      // With those, we can safely check the specific errors involved
      final var error = onLine.get(0);
      assertEquals("$stack7", error.variable());
      assertEquals("playing", error.source());
      assertEquals("plotting", error.target());
    }
  }
}
