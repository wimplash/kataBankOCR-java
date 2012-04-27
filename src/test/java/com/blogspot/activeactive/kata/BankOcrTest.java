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
    expected.add(" _     _  _     _  _  _  _ ");
    expected.add("| |  | _| _||_||_ |_   ||_|");
    expected.add("|_|  ||_  _|  | _||_|  ||_|");
    expected.add("                           ");
    expected.add("    _  _     _  _  _  _  _ ");
    expected.add("  | _| _||_||_ |_   ||_||_|");
    expected.add("  ||_  _|  | _||_|  ||_| _|");
    expected.add("                           ");
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
    expected.add(" _     _  _     _  _  _  _ ");
    expected.add("| |  | _| _||_||_ |_   ||_|");
    expected.add("|_|  ||_  _|  | _||_|  ||_|");
    expected.add("                           ");
    expected.add("    _  _     _  _  _  _  _ ");
    expected.add("  | _| _||_||_ |_   ||_||_|");
    expected.add("  ||_  _|  | _||_|  ||_| _|");
    expected.add("                           ");
    assertThat(results, is(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readFile_shouldThrowIllegalArgumentExceptionWhenFileDoesNotExist() throws Exception {
    cut.readFile("not/a/real/.file");
  }

  @Test
  public void parseFile_shouldReturnAListOfStringsContainingTheParsedDigits() throws Exception {
    final List<String> results = cut.parseFile("src/test/java/.test");
    final List<String> expected = new ArrayList<String>();
    expected.add("012345678");
    expected.add("123456789");
    assertThat(results, is(expected));
  }
}
