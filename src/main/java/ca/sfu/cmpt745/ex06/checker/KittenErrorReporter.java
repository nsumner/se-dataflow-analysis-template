package ca.sfu.cmpt745.ex06.checker;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface KittenErrorReporter {
  public void reportError(String variableName,
                          int lineNumber,
                          String targetState,
                          String sourceState);
}


class ErrorInfo {
  public String variable;
  public int line;
  public String target;
  public String source;
  public ErrorInfo(String variable, int line, String target, String source) {
    this.variable = variable;
    this.line = line;
    this.target = target;
    this.source = source;
  }
}


class JsonKittenReporter implements KittenErrorReporter {
  public final void reportError(String variableName,
                          int lineNumber,
                          String targetState,
                          String sourceState) {
    targetState = targetState.toLowerCase();
    sourceState = sourceState.toLowerCase();

    System.out.println("Error on " + variableName + " at " + lineNumber + "\n"
                       + sourceState + " -> " + targetState + "\n");
    List<ErrorInfo> lineInfo =
      errors.computeIfAbsent(lineNumber, key -> new ArrayList<ErrorInfo>());
    lineInfo.add(new ErrorInfo(variableName, lineNumber, targetState, sourceState));
  }

  public final void dumpErrors(final PrintStream out) {
    String errorString = errors.entrySet().stream()
      .filter(entry -> !entry.getValue().isEmpty())
      .map(entry -> {
        int line = entry.getKey();
        List<ErrorInfo> reports = entry.getValue();
        return "  {\"line\":" + line + ", \"bugs\":["
          + reports.stream()
              .map(JsonKittenReporter::reportToJSON)
              .collect(Collectors.joining(", "))
          + "]}";
      })
      .collect(Collectors.joining(",\n"));

    out.println("{ \"errors\": [\n" + errorString + "\n] }");
  }

  public final Map<Integer,ArrayList<ErrorInfo>> getErrors() {
    return Collections.unmodifiableMap(errors);
  }

  private static String reportToJSON(ErrorInfo report) {
    return "{\"variable\":\"" + report.variable + "\", "
         + "\"target\":\"" + report.target + "\", "
         + "\"source\":\"" + report.source + "\" "
         + " } ";
  }

  private HashMap<Integer,ArrayList<ErrorInfo>> errors = new HashMap<>();
}

