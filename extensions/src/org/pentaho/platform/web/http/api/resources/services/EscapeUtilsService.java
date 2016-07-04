package org.pentaho.platform.web.http.api.resources.services;

import org.pentaho.platform.web.http.api.resources.utils.EscapeUtils;

public class EscapeUtilsService implements IEscapeUtilsService{

  public EscapeUtilsService() {
  }

  @Override
  public String escapeJsonOrRaw( String text ) {
    return EscapeUtils.escapeJsonOrRaw( text );
  }

}
