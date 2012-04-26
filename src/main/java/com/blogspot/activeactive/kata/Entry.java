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
    validateLine(line);
    if (isFull()) {
      return false;
    } else {
      lines.add(line);
      return true;
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

  public List<Integer> getDigits() {
    final List<Integer> digits = new ArrayList<Integer>();
    for (final String[] character : getCharacters()) {
      digits.add(Numeral.decode(character).value);
    }
    return digits;
  }
}
