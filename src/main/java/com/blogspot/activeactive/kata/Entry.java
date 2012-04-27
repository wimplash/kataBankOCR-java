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

  public boolean addLine(final String line) {
    validateLine(line);
    if (isFull()) {
      return false;
    } else {
      lines.add(line);
      return true;
    }
  }

  protected List<String[]> getCharacters() {
    if (! isFull()) {
      throw new IllegalStateException("The characters in an Entry can only be"
          + " retrieved when the Entry has been fully populated.");
    }
    final List<String[]> characters = new ArrayList<String[]>();
    for (int i = 0; i < 9; i++) {
      final String[] result = new String[4];
      for (int j = 0; j < 4; j++) {
        result[j] = lines.get(j).substring(i * 3, (i + 1) * 3);
      }
      characters.add(result);
    }
    return characters;
  }

  public int getChecksum() {
    final List<Integer> digits = getDigits();
    int checksum = 0;
    for (int i = 0; i < digits.size(); i++) {
      checksum += (i + 1) * digits.get(digits.size() - (i + 1));
    }
    return checksum;
  }

  public List<Integer> getDigits() {
    final List<Integer> digits = new ArrayList<Integer>();
    for (final String[] character : getCharacters()) {
      digits.add(Numeral.decode(character).value);
    }
    return digits;
  }

  public List<String> getLines() {
    return Collections.unmodifiableList(lines);
  }

  public boolean isFull() {
    return lines.size() == 4;
  }

  @Override
  public String toString() {
    if (isFull()) {
      final StringBuffer buff = new StringBuffer();
      for (final Integer digit : getDigits()) {
        buff.append(digit);
      }
      return buff.toString();
    } else {
      final StringBuffer buff = new StringBuffer("incomplete:");
      for (final String line : lines) {
        buff.append(System.getProperty("line.separator"));
        buff.append(line);
      }
      return buff.toString();
    }
  }

  private void validateLine(final String line) {
    if (line == null) {
      throw new IllegalArgumentException("The line parameter is required and"
          + " must not be null-valued.");
    }
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
