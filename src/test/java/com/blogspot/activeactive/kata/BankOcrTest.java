/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    assertThat(results, is(testFileLines()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void main_shouldFailWhenPassedNull() throws Exception {
    BankOcr.main(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void main_shouldFailWhenPassedAnEmptyArray() throws Exception {
    BankOcr.main(new String[0]);
  }

  @Test
  public void main_shouldWriteToDefaultFileWhenPassedOneInput() throws Exception {
    BankOcr.main(new String[] { "src/test/java/.test" });
    assertThat(readFile("output.txt"), is(testFileLines()));
  }

  @Test
  public void main_shouldWriteToSpecifiedFileWhenPassedTwoInputs() throws Exception {
    BankOcr.main(new String[] { "src/test/java/.test", "test.output" });
    assertThat(readFile("test.output"), is(testFileLines()));
  }

  private List<String> readFile(final String filename) throws Exception {
    final File f = new File(filename);
    assertThat(f.exists(), is(true));
    final BufferedReader r = new BufferedReader(new FileReader(f));
    final List<String> results = new ArrayList<String>();
    String line = r.readLine();
    while (line != null) {
      results.add(line);
      line = r.readLine();
    }
    return results;
  }

  private List<String> testFileLines() {
    final List<String> expected = new ArrayList<String>();
    expected.add("012345678");
    expected.add("123456789");
    return expected;
  }

  @After
  public void cleanupFiles() throws Exception {
    new File("test.output").delete();
    new File("output.txt").delete();
  }
}
