package ca.sfu.cmpt745.ex06.checker;

import java.util.Map;
import java.util.EnumSet;

import soot.Body;
import soot.BodyTransformer;
import soot.G;
import soot.Local;
import soot.RefType;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import soot.jimple.AbstractExprSwitch;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;



public class KittenChecker extends BodyTransformer {
  KittenChecker(KittenErrorReporter reporter) {
    this.reporter = reporter;
  }

  protected void internalTransform(Body body, String phase, Map options) {
    UnitGraph graph = new ExceptionalUnitGraph(body);

    System.out.println("Implement your analysis here.");
    //KittenAnalysis analysis = new KittenAnalysis(graph);

    // You should define and uncomment the kitten analysis above.
    // Then explore the results and report potential errors using the provided
    // reporter.
  }

  final KittenErrorReporter reporter;
}

