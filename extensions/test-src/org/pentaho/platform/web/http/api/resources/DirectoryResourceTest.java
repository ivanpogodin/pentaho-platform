/*
 * Copyright 2002 - 2016 Pentaho Corporation.  All rights reserved.
 *
 * This software was developed by Pentaho Corporation and is provided under the terms
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. TThe Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

package org.pentaho.platform.web.http.api.resources;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.platform.api.engine.IAuthorizationPolicy;
import org.pentaho.platform.api.engine.IContentGenerator;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.PentahoAccessControlException;
import org.pentaho.platform.api.repository2.unified.IRepositoryContentConverterHandler;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.UnifiedRepositoryAccessDeniedException;
import org.pentaho.platform.engine.core.output.SimpleOutputHandler;
import org.pentaho.platform.engine.core.solution.SimpleParameterProvider;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.plugin.services.importexport.Exporter;
import org.pentaho.platform.plugin.services.importexport.StreamConverter;
import org.pentaho.platform.repository2.unified.webservices.DefaultUnifiedRepositoryWebService;
import org.pentaho.platform.repository2.unified.webservices.LocaleMapDto;
import org.pentaho.platform.repository2.unified.webservices.RepositoryFileAclDto;
import org.pentaho.platform.repository2.unified.webservices.RepositoryFileDto;
import org.pentaho.platform.repository2.unified.webservices.RepositoryFileTreeDto;
import org.pentaho.platform.repository2.unified.webservices.StringKeyStringValueDto;
import org.pentaho.platform.security.policy.rolebased.actions.PublishAction;
import org.pentaho.platform.web.http.api.resources.services.FileService;
import org.pentaho.platform.web.http.api.resources.utils.FileUtils;
import org.pentaho.platform.web.http.messages.Messages;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.IllegalSelectorException;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DirectoryResourceTest {
  private static final String XML_EXTENSION = "xml";
  private static final String PATH_ID = "pathId.xml";
  private static final String PATH_ID_WITHOUTH_EXTENSION = "pathId";
  private static final String PATH_ID_INCORRECT_EXTENSION = "pathId.wrong";
  private static final String NAME_NEW_FILE = "nameNewFile.xml";
  private static final String NAME_NEW_FILE_WITHOUTH_EXTENSION = "nameNewFile";
  private static final String NAME_NEW_FILE_WRONG_EXTENSION = "nameNewFile.wrong";

  private static final String FILE_ID = "444324fd54ghad";
  private DirectoryResource directoryResource;

  @Before
  public void setUp() {
    directoryResource = spy( new DirectoryResource() );
    directoryResource.fileService = mock( FileService.class );
    directoryResource.httpServletRequest = mock( HttpServletRequest.class );
  }

  @After
  public void tearDown() {
    directoryResource = null;
  }

  @BeforeClass
  public static void initTest() {
    IRepositoryContentConverterHandler handler = mock( IRepositoryContentConverterHandler.class );
    when( handler.getConverter( XML_EXTENSION ) ).thenReturn( new StreamConverter() );
    PentahoSystem.registerObject( handler );
  }


  @Test
  public void testCreateDirectory() throws Exception {
    String charsetName = "charsetName";
    InputStream mockInputStream = mock( InputStream.class );

    doNothing().when( directoryResource.fileService ).createFile( charsetName, PATH_ID, mockInputStream );

    Response mockResponse = mock( Response.class );
    doReturn( mockResponse ).when( directoryResource ).buildOkResponse();

    doReturn( charsetName ).when( directoryResource.httpServletRequest ).getCharacterEncoding();

    Response testResponse = directoryResource.createDirs( PATH_ID );

    assertEquals( mockResponse, testResponse );
    verify( directoryResource.fileService, times( 1 ) ).createFile( charsetName, PATH_ID, mockInputStream );
    verify( directoryResource, times( 1 ) ).buildOkResponse();
  }

  @Test
  public void testCreateDirsError() throws Exception {
    String charsetName = "charsetName";
    InputStream mockInputStream = mock( InputStream.class );

    Exception mockException = mock( RuntimeException.class );

    doThrow( mockException ).when( directoryResource.fileService )
      .createFile( charsetName, PATH_ID, mockInputStream );

    doReturn( charsetName ).when( directoryResource.httpServletRequest ).getCharacterEncoding();

    Response mockResponse = mock( Response.class );
    doReturn( mockResponse ).when( directoryResource ).buildServerErrorResponse( mockException );

    Response testResponse = directoryResource.createDirs( PATH_ID );

    assertEquals( mockResponse, testResponse );
    verify( directoryResource, times( 1 ) ).buildServerErrorResponse( mockException );
    verify( directoryResource.httpServletRequest, times( 1 ) ).getCharacterEncoding();
    verify( directoryResource.fileService ).createFile( charsetName, PATH_ID, mockInputStream );
  }

}
