package com.blogspot.activeactive.kata;

import java.util.Collection;

public class BankOcr {
  public void parseLines(final Collection<String> lines) {
    if (lines == null || lines.isEmpty()) {
      throw new IllegalArgumentException("The lines parameter is required and"
          + " must not be null-valued or empty.");
    }
    if ((lines.size() % 4) != 0) {
      throw new IllegalArgumentException("Each valid entry in the lines"
          + " parameter must contain exactly four lines. The lines parameter"
          + " does not contain a whole number of valid entries.");
    }
    for (final String line : lines) {
      if (line.length() != 27) {
        throw new IllegalArgumentException("Each valid line contained in the"
            + " lines parameter must contain exactly 27 characters.");
      }
      if (line.replaceAll("[|_ ]", "").equals("")) {
      } else {
        throw new IllegalArgumentException("Each valid line contained in the"
            + " lines parameter may only contain underscore (\"_\")"
            + " characters, pipe (\"|\") characters, or spaces (\" \").");
      }
    }
  }
}
