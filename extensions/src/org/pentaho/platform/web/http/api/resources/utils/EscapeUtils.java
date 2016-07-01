/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License, version 2 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/gpl-2.0.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 *
 * Copyright 2006 - 2016 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.platform.web.http.api.resources.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.SerializableString;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.io.CharacterEscapes;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.eclipse.jetty.util.ajax.JSON;

public class EscapeUtils {
  
  private static class HtmlEscapeStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        String escaped = StringEscapeUtils.escapeHtml4(s);
        jsonGenerator.writeString(escaped);
    }
  }

  public static String escapeHtmlInJson( String text ) {
    if (text == null) {
      return null;
    }
    try { // escape only JSON values and keys
      ObjectMapper mapper1 = new ObjectMapper();
      SimpleModule simpleModule = new SimpleModule("SimpleModule", new Version(1,0,0,null));
      simpleModule.addSerializer(String.class, new HtmlEscapeStringSerializer());
      mapper1.registerModule(simpleModule);
      final Object parsedJson = JSON.parse( text );
      String result = mapper1.writeValueAsString(parsedJson);
      return result;
    } catch ( Exception e ) { // escape raw text
      //TODO log debug
      e.printStackTrace();
      String result = StringEscapeUtils.escapeHtml4(text);
      return result;
    }
  }
  
  private static final int[] esc;
  static{
      esc  = CharacterEscapes.standardAsciiEscapesForJSON();
      esc[(int)'<'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'>'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'&'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'\''] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'\"'] = CharacterEscapes.ESCAPE_CUSTOM;
  }
  
  private static class HTMLCharacterEscapes extends CharacterEscapes
  {
      private final int[] asciiEscapes;
      
      public HTMLCharacterEscapes()
      {
          // start with set of characters known to require escaping (double-quote, backslash etc)
          int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
          // and force escaping of a few others:
          esc['<'] = CharacterEscapes.ESCAPE_STANDARD;
          esc['>'] = CharacterEscapes.ESCAPE_STANDARD;
          esc['&'] = CharacterEscapes.ESCAPE_STANDARD;
          esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
          esc['\"'] = CharacterEscapes.ESCAPE_STANDARD;
          asciiEscapes = esc;
      }
      // this method gets called for character codes 0 - 127
      @Override public int[] getEscapeCodesForAscii() {
          return asciiEscapes;
      }
      // and this for others; we don't need anything special here
      @Override public SerializableString getEscapeSequence(int ch) {
          // no further escaping (beyond ASCII chars) needed:
          return null;
      }
  }

  public static String escapeJson( String text ) throws IOException {
    if (text == null) {
      return null;
    }
    ObjectMapper escapingMapper = new ObjectMapper();
    escapingMapper.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());

    JsonNode parsedJson = (new ObjectMapper()).readTree( text );
    String result = escapingMapper.writeValueAsString(parsedJson);
    return result;
  }

  public static String escapeRaw( String text ) {
    if (text == null) {
      return null;
    }
    ObjectMapper escapingMapper = new ObjectMapper();
    escapingMapper.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());

    String result = null;
    try {
      result = escapingMapper.writeValueAsString( text );
    } catch ( Exception e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result.substring( 1, result.length()-1 );//unquote
  }

  public static String escapeJsonOrRaw( String text ) {
    if (text == null) {
      return null;
    }
    try {
      return escapeJson( text );
    } catch ( Exception e ) {
      //TODO log debug
      return escapeRaw(text);
    }
  }

}
