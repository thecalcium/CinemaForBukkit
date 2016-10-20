package de.codolith.Cinema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class VersionChecker {
	
	public static final int projectID=38636;
	
	// Keys for extracting file information from JSON response
    private static final String API_NAME_VALUE = "name";
    private static final String API_LINK_VALUE = "downloadUrl";
    private static final String API_RELEASE_TYPE_VALUE = "releaseType";
    private static final String API_FILE_NAME_VALUE = "fileName";
    private static final String API_GAME_VERSION_VALUE = "gameVersion";

    // Static information for querying the API
    private static final String API_QUERY = "/servermods/files?projectIds=";
    private static final String API_HOST = "https://api.curseforge.com";
    
    private String apiKey= null;
    
    public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	private String 	versionName = null,
    				versionLink = null,
					versionType = null,
					versionFileName = null,
					versionGameVersion = null;
	
	public String getVersionName() {
		return versionName;
	}

	public String getVersionLink() {
		return versionLink;
	}

	public String getVersionType() {
		return versionType;
	}

	public String getVersionFileName() {
		return versionFileName;
	}

	public String getVersionGameVersion() {
		return versionGameVersion;
	}

	public VersionChecker(){
		this(null);
	}
	
	public VersionChecker(String apiKey){
		this.apiKey = apiKey;
	}
	
	public void check(){
		URL url = null;

        try {
            // Create the URL to query using the project's ID
            url = new URL(API_HOST + API_QUERY + projectID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        try {
            // Open a connection and query the project
            URLConnection conn = url.openConnection();

            if (apiKey != null) {
                // Add the API key to the request if present
                conn.addRequestProperty("X-API-Key", apiKey);
            }

            // Add the user-agent to identify the program
            conn.addRequestProperty("User-Agent", " Cinema/v"+Cinema.version+" (by fredlllll)");

            // Read the response of the query
            // The response will be in a JSON format, so only reading one line is necessary.
            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            // Parse the array of files from the query's response
            JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.size() > 0) {
                // Get the newest file's details
                JSONObject latest = (JSONObject) array.get(array.size() - 1);
               
                versionName = (String) latest.get(API_NAME_VALUE); // Get the version's title
                versionLink = (String) latest.get(API_LINK_VALUE); // Get the version's link
                versionType = (String) latest.get(API_RELEASE_TYPE_VALUE); // Get the version's release type
                versionFileName = (String) latest.get(API_FILE_NAME_VALUE); // Get the version's file name
                versionGameVersion = (String) latest.get(API_GAME_VERSION_VALUE); // Get the version's game version
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
	}
}
