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
    /* NO-OP */
  }
}
