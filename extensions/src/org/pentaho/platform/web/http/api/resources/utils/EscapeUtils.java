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
 * Copyright 2006 - 2015 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.platform.web.http.api.resources.utils;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.util.RepositoryPathEncoder;
import org.pentaho.platform.web.http.messages.Messages;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.SerializableString;
import org.codehaus.jackson.io.CharacterEscapes;
import org.codehaus.jackson.io.SerializedString;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class EscapeUtils {
  
  private static final int[] esc;
  static{
      esc  = CharacterEscapes.standardAsciiEscapesForJSON();
      esc[(int)'<'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'>'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'&'] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'\''] = CharacterEscapes.ESCAPE_CUSTOM;
      esc[(int)'\"'] = CharacterEscapes.ESCAPE_CUSTOM;
  }
  
  private static final class HtmlEscapes extends CharacterEscapes {

    @Override
    public
    int[] getEscapeCodesForAscii() {
        return esc;
    }

    @Override
    public
    SerializableString getEscapeSequence(final int i) {
      return new SerializedString(org.apache.commons.lang.StringEscapeUtils.escapeHtml(Character.toString((char) i)));
    }
    
  }

  public class HTMLCharacterEscapes extends CharacterEscapes
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

  // and then an example of how to apply it
  public ObjectMapper getEscapingMapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());
      return mapper;
  }
  public ObjectMapper getRawEscapingMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());
    return mapper;
}

  public String escapeJsonValues1( String text ) {
    if (text == null) {
      return null;
    }
    String result = null;
    try {
      ObjectMapper om = new ObjectMapper();
      JsonNode node = om.readTree( text );

      org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
      objectMapper.getJsonFactory().setCharacterEscapes(new HtmlEscapes());
      result = objectMapper.writeValueAsString(node);
    } catch (Error e) {
      e.printStackTrace();
    } catch ( JsonGenerationException e ) {
      e.printStackTrace();
    } catch ( JsonMappingException e ) {
      e.printStackTrace();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    if (result == null) {
      result = org.apache.commons.lang.StringEscapeUtils.escapeHtml(text);
    }
    return result;
    
  }

  public String escapeJsonValues2( String text ) {
    if (text == null) {
      return null;
    }
    String result = null;
    final ObjectMapper escapingMapper = getEscapingMapper();
    try { // escape only JSON values and keys
      ObjectMapper om = new ObjectMapper();
      JsonNode node = om.readTree( text );
      
      result = escapingMapper.writeValueAsString(node);
    } catch ( Exception e ) {
      //TODO log debug
      e.printStackTrace();
    }
    if (result == null) {
      // Failed escaping as JSON
      // Escape raw text
      result = org.apache.commons.lang.StringEscapeUtils.escapeHtml(text);
    }
    return result;
    
  }
  public String escapeJsonValues3( String text ) {
    if (text == null) {
      return null;
    }
    ObjectMapper om = new ObjectMapper();
    final ObjectMapper escapingMapper = getEscapingMapper();
    String result = null;
    try { // escape only JSON values and keys
      JsonNode node = om.readTree( text );
      
      result = escapingMapper.writeValueAsString(node);
    } catch ( Exception e ) {
      //TODO log debug
      e.printStackTrace();
    }
    if (result == null) {
      // Failed escaping as JSON
      // Escape raw text
      try { // escape only JSON values and keys
        result = escapingMapper.writeValueAsString(text);
      } catch ( Exception e ) {
        //TODO log debug
        e.printStackTrace();
      }
    }
    return result;
  }

  public String escapeJsonValues( String text ) {
    return escapeJsonValues3( text );
  }

}
