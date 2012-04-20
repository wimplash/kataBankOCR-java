package com.blogspot.activeactive.kata;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BankOcrTest  {

  private BankOcr cut;

  @Before
  public void createClassUnderTest() {
    cut = new BankOcr();
  }

  @Test
  public void cut_shouldExist() {
    assertThat(cut, is(notNullValue()));
  }

  @Test
  public void parseLines_shouldAcceptAListOfStringValues() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _ ");
    }
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectANullValuedList() {
    cut.parseLines(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectAnEmptyList() {
    final List<String> lines = new ArrayList<String>();
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectAListWhichDoesNotContainAMultipleOfFourLines() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 3; i++) {
      lines.add("");
    }
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectAListForWhichEachElementDoesNotContainTwentySevenCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add("12345678901234567890123456");
    }
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectAListForWhichAnElementContainsInvalidCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _1");
    }
    cut.parseLines(lines);
  }

  @Test
  public void parseLines_shouldSeparateLinesIntoAListOfStringArrays() {
    final String testLine = " _ | | _ | | _ | | _ | | _ ";
    final List<String> testLines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      testLines.add(testLine);
    }
    final List<String[]> result = cut.parseLines(testLines);
    assertThat(result.size(), is(1));
    final String[] entry = result.iterator().next();
    assertThat(entry.length, is(4));
    for (final String line : entry) {
      assertThat(line, is(testLine));
    }
  }
}
