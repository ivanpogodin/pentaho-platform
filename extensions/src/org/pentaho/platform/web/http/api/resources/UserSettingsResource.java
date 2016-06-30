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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.platform.web.http.api.resources;

import org.codehaus.enunciate.Facet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.usersettings.IUserSettingService;
import org.pentaho.platform.api.usersettings.pojo.IUserSetting;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 * This resource manages the user settings of the platform
 * 
 *
 */
@Path( "/user-settings" )
@Facet( name = "Unsupported" )
public class UserSettingsResource extends AbstractJaxRSResource {

  public UserSettingsResource() {
  }

  private IPentahoSession getPentahoSession() {
    return PentahoSessionHolder.getSession();
  }

  /**
   * Retrieve the global settings and the user settings for the current user
   * 
   * @return list of settings for the platform
   */
  @GET
  @Path( "/list" )
  @Produces( { APPLICATION_JSON, APPLICATION_XML } )
  @Facet ( name = "Unsupported" )
  public ArrayList<Setting> getUserSettings() {
    try {
      IUserSettingService settingsService = PentahoSystem.get( IUserSettingService.class, getPentahoSession() );
      ArrayList<IUserSetting> userSettings = (ArrayList<IUserSetting>) settingsService.getUserSettings();

      ArrayList<Setting> settings = new ArrayList<Setting>();
      for ( IUserSetting userSetting : userSettings ) {
        settings.add( new Setting( userSetting.getSettingName(), userSetting.getSettingValue() ) );
      }

      return settings;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieve a particular user setting for the current user
   * 
   * @param setting (Name of the setting)
   * 
   * @return value of the setting for the user
   */
  @GET
  @Path( "{setting : .+}" )
  @Facet ( name = "Unsupported" )
  public Response getUserSetting( @PathParam( "setting" ) String setting ) {
    IUserSettingService settingsService = PentahoSystem.get( IUserSettingService.class, getPentahoSession() );
    IUserSetting userSetting = settingsService.getUserSetting( setting, null );
    return Response.ok( userSetting != null ? userSetting.getSettingValue() : null ).build();
  }

  /**
   * Save the value of a particular setting for the current user
   * 
   * @param setting  (Setting name)
   * @param settingValue   (Value of the setting)
   * 
   * @return 
   */
  @POST
  @Path( "{setting : .+}" )
  @Facet ( name = "Unsupported" )
  public Response setUserSetting( @PathParam( "setting" ) String setting, String settingValue ) {
    IUserSettingService settingsService = PentahoSystem.get( IUserSettingService.class, getPentahoSession() );

    //preventing stored XSS(PPP-3464)

    settingValue = encodeJsonStringValues( settingValue );
    settingsService.setUserSetting( setting, settingValue );
    return Response.ok( settingValue ).build();
  }
  
  String encodeJsonStringValues(String jsonSrc) {
    if ( jsonSrc == null ) {
      return null;
    }
    Object jsonObj;
    try {
      jsonObj = (new JSONParser()).parse( jsonSrc );
    } catch ( ParseException e ) {
      //TODO: log-debug
      return encodeString(jsonSrc);
    }
    Object encodedJsonObj = encodeJsonStringValues( jsonObj );
    return String.valueOf( encodedJsonObj );
  }
  
  <T> T encodeJsonStringValues(T jsonObj) {
    if ( jsonObj instanceof String ) {
      return (T)encodeString( (String) jsonObj);
    }
    if ( jsonObj instanceof JSONArray ) {
      return (T)encodeArray( (JSONArray) jsonObj);
    }
    if ( jsonObj instanceof JSONObject ) {
      return (T)encodeObject( (JSONObject) jsonObj);
    }
    return jsonObj;
  }

  String encodeString(String text) {
    if ( text == null ) {
      return null;
    }
    return org.apache.commons.lang.StringEscapeUtils.escapeHtml( text );
    //return text.replaceAll( "&", "&amp;" ).replaceAll( "\"", "&quot;" ).replaceAll( "<", "&lt;" ).replaceAll( ">", "&gt;" );
  }
  JSONArray encodeArray(JSONArray array) {
    if ( array == null ) {
      return null;
    }
    JSONArray r = new JSONArray();
    for (Object value : array) {
      final Object encodedValue = encodeJsonStringValues(value);
      r.add( encodedValue );
    }
    return r;
  }
  JSONObject encodeObject(JSONObject object) {
    if ( object == null ) {
      return null;
    }
    JSONObject r = new JSONObject();
    for (Object key : object.keySet() ){
      Object value = object.get( key );
      final Object encodedValue = encodeJsonStringValues(value);
      r.put( key, encodedValue );
    }
    return object;
  }
  

}
