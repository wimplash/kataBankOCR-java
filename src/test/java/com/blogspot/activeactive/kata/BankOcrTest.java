package com.blogspot.activeactive.kata;

import org.junit.Test;
import java.util.Collection;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BankOcrTest  {

  @Test
  public void cut_shouldExist() {
    assertThat(new BankOcr(), is(notNullValue()));
  }

  @Test
  public void parseLines_shouldAcceptACollectionOfStringValues() {
    final BankOcr cut = new BankOcr();
    final Collection<String> lines = new ArrayList<String>();
    cut.parseLines(lines);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseLines_shouldRejectANullValuedCollection() {
    final BankOcr cut = new BankOcr();
    cut.parseLines(null);
  }
}
