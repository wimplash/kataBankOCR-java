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
}
