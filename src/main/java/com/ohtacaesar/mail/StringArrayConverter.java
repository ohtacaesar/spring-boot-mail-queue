package com.ohtacaesar.mail;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[], String> {

  @Override
  public String convertToDatabaseColumn(String[] attribute) {
    if (attribute == null) {
      return "";
    }
    return String.join(",", attribute);
  }

  @Override
  public String[] convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return new String[0];
    }
    return dbData.split(",");
  }
}
