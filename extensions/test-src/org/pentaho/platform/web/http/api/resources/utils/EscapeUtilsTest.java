package org.pentaho.platform.web.http.api.resources.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Assert;
import org.junit.Test;


public class EscapeUtilsTest {

  public EscapeUtilsTest() {
    // TODO Auto-generated constructor stub
  }

  EscapeUtils eu = new EscapeUtils();

  public static void main( String[] args ) {
    System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
    EscapeUtils eu = new EscapeUtils();
    Assert.assertEquals( "[1]", eu.escapeJsonValues( "[1]" ) );
    Assert.assertEquals( "[\"asdf\",\"&lt;xxx&gt;\"]", eu.escapeJsonValues( "[\"asdf\",\"<xxx>\"]" ) );
  }
  @Test
  public void test0(  ) {
    System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
    Assert.assertEquals( "[1]", eu.escapeJsonValues( "[1]" ) );
  }  
  @Test
  public void test1( ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "[\"as<>df\",\"<xxx>\"]";
      String actual = eu.escapeJsonValues( src);
      final String expect = "[\"as\\u003C\\u003Edf\",\"\\u003Cxxx\\u003E\"]";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }
  }  
  @Test
  public void test2( ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "[\"asdf\",123]";
      String actual = eu.escapeJsonValues( src);
      final String expect = "[\"asdf\",123]";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }
  }  
  @Test
  public void test3( ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "{\"as&df\":\"<xxx>\", \"AS\" : \"zz\", \"X\":null}";
      String actual = eu.escapeJsonValues( src);
      final String expect = "{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"AS\":\"zz\",\"X\":null}";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }

  }
  @Test
  public void test4( ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "{\"as&df\":\"<xxx>\", \"A\\\"S\" : \"z\\\"z\", \"X\":[123,\"\",\">\\\\<\"]}";
      String actual = eu.escapeJsonValues( src);
      final String expect = "{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"A\\\"S\":\"z\\\"z\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }

  }
  @Test
  public void test10(  ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "1";
      String actual = eu.escapeJsonValues( src);
      final String expect = "1";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }
  }  

  //@Test
  public void test11(  ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "{{\"as&df\":\"<xxx>\", \"AS\" : \"zz\", \"X\":[123,\"\",\">\\\\<\"]}";
      String actual = eu.escapeJsonValues( src);
      final String expect = "{{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"AS\":\"zz\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }

  }

  //@Test
  public void test20(  ) {
    {
      System.out.println((new Throwable()).getStackTrace()[0].getMethodName());
      final String src = "{{\"as&df\":\"<xxx>\", \"AS\" : \"zz\", \"X\":[123,\"\",\">\\\\<\"]}";
      String actual = StringEscapeUtils.escapeJava( src );
      final String expect = "{{\"as\\u0026df\":\"\\u003Cxxx\\u003E\",\"AS\":\"zz\",\"X\":[123,\"\",\"\\u003E\\\\\\u003C\"]}";
      
      for (char c : src.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : actual.toCharArray()) System.out.print( c ); System.out.println();
      for (char c : expect.toCharArray()) System.out.print( c ); System.out.println();
      
      Assert.assertEquals( expect, actual );
      
      System.out.println("   src.json: " + JSON.toString( JSON.parse( src )));
      System.out.println("actual.json: " + JSON.toString( JSON.parse( actual )));
      System.out.println("expect.json: " + JSON.toString( JSON.parse( expect )));
      Assert.assertEquals( "JSON", JSON.toString( JSON.parse( src )), JSON.toString( JSON.parse( actual )) );
    }

  }
}
