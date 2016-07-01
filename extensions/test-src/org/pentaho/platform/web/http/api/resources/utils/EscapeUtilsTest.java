/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2016 Pentaho Corporation..  All rights reserved.
 */
package org.pentaho.platform.web.http.api.resources.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Assert;
import org.junit.Test;

public class EscapeUtilsTest {

  @Test
  public void test0() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "1";
    final String expect = "1";
    //final String expect = "[\"as&lt;&gt;df\",\"&lt;xxx&gt;\"]";
    Assert.assertEquals( "HTML", src, StringEscapeUtils.unescapeJava( expect ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test1() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "[\"as<>df\",\"<xxx>\"]";
    final String expect = "[\"as\\u003C\\u003Edf\",\"\\u003Cxxx\\u003E\"]";
    //final String expect = "[\"as&lt;&gt;df\",\"&lt;xxx&gt;\"]";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );
    System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
    System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
    System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test2() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "[\"asdf\",123,\"<html>\", \"f&g\"]";
    final String expect = "[\"asdf\",123,\"\\u003Chtml\\u003E\",\"f\\u0026g\"]";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );
    System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
    System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
    System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test3() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "{\"as&df\":\"<xxx>\", \"AS\" : \"zz\", \"X\":null}";
    final String expect = "{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"AS\":\"zz\",\"X\":null}";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );
    System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
    System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
    System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test4() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "{\"as&df\":\"<xxx>\", \"A\\\"S\" : \"z\\\"z\", \"X\":[123,\"\",\">\\\\<\"]}";
    final String expect =
        "{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"A\\u0022S\":\"z\\u0022z\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );
    System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
    System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
    System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test10() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "1";
    final String expect = "1";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );
    System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
    System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
    System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );

    Assert.assertEquals( expect, actual );
  }

  @Test
  public void test11() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "{as&df<html>\"\\123";
    final String expect = "{as\\u0026df\\u003Chtml\\u003E\\u0022\\\\123";
    System.out.println( "                src: " + src );
    System.out.println( "             expect: " + expect );
    System.out.println( "expect.unescapeHtml: " + StringEscapeUtils.unescapeHtml( expect ) );
    Assert.assertEquals( "HTML", src, StringEscapeUtils.unescapeJava( expect ) );

    String actual = EscapeUtils.escapeJsonOrRaw( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );

    Assert.assertEquals( expect, actual );
  }

}
