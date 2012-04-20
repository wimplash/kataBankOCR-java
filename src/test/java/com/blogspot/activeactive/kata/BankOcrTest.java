package com.blogspot.activeactive.kata;

import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
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
  public void parseLines_shouldAcceptACollectionOfStringValues() {
    final Collection<String> lines = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      lines.add("");
    }
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectANullValuedCollection() {
    cut.parseLines(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectAnEmptyCollection() {
    final Collection<String> lines = new ArrayList<String>();
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectACollectionWhichDoesNotContainAMultipleOfFourLines() {
    final Collection<String> lines = new ArrayList<String>();
    for (int i = 0; i < 3; i++) {
      lines.add("");
    }
    cut.parseLines(lines);
  }
}
