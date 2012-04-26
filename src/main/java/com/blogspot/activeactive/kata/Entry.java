/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entry {
  private final List<String> lines = new ArrayList<String>();

  public List<String> getLines() {
    return Collections.unmodifiableList(lines);
  }

  public boolean isFull() {
    return lines.size() == 4;
  }

  public boolean addLine(final String line) {
    if (isFull()) {
      return false;
    } else {
      validateLine(line);
      lines.add(line);
      return true;
    }
  }

  private void validateLine(final String line) {
    if (line.length() != 27) {
      throw new IllegalArgumentException("Each valid line contained in the"
          + " lines parameter must contain exactly 27 characters.");
    }
    if (! line.replaceAll("[|_ ]", "").equals("")) {
      throw new IllegalArgumentException("Each valid line contained in the"
          + " lines parameter may only contain underscore (\"_\")"
          + " characters, pipe (\"|\") characters, or spaces (\" \").");
    }
  }
}
