// 4e91f891e8c74607adf501dbeaa18a1d
package ca.sfu.cmpt745.ex06.checker;

import java.util.Map;
import java.util.EnumSet;


import sootup.core.graph.StmtGraph;
import sootup.core.jimple.basic.Local;
import sootup.core.jimple.common.stmt.*;
import sootup.core.jimple.common.expr.*;
import sootup.core.model.Body;
import sootup.core.signatures.MethodSignature;
import sootup.core.types.ClassType;
import sootup.core.types.Type;
import sootup.core.views.View;
import sootup.analysis.intraprocedural.ForwardFlowAnalysis;


public class KittenChecker {
  KittenChecker(KittenErrorReporter reporter) {
    this.reporter = reporter;
  }

  public void transform(Body body, View view) {
    StmtGraph<?> graph = body.getStmtGraph();

    System.out.println("Implement your analysis here.");
    System.err.println("\n\nImplement your analysis here.\n\n");
    //KittenAnalysis analysis = new KittenAnalysis(body, graph, view);

    // You should define and uncomment the kitten analysis above.
    // Then explore the results and report potential errors using the provided
    // reporter.
  }

  final KittenErrorReporter reporter;
}

