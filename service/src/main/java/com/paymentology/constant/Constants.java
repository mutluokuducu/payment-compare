package com.paymentology.constant;

public class Constants {

  public static final String UPLOAD_CSV_FILES = "/api/v1/uploadCsvFiles";

  private static String fileName;

  public static String getFileName() {
    return fileName;
  }

  public static void setFileName(String fileName) {
    Constants.fileName = fileName;
  }

}
