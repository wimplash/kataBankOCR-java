/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
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
  public void readEntries_shouldAcceptAListOfStringValues() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _ ");
    }
    cut.readEntries(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readEntries_shouldRejectANullValuedList() {
    cut.readEntries(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readEntries_shouldRejectAnEmptyList() {
    final List<String> lines = new ArrayList<String>();
    cut.readEntries(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readEntries_shouldRejectAListWhichDoesNotContainAMultipleOfFourLines() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 3; i++) {
      lines.add("");
    }
    cut.readEntries(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readEntries_shouldRejectAListForWhichEachElementDoesNotContainTwentySevenCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add("12345678901234567890123456");
    }
    cut.readEntries(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readEntries_shouldRejectAListForWhichAnElementContainsInvalidCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _1");
    }
    cut.readEntries(lines);
  }

  @Test
  public void readEntries_shouldSeparateLinesIntoAListOfEntries() {
    final String testLine = " _ | | _ | | _ | | _ | | _ ";
    final List<String> testLines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      testLines.add(testLine);
    }
    final List<Entry> result = cut.readEntries(testLines);
    assertThat(result.size(), is(1));
    final Entry entry = result.iterator().next();
    assertThat(entry.getLines().size(), is(4));
    for (final String line : entry.getLines()) {
      assertThat(line, is(testLine));
    }
  }

  @Test
  public void readFile_shouldReadAllLinesFromTheGivenFile() throws Exception {
    final List<String> results = cut.readFile("src/test/java/.test");
    final List<String> expected = new ArrayList<String>();
    expected.add("TEST LINE 1");
    expected.add("TEST LINE 2");
    expected.add("TEST LINE 3");
    expected.add("TEST LINE 4");
    assertThat(results, is(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readFile_shouldFailWhenPassedNull() throws Exception {
    cut.readFile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readFile_shouldFailWhenPassedAnEmptyString() throws Exception {
    cut.readFile("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readFile_shouldFailWhenPassedAStringContainingOnlyWhitespace() throws Exception {
    cut.readFile(" ");
  }

  @Test
  public void readFile_shouldIgnoreLeadingAndTrailingWhitespaceInFilenameParameter() throws Exception {
    final List<String> results = cut.readFile(" src/test/java/.test ");
    final List<String> expected = new ArrayList<String>();
    expected.add("TEST LINE 1");
    expected.add("TEST LINE 2");
    expected.add("TEST LINE 3");
    expected.add("TEST LINE 4");
    assertThat(results, is(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readFile_shouldThrowIllegalArgumentExceptionWhenFileDoesNotExist() throws Exception {
    cut.readFile("not/a/real/.file");
  }

  @Test
  public void identifyCharacter_shouldIdentifyOne() {
    final String[] character = new String[] {
      "   ",
      "  |",
      "  |",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(1));
  }

  @Test
  public void identifyCharacter_shouldIdentifyTwo() {
    final String[] character = new String[] {
      " _ ",
      " _|",
      "|_ ",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void identifyCharacter_shouldFailForInvalidCharacter() {
    final String[] invalid = new String[] {
      " _ ",
      " _ ",
      " _ ",
      "   "};
    cut.identifyCharacter(invalid);
  }

  @Test
  public void identifyCharacter_shouldIdentifyThree() {
    final String[] character = new String[] {
      " _ ",
      " _|",
      " _|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(3));
  }

  @Test
  public void identifyCharacter_shouldIdentifyFour() {
    final String[] character = new String[] {
      "   ",
      "|_|",
      "  |",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(4));
  }

  @Test
  public void identifyCharacter_shouldIdentifyFive() {
    final String[] character = new String[] {
      " _ ",
      "|_ ",
      " _|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(5));
  }

  @Test
  public void identifyCharacter_shouldIdentifySix() {
    final String[] character = new String[] {
      " _ ",
      "|_ ",
      "|_|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(6));
  }

  @Test
  public void identifyCharacter_shouldIdentifySeven() {
    final String[] character = new String[] {
      " _ ",
      "  |",
      "  |",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(7));
  }

  @Test
  public void identifyCharacter_shouldIdentifyEight() {
    final String[] character = new String[] {
      " _ ",
      "|_|",
      "|_|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(8));
  }

  @Test
  public void identifyCharacter_shouldIdentifyNine() {
    final String[] character = new String[] {
      " _ ",
      "|_|",
      " _|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(9));
  }

  @Test
  public void identifyCharacter_shouldIdentifyZero() {
    final String[] character = new String[] {
      " _ ",
      "| |",
      "|_|",
      "   "};
    final int result = cut.identifyCharacter(character);
    assertThat(result, is(0));
  }

  @Test
  public void splitEntry_shouldProduceAZeroLengthArrayOfStringArraysWhenPassedEmptyArray() {
    final String[][] characters = cut.splitEntry(new String[0]);
    assertThat(characters.length, is(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void splitEntry_shouldFailWhenPassedAStringArrayWhichIsNotFourLinesInLength() {
    final String[] entry = new String[] { "   ", "   ", "   " };
    cut.splitEntry(entry);
  }

  @Test(expected = IllegalArgumentException.class)
  public void splitEntry_shouldFailWhenPassedAStringArrayOfLengthFourContainingLinesWhichAreNotAMultipleOfThreeCharactersInLength() {
    final String[] entry = new String[] { "     ", "     ", "     ", "     " };
    cut.splitEntry(entry);
  }

  @Test(expected = IllegalArgumentException.class)
  public void splitEntry_shouldFailWhenPassedAStringArrayOfLengthFourContainingLinesWhichAreNotAllOfTheSameLength() {
    final String[] entry = new String[] { "   ", "      ", "   ", "   " };
    cut.splitEntry(entry);
  }
}
