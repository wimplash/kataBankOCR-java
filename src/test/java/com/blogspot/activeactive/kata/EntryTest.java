/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import org.junit.Test;

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
}
