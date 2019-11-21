package ca.sfu.cmpt745.ex06.checker;


public class KittenErrorReporter {
  public void reportError(String variableName,
                          int lineNumber,
                          String targetState,
                          String sourceState) {
    System.out.println("Error on " + variableName + " at " + lineNumber + "\n"
                       + sourceState + " -> " + targetState + "\n");
  }
}

