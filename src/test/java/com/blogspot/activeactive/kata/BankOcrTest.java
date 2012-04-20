package com.blogspot.activeactive.kata;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BankOcrTest  {

  @Test
  public void cut_shouldExist() {
    assertThat(new BankOcr(), is(notNullValue()));
  }

  @Test
  public void parse_shouldAcceptAStringValue() {
    final BankOcr cut = new BankOcr();
    cut.parse("");
  }
}
