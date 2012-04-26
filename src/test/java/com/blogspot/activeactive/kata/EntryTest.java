/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import java.util.List;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EntryTest {
  
  @Test(expected = IllegalArgumentException.class)
  public void addLine_shouldRejectALineWhichDoesNotContainTwentySevenCharacters() {
    final String line = "12345678901234567890123456";
    new Entry().addLine(line);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addLine_shouldRejectALineWhichContainsInvalidCharacters() {
    final String line = " _ | | _ | | _ | | _ | | _1";
    new Entry().addLine(line);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addLine_shouldRejectANullValuedLine() {
    new Entry().addLine(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addLine_shouldRejectAnEmptyLine() {
    new Entry().addLine("");
  }

  @Test
  public void getLines_shouldReturnAnEmptyListForANewEntry() {
    assertThat(new Entry().getLines().size(), is(0));
  }

  @Test
  public void getLines_shouldReturnAFourElementListForAnEntryWhichHasFourLinesAdded() {
    final Entry cut = new Entry();
    final String line = " _ | | _ | | _ | | _ | | _ ";
    for (int i = 0; i < 4; i++) {
      cut.addLine(line);
    }
    final List<String> lines = cut.getLines();
    assertThat(lines.size(), is(4));
    for (final String l : lines) {
      assertThat(l, is(line));
    }
  }

  @Test
  public void getLines_shouldReturnTheFirstFourLinesForAnEntryWhichHasFiveLinesAdded() {
    final Entry cut = new Entry();
    final String line = " _ | | _ | | _ | | _ | | _ ";
    for (int i = 0; i < 5; i++) {
      cut.addLine(line);
    }
    final List<String> lines = cut.getLines();
    assertThat(lines.size(), is(4));
    for (final String l : lines) {
      assertThat(l, is(line));
    }
  }

  @Test
  public void isFull_shouldReturnFalseForANewEntry() {
    assertThat(new Entry().isFull(), is(false));
  }

  @Test
  public void isFull_shouldReturnFalseForAnEntryWithThreeLinesAdded() {
    final Entry cut = new Entry();
    final String line = " _ | | _ | | _ | | _ | | _ ";
    for (int i = 0; i < 3; i++) {
      cut.addLine(line);
    }
    assertThat(cut.isFull(), is(false));
  }

  @Test
  public void isFull_shouldReturnTrueForAnEntryWithFourLinesAdded() {
    final Entry cut = new Entry();
    final String line = " _ | | _ | | _ | | _ | | _ ";
    for (int i = 0; i < 4; i++) {
      cut.addLine(line);
    }
    assertThat(cut.isFull(), is(true));
  }

  @Test
  public void getCharacters_shouldReturnAListOfStringArraysForTheCharactersInTheEntry() {
    final Entry cut = new Entry();
    cut.addLine(" _     _  _     _  _  _  _ ");
    cut.addLine("| |  | _| _||_||_ |_   ||_|");
    cut.addLine("|_|  ||_  _|  | _||_|  ||_|");
    cut.addLine("                           ");
    final List<String[]> results = cut.getCharacters();
    for (int i = 0; i < 9; i++) {
      final String[] result = results.get(i);
      final Numeral n = Numeral.decode(result);
      assertThat(n.value, is(i));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void getCharacters_shouldFailWhenTheEntryIsNotFull() {
    final Entry cut = new Entry();
    cut.addLine(" _     _  _     _  _  _  _ ");
    cut.addLine("| |  | _| _||_||_ |_   ||_|");
    cut.addLine("|_|  ||_  _|  | _||_|  ||_|");
    cut.getCharacters();
  }

  @Test
  public void getDigits_shouldReturnAListOfIntegerValuesForTheCharactersInTheEntry() {
    final Entry cut = new Entry();
    cut.addLine(" _     _  _     _  _  _  _ ");
    cut.addLine("| |  | _| _||_||_ |_   ||_|");
    cut.addLine("|_|  ||_  _|  | _||_|  ||_|");
    cut.addLine("                           ");
    final List<Integer> results = cut.getDigits();
    for (int i = 0; i < 9; i++) {
      assertThat(results.get(i), is(i));
    }
  }
}
