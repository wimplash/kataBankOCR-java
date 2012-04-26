/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

public enum Numeral {
  UNKNOWN(-1, null),
  ZERO(0, new String[] {
  " _ ",
  "| |",
  "|_|",
  "   "}),
  ONE(1, new String[] {
  "   ",
  "  |",
  "  |",
  "   "}),
  TWO(2, new String[] {
  " _ ",
  " _|",
  "|_ ",
  "   "}),
  THREE(3, new String[] {
  " _ ",
  " _|",
  " _|",
  "   "}),
  FOUR(4, new String[] {
  "   ",
  "|_|",
  "  |",
  "   "}),
  FIVE(5, new String[] {
  " _ ",
  "|_ ",
  " _|",
  "   "}),
  SIX(6, new String[] {
  " _ ",
  "|_ ",
  "|_|",
  "   "}),
  SEVEN(7, new String[] {
  " _ ",
  "  |",
  "  |",
  "   "}),
  EIGHT(8, new String[] {
  " _ ",
  "|_|",
  "|_|",
  "   "}),
  NINE(9, new String[] {
  " _ ",
  "|_|",
  " _|",
  "   "});


  public final int value;
  public final String[] representation;

  private Numeral(final int value, final String[] representation) {
    this.value = value;
    this.representation = representation;
  }

  public static Numeral decode(final String[] representation) {
    for (final Numeral numeral : Numeral.values()) {
      if (UNKNOWN.equals(numeral)) {
        continue;
      }
      if (numeral.matches(representation)) {
        return numeral;
      }
    }
    return UNKNOWN;
  }

  protected boolean matches(final String[] representation) {
    for (int i = 0; i < 4; i++) {
      if (! this.representation[i].equals(representation[i])) {
        return false;
      }
    }
    return true;
  }
}
