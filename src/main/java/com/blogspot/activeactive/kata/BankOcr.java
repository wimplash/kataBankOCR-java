package com.blogspot.activeactive.kata;

import java.util.List;
import java.util.ArrayList;

public class BankOcr {
  public List<String[]> parseLines(final List<String> lines) {
    validateLines(lines);

    final List<String[]> result = new ArrayList<String[]>();
    int lineIdx = 0;
    String[] entry = new String[4];
    for (final String line : lines) {
      validateLine(line);
      entry[lineIdx] = line;
      if (lineIdx == 3) {
        lineIdx = 0;
        result.add(entry);
        entry = new String[4];
      } else {
        lineIdx++;
      }
    }
    return result;
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

  private void validateLines(final List<String> lines) {
    if (lines == null || lines.isEmpty()) {
      throw new IllegalArgumentException("The lines parameter is required and"
          + " must not be null-valued or empty.");
    }
    if ((lines.size() % 4) != 0) {
      throw new IllegalArgumentException("Each valid entry in the lines"
          + " parameter must contain exactly four lines. The lines parameter"
          + " does not contain a whole number of valid entries.");
    }
  }
}
