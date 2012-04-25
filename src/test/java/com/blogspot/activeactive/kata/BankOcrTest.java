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
  public void parseFile_shouldAcceptAListOfStringValues() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _ ");
    }
    cut.parseFile(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseFile_shouldRejectANullValuedList() {
    cut.parseFile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseFile_shouldRejectAnEmptyList() {
    final List<String> lines = new ArrayList<String>();
    cut.parseFile(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseFile_shouldRejectAListWhichDoesNotContainAMultipleOfFourLines() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 3; i++) {
      lines.add("");
    }
    cut.parseFile(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseFile_shouldRejectAListForWhichEachElementDoesNotContainTwentySevenCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add("12345678901234567890123456");
    }
    cut.parseFile(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseFile_shouldRejectAListForWhichAnElementContainsInvalidCharacters() {
    final List<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add(" _ | | _ | | _ | | _ | | _1");
    }
    cut.parseFile(lines);
  }

  @Test
  public void parseFile_shouldSeparateLinesIntoAListOfStringArrays() {
    final String testLine = " _ | | _ | | _ | | _ | | _ ";
    final List<String> testLines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      testLines.add(testLine);
    }
    final List<String[]> result = cut.parseFile(testLines);
    assertThat(result.size(), is(1));
    final String[] entry = result.iterator().next();
    assertThat(entry.length, is(4));
    for (final String line : entry) {
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
}
