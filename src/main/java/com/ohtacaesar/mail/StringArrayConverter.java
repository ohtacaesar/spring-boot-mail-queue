package com.ohtacaesar.mail;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[], String> {

  @Override
  public String convertToDatabaseColumn(String[] attribute) {
    if (attribute == null) {
      return null;
    }
    return String.join(",", attribute);
  }

  @Override
  public String[] convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return dbData.split(",");
  }
}
