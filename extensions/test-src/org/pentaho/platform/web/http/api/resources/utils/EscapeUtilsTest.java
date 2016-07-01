package org.pentaho.platform.web.http.api.resources.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Assert;
import org.junit.Test;

public class EscapeUtilsTest {

  public EscapeUtilsTest() {
    // TODO Auto-generated constructor stub
  }

  public static void main( String[] args ) {
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    EscapeUtils eu = new EscapeUtils();
    Assert.assertEquals( "[1]", EscapeUtils.escapeJsonOrHtml( "[1]" ) );
    Assert.assertEquals( "[\"asdf\",\"&lt;xxx&gt;\"]", eu.escapeJsonOrHtml( "[\"asdf\",\"<xxx>\"]" ) );
  }

  @Test
  public void test0() {
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    Assert.assertEquals( "[1]", EscapeUtils.escapeJsonOrHtml( "[1]" ) );
  }

  @Test
  public void test1() {
    System.out.println();
    System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
    final String src = "[\"as<>df\",\"<xxx>\"]";
    final String expect = "[\"as\\u003C\\u003Edf\",\"\\u003Cxxx\\u003E\"]";
    //final String expect = "[\"as&lt;&gt;df\",\"&lt;xxx&gt;\"]";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrHtml( src );

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

    String actual = EscapeUtils.escapeJsonOrHtml( src );

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

    String actual = EscapeUtils.escapeJsonOrHtml( src );

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
        "{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"A\\\"S\":\"z\\\"z\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );

    String actual = EscapeUtils.escapeJsonOrHtml( src );

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

    String actual = EscapeUtils.escapeJsonOrHtml( src );

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
    final String expect = "{as&amp;df&lt;html&gt;&quot;\\123";
    System.out.println( "                src: " + src );
    System.out.println( "             expect: " + expect );
    System.out.println( "expect.unescapeHtml: " + StringEscapeUtils.unescapeHtml( expect ) );
    Assert.assertEquals( "HTML", src, StringEscapeUtils.unescapeHtml( expect ) );

    String actual = EscapeUtils.escapeJsonOrHtml( src );

    System.out.println( "        src: " + src );
    System.out.println( "     expect: " + expect );
    System.out.println( "     actual: " + actual );

    Assert.assertEquals( expect, actual );
  }

  // @Test
  public void test20() {
    {
      System.out.println( ( new Throwable() ).getStackTrace()[0].getMethodName() );
      final String src = "{{\"as&df\":\"<xxx>\", \"AS\" : \"zz\", \"X\":[123,\"\",\">\\\\<\"]}";
      String actual = StringEscapeUtils.escapeJava( src );
      final String expect =
          "{{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"AS\":\"zz\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";

      for ( char c : src.toCharArray() )
        System.out.print( c );
      System.out.println();
      for ( char c : actual.toCharArray() )
        System.out.print( c );
      System.out.println();
      for ( char c : expect.toCharArray() )
        System.out.print( c );
      System.out.println();

      Assert.assertEquals( expect, actual );

      System.out.println( "   src.json: " + JSON.toString( JSON.parse( src ) ) );
      System.out.println( "actual.json: " + JSON.toString( JSON.parse( actual ) ) );
      System.out.println( "expect.json: " + JSON.toString( JSON.parse( expect ) ) );
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( actual ) ) );
    }

  }

  @Test
  public void testEscapeHtmlInJson() {
    final String src = "[\"as<>df\",\"<xxx>\"]";
    final String expect = "[\"as\\u003C\\u003Edf\",\"\\u003Cxxx\\u003E\"]";
    //ensure the test to be correct
    Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src ) ), JSON.toString( JSON.parse( expect ) ) );
    //tested code
    String actual = EscapeUtils.escapeHtmlInJson( src );
    
    Assert.assertEquals( expect, actual );
  }
}
