/**
 * Phresco Commons
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photon.phresco.util;

public interface ServiceConstants {

	String REST_API_PROJECT =  "/project";
	String REST_API_PROJECT_CREATE =  "/create";
	String REST_API_PROJECT_UPDATE =  "/update";
	String REST_APP_UPDATEDOCS = "/updatedocs";
	String REST_API_COMPONENT =  "/components";
	String REST_API_FRAMEWORK_COMPONENT = "/framwork/components";
	String REST_API_ADMIN =  "/admin";
	String REST_API_CUSTOMERS = "/customers";
	String REST_API_ICON = "/icon";
	String REST_API_APPTYPES = "/apptypes";
	String REST_API_PROPERTY = "/property";
	String REST_API_CONFIG_TEMPLATES= "/configtemplates";
	String REST_API_MODULES= "/modules";
	String REST_API_MODULES_DESC= "/modules/desc";
	String REST_API_PILOTS = "/pilots";
	String REST_API_ARTIFACTINFO = "/artifactInfo";
	String REST_API_SERVERS = "/servers";
	String REST_API_DATABASES = "/databases";
	String REST_API_WEBSERVICES = "/webservices";
	String REST_API_REPORTS = "/reports";
	String REST_API_OPTIONS = "/options";
	String REST_API_OPTIONS_FUNCTIONAL = "/options/functional";
	String REST_API_OPTIONS_CUSTOMER = "/options/customer";
	String REST_API_TECHGROUPS = "/techgroups";
	String REST_API_TECHNOLOGIES = "/technologies";
	String REST_API_DOWNLOADS = "/downloads";
	String REST_API_GLOBALURL ="/globalurl";
	String REST_API_VIDEOS = "/videos";
	String REST_API_USERS = "/users";
	String REST_API_USERS_IMPORT = "/users/import";
	String REST_API_ROLES = "/roles";
	String REST_API_VERSION = "/version";
	String REST_API_FORUMS = "/forums";
	String REST_API_PERMISSIONS = "/permissions";
	String REST_API_LDAP = "/settings/ldap";
	String REST_API_SETTINGS = "/settings";
	String REST_API_PLATFORMS = "/platforms";
	String REST_API_TWEETS = "/tweets";
	String REST_API_LICENSE = "/licenses";
	String REST_API_JSBYID = "/modules/js";
	String REST_API_PILOTSBYID = "/pilots/id";
	String REST_API_LOGIN = "/login";
	String REST_API_ENV_PATH = "/settings/env";
	String REST_LOGIN_PATH = "/service/rest/api/login";
	String REST_API_REPO = "/repo";
	String REST_API_CI_CONFIG = "/ci/config";
	String REST_API_CI_CREDENTIAL = "/ci/credentialsxml";
	String REST_API_CI_JDK_HOME = "/ci/javahomexml";
	String REST_API_CI_MAVEN_HOME = "/ci/mavenhomexml";
	String REST_API_CI_MAILER_HOME = "/ci/mailxml";
	String REST_API_CI_MAIL_PLUGIN = "/ci/emailext";
	String REST_API_LOG = "/log";
	String REST_API_LDAP_PARAM_ID = "ldap";
	String REST_API_PATH_ID = "/{id}";
	String REST_API_PATH_PARAM_ID = "id";
	String REST_API_QUERY_PARAM_SESSION_ID = "sessionId";
	String REST_API_QUERY_PARAM_LIMIT = "limit";
	String REST_API_QUERY_PARAM_OFFSET = "offset";
	String ERROR_MSG_UNSUPPORTED_OPERATION = "{0} operation is not allowed";
	String ERROR_MSG_NOT_FOUND = "Content Not Found";
	String ERROR_MSG_ID_NOT_EQUAL = "Given Id Not Equal";
	String REST_QUERY_TECHID = "techId";
	String REST_QUERY_ISPILOT = "pilot";
	String REST_LIMIT_VALUE = "limitValue";
	String REST_SKIP_VALUE = "skipValue";
	String REST_QUERY_PROJECTID = "projectId";
	String REST_QUERY_TYPE = "type";
	String REST_QUERY_PLATFORM = "platform";
	String REST_QUERY_TYPE_MODULE = "module";
	String REST_QUERY_TYPE_COMPONENT = "component";
	String REST_QUERY_TYPE_JS = "js";
	String REST_QUERY_ID = "id";
	String REST_QUERY_CUSTOMERID = "customerId";
	String REST_QUERY_APPLIESTO = "appliesTo";
	String REST_QUERY_APPTYPEID = "appTypeId";
	String DEFAULT_CUSTOMER_NAME = "photon";
	String PROJECT_NAME = "name";
	String DEFAULT_REPO = "default";
	String APPTYPE = "appType";
	String LOGIN = "login";
	String ADMIN_CUSTOMER = "admin/customers";
	String FEATURE_TYPE_JS = "JAVASCRIPT";
	String JS_GROUP_ID = "jslibraries.files";
	String ICON_EXT = "png";
	String UPDATE_REPO_ID = "updateRepo";
	String VIDEO_REPO_ID  = "videoRepo";
	
	 /*
     * Constants for MongoDB Collections
     */
	String CUSTOMERS_COLLECTION_NAME = "customers";
	String FORUM_COLLECTION_NAME = "forums";
	String VIDEOS_COLLECTION_NAME = "videos";
	String VIDEODAO_COLLECTION_NAME = "videoDAOs";
	String VIDEOTYPES_COLLECTION_NAME = "videotypes";
	String VIDEOTYPESDAO_COLLECTION_NAME = "videotypeDAOs";
	String USERS_COLLECTION_NAME = "users";
	String LOG_COLLECTION_NAME = "adminLog";
	String DOWNLOAD_COLLECTION_NAME = "downloads";
	String GLOBALURL_COLLECTION_NAME="globalurl";
	String APPTYPES_COLLECTION_NAME = "apptypes";
	String PILOTS_COLLECTION_NAME = "pilots";
	String SERVERS_COLLECTION_NAME = "servers";
	String DATABASES_COLLECTION_NAME = "databases";
	String WEBSERVICES_COLLECTION_NAME = "webservices";
	String SETTINGS_COLLECTION_NAME = "settings";
	String TECHNOLOGIES_COLLECTION_NAME = "technologies";
	String USERDAO_COLLECTION_NAME = "userdaos";
	String ROLES_COLLECTION_NAME = "roles";
	String PERMISSION_COLLECTION_NAME = "permissions";
	String APPTYPESDAO_COLLECTION_NAME = "apptypedao";
	String CUSTOMERDAO_COLLECTION_NAME = "customers";
	String CREATEDPROJECTS_COLLECTION_NAME = "createdprojects";
	String ARCHETYPEINFO_COLLECTION_NAME = "archetypes";
	String ARTIFACT_GROUP_COLLECTION_NAME = "artifactGroupDAOs";
	String ARTIFACT_INFO_COLLECTION_NAME = "artifactInfos";
	String APPLICATION_INFO_COLLECTION_NAME = "applicationInfos";
	String PLATFORMS_COLLECTION_NAME = "platforms";
	String REPORTS_COLLECTION_NAME = "reports";
	String PROPERTIES_COLLECTION_NAME = "properties";
	String OPTIONS_COLLECTION_NAME = "options";
	String FUNCTIONAL_FRAMEWORK_COLLECTION_NAME = "functionalFrameworks";
	String CUSTOMER_OPTIONS_COLLECTION_NAME = "customerOptions";
	String LICENSE_COLLECTION_NAME ="Licenses";
	String TECH_GROUP_COLLECTION_NAME = "techgroup";
	String TECH_GROUP_ID = "techGroupIds";
	String FRAMEWORK_THEME_COLLECTION_NAME = "frameworkTheme";
	String REPOINFO_COLLECTION_NAME = "repoInfo";
	String ARTIFACT_ELEMENT_COLLECTION_NAME = "artifactElement";
	
	/*
	 * DB query params
	 */
	String DB_COLUMN_CUSTOMERIDS = "customerIds";
    String DB_COLUMN_ARTIFACT_GROUP_ID = "artifactGroupId";
    String DB_COLUMN_ARTIFACT_GROUP_TYPE = "type";
    String DB_COLUMN_TECHID = "techId";
    String DB_COLUMN_VERSIONIDS = "versionIds";
    String DB_COLUMN_APPLIESTOTECHID = "appliesTo.techId";
	String DB_COLUMN_CREATIONDATE = "creationDate";
	String DB_COLUMN_VIDEOINFOID = "videoInfoId";
	String DB_COLUMN_PLATFORM = "platformTypeIds";

	
	/*
     * Constants for Exception Message keys
     */
	String EX_PHEX00001 = "PHEX00001";
	String EX_PHEX00002 = "PHEX00002";
	String EX_PHEX00003 = "PHEX00003";
	String EX_PHEX00004 = "PHEX00004";
	String EX_PHEX00005 = "PHEX00005";
	String EX_PHEX00006 = "PHEX00006";
	String EX_PHEX00007 = "PHEX00007";
	
	/*
     * Constants for Operatins
     */
	String UPDATE = "Update";
	String INSERT = "Insert";
	String DELETE = "Delete";
	
	/*
	 * Constants for URL in ServiceBaseAction
	 */
	String COLON_DOUBLE_SLASH = "://";
	String COLON = ":";
	String SLASH_REST_SLASH_API = "/rest/api";
	String OPEN_PHRASE = "{";
	String CLOSE_PHRASE = "}";
	
	/*
     * Constants for Fields
     */
	String REST_API_FIELD_TECH = "technologies";
	String REST_API_FIELD_APPID = "appTypeId";
	String REST_API_USED = "used";
	String REST_API_NAME = "name";
	String PASSWORD = "password";
	String AUTHTYPE = "authType";
	String REST_API_ARTIFACTID = "artifactId";
	String REST_API_MODULEID = "moduleGroupId";
	String CATEGORY = "category";
	String SERVER = "Server";
	String DATABASE = "Database";
	String APPLIES_TO_TECHIDS = "appliesToTechIds";
	String TECHINFO_VERSION = "techInfo.version";
	/*
     * Constants for Media Type
     */
	String MEDIATYPE_ZIP = "application/zip";
	
	/*
     * Constants for Response Code
     */
	int RES_CODE_200 = 200;
	int RES_CODE_201 = 201;
	
	/*
     * Constants for Media Type
     */
	String REPOTYPE_RELEASE = "RELEASE";
	String REPOTYPE_SNAPSHOT = "SNAPSHOT";
	String REPOTYPE_GROUP = "group";
	String REPO_LOCAL="/service/local/repositories/";
	String REPO_GROUPURL = "/service/local/repo_groups/";
	String REPO_GROUP_PATH = "/service/local/repo_groups?undefined";
	String REPO_GROUP_CONTENT = "/content/groups/";
	String REPO_HOSTED_PATH = "/service/local/repositories?undefined";
	String REPO_HOSTED_CONTENT = "/content/repositories/";
	String REPO_PROVIDER = "maven2";
	String REPO_PROVIDER_ROLE = "org.sonatype.nexus.proxy.repository.Repository";
	String REPO_ALLOW_WRITE = "ALLOW_WRITE";
	int NOT_FOUND_CACHE = 1440;
	String REPO_HOSTED = "hosted";
	String SLASH = "\"";
	String FORWARD_SLASH="/";
	String DOT=".";
	String CONTENT="/content/";
	String LOCAL_REPO_GROUP = "/service/local/repo_groups/";
	String REPO_RELEASE_NAME = "release";
	String REPO_SNAPSHOT_NAME = "snapshot";
	String REPO_OBJECT_ID = "data";
	String PHRESCO_REPO_NAME = "Releases";
	String REPO_FAILURE_MSG = "Repository Creation Failed Repository Already Exists......";
	
	/*
     * Constants for Object Validation
     */
	String VAL_ID_MSG = "Id Should Not Be Null";
	String VAL_NAME_MSG = "Name Should Not Be Null";
	String VAL_APPID_MSG = "ApptypeId Should Not Be Null";
	String VAL_ARCHETYPE_MSG = "Archetype Should Not Be Null";
	String VAL_TECHGRPID_MSG = "TechnologyGroupId Should Not Be Null";
	String VAL_CUSID_MSG = "Customerids Should Not Be Null";
	
}