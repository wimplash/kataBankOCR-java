package com.blogspot.activeactive.kata;

import java.util.Collection;

public class BankOcr {
  public void parseLines(final Collection<String> lines) {
    if (lines == null || lines.isEmpty()) {
      throw new IllegalArgumentException("The lines parameter is required and"
          + " must not be null-valued or empty.");
    }
    /* NO-OP */
  }
}
