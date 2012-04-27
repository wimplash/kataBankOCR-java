/**
 * This code, and the development history behind it can be found at:
 * http://www.github.com/wimplash/kataBankOCR-java
 */
package com.blogspot.activeactive.kata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankOcr {

  public static void main(final String[] args) throws Exception {
    String inputFile = null;
    String outputFile = null;
    if (args == null || args.length == 0) {
      throw new IllegalArgumentException("You must provide one or two"
          + " arguments. The first argument is the input file, the second"
          + " argument is the output file. If an output file is not provided"
          + " program output will go into './output.txt'.");
    } else if (args.length == 1) {
      inputFile = args[0];
      outputFile = "output.txt";
    } else {
      inputFile = args[0];
      outputFile = args[1];
    }
    new BankOcr().parseAndWrite(inputFile, outputFile);
  }

  public void parseAndWrite(final String inputFilename,
      final String outputFilename) throws IOException {
    writeToFile(parseFile(inputFilename), outputFilename);
  }

  protected void writeToFile(final List<String> lines, final String filename)
      throws IOException {
    final File f = new File(filename);
    f.createNewFile();
    final FileWriter fw = new FileWriter(f);
    final BufferedWriter w = new BufferedWriter(fw);
    for (int i = 0; i < lines.size(); i++) {
      if (i != 0) {
        w.newLine();
      }
      w.write(lines.get(i));
    }
    w.flush();
    w.close();
  }

  protected List<String> readFile(final String filename) throws IOException {
    final BufferedReader br = createReader(filename);
    final List<String> results = new ArrayList<String>();
    String line = br.readLine();
    while (line != null) {
      results.add(line);
      line = br.readLine();
    }
    return results;
  }

  public List<Entry> readEntries(final List<String> contents) {
    validateContents(contents);

    final List<Entry> result = new ArrayList<Entry>();
    Entry entry = new Entry();
    for (final String line : contents) {
      entry.addLine(line);
      if (entry.isFull()) {
        result.add(entry);
        entry = new Entry();
      }
    }
    return result;
  }

  public List<String> parseFile(final String filename) throws IOException {
    final List<String> contents = readFile(filename);
    final List<Entry> entries = readEntries(contents);
    final List<String> result = new ArrayList<String>();
    for (final Entry entry : entries) {
      result.add(entry.toString());
    }
    return result;
  }

  private BufferedReader createReader(final String filename) throws IOException {
    validateFilename(filename);
    final File f = new File(filename.trim());
    validateFile(f);

    /*
     * Ideally, in a language other than java, I could find a way to
     * mock an un-readable file in the tests to exercise this line, but
     * I cannot easily do that here.
     */
    final FileReader fr = new FileReader(f);
    return new BufferedReader(fr);
  }

  private void validateFile(final File file) {
    if (! file.exists()) {
      throw new IllegalArgumentException("The filename parameter is required"
          + " but refers to a file which does not exist.");
    }
  }

  private void validateFilename(final String filename) {
    if (filename == null || "".equals(filename.trim())) {
      throw new IllegalArgumentException("The filename parameter is required"
          + " and must not be null-valued, empty, or contain only whitespace.");
    }
  }

  private void validateContents(final List<String> contents) {
    if (contents == null || contents.isEmpty()) {
      throw new IllegalArgumentException("The contents parameter is required and"
          + " must not be null-valued or empty.");
    }
    if ((contents.size() % 4) != 0) {
      throw new IllegalArgumentException("Each valid entry in the contents"
          + " parameter must contain exactly four lines. The contents parameter"
          + " does not contain a whole number of valid account number entries.");
    }
  }
}
