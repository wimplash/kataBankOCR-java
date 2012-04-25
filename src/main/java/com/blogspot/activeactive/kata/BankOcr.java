/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class BankOcr {
  public static final String[] ONE = new String[] {
    "   ",
    "  |",
    "  |",
    "   "};
  public static final String[] TWO = new String[] {
    " _ ",
    " _|",
    "|_ ",
    "   "};
  public static final String[] THREE = new String[] {
    " _ ",
    " _|",
    " _|",
    "   "};

  protected int identifyCharacter(final String[] character) {
    if (ONE.equals(character)) {
      return 1;
    } else if (TWO.equals(character)) {
      return 2;
    } else if (THREE.equals(character)) {
      return 3;
    } else {
      final StringBuffer msg = new StringBuffer();
      msg.append("Could not identify the given character:");
      msg.append(System.getProperty("line.separator"));
      msg.append('\'').append(character[0]).append('\'');
      msg.append(System.getProperty("line.separator"));
      msg.append('\'').append(character[1]).append('\'');
      msg.append(System.getProperty("line.separator"));
      msg.append('\'').append(character[2]).append('\'');
      msg.append(System.getProperty("line.separator"));
      msg.append('\'').append(character[3]).append('\'');
      throw new IllegalArgumentException(msg.toString());
    }
  }

  protected List<String> readFile(final String filename) throws IOException {
    final BufferedReader br = createReader(filename);
    final List<String> results = new ArrayList<String>();
    String line = br.readLine();
    while (line != null) {
      results.add(line);
      line = br.readLine();
    }
    return results;
  }

  protected List<String[]> splitContents(final List<String> contents) {
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

  private BufferedReader createReader(final String filename) throws IOException {
    validateFilename(filename);
    final File f = new File(filename.trim());
    validateFile(f);

    /*
     * Ideally, in a language other than java, I could find a way to
     * mock an un-readable file in the tests to exercise this line, but
     * I cannot easily do that here.
     */
    final FileReader fr = new FileReader(f);
    return new BufferedReader(fr);
  }

  private void validateFile(final File file) {
    if (! file.exists()) {
      throw new IllegalArgumentException("The filename parameter is required"
          + " but refers to a file which does not exist.");
    }
  }

  private void validateFilename(final String filename) {
    if (filename == null || "".equals(filename.trim())) {
      throw new IllegalArgumentException("The filename parameter is required"
          + " and must not be null-valued, empty, or contain only whitespace.");
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
