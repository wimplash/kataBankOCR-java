package com.blogspot.activeactive.kata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class BankOcr {
  protected List<String> readFile(final String filename) throws IOException {
    if (filename == null || "".equals(filename.trim())) {
      throw new IllegalArgumentException("The filename parameter is required"
          + " and must not be null-valued, empty, or contain only whitespace.");
    }
    final File f = new File(filename);
    final FileReader fr = new FileReader(f);
    final BufferedReader br = new BufferedReader(fr);
    final List<String> results = new ArrayList<String>();
    String line = br.readLine();
    while (line != null) {
      results.add(line);
      line = br.readLine();
    }
    return results;
  }

  protected List<String[]> parseFile(final List<String> contents) {
    validateContents(contents);

    final List<String[]> result = new ArrayList<String[]>();
    int lineIdx = 0;
    String[] entry = new String[4];
    for (final String line : contents) {
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

  private void validateContents(final List<String> contents) {
    if (contents == null || contents.isEmpty()) {
      throw new IllegalArgumentException("The contents parameter is required and"
          + " must not be null-valued or empty.");
    }
    if ((contents.size() % 4) != 0) {
      throw new IllegalArgumentException("Each valid entry in the contents"
          + " parameter must contain exactly four lines. The contents parameter"
          + " does not contain a whole number of valid account number entries.");
    }
  }
}
