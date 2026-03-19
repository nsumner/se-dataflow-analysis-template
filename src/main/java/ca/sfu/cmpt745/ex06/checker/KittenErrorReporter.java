// 4e91f891e8c74607adf501dbeaa18a1d
package ca.sfu.cmpt745.ex06.checker;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface KittenErrorReporter {
  void reportError(String variableName, int lineNumber, String targetState, String sourceState);
}


record ErrorInfo(String variable, int line, String target, String source) { }


final class JsonKittenReporter implements KittenErrorReporter {

  @Override
  public void reportError(String variableName,
                          int lineNumber,
                          String targetState,
                          String sourceState) {
    targetState = targetState.toLowerCase();
    sourceState = sourceState.toLowerCase();

    System.out.println("Error on " + variableName + " at " + lineNumber + "\n"
                       + sourceState + " -> " + targetState + "\n");

    errors.computeIfAbsent(lineNumber, key -> new ArrayList<ErrorInfo>())
          .add(new ErrorInfo(variableName, lineNumber, targetState, sourceState));
  }

  public void dumpErrors(final PrintStream out) {
    String errorString = errors.entrySet().stream()
      .filter(entry -> !entry.getValue().isEmpty())
      .map(entry -> {
        String bugList = entry.getValue().stream()
          .map(JsonKittenReporter::reportToJSON)
          .collect(Collectors.joining(", "));
        return "  {\"line\":" + entry.getKey() + ", \"bugs\":[" + bugList + "]}";
      })
      .collect(Collectors.joining(",\n"));

    out.println("{ \"errors\": [\n" + errorString + "\n] }");
  }

  public Map<Integer,List<ErrorInfo>> getErrors() {
    return Collections.unmodifiableMap(errors);
  }

  private static String reportToJSON(ErrorInfo report) {
    return "{\"variable\":\"" + report.variable() + "\", "
         + "\"target\":\"" + report.target() + "\", "
         + "\"source\":\"" + report.source() + "\" "
         + " } ";
  }

  private HashMap<Integer,List<ErrorInfo>> errors = new HashMap<>();
}

