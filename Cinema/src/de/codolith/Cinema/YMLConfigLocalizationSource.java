package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class YMLConfigLocalizationSource implements ILocalizationSource
{
	HashMap<String, HashMap<String, String>> languages = new HashMap<String, HashMap<String, String>>();
	ArrayList<String> availableLabels = new ArrayList<String>();
	private String languageCode;
	private String defaultLanguageCode = "";
	
	public String getDefaultLanguageCode(){
		return defaultLanguageCode;
	}
	
	public void setDefaultLanguageCode(String value){
		defaultLanguageCode = value;
	}

	public String get(String label)
	{
		String retval = label;
		HashMap<String, String> labels = languages.get(languageCode);
		if(labels != null){
			String tmp = labels.get(label);
			if(tmp!= null){
				retval = tmp;
			}
		}		
		return retval;
	}
	
	public String get(String language,String label){
		String retval = label;
		HashMap<String, String> labels = languages.get(language);
		if(labels != null){
			String tmp = labels.get(label);
			if(tmp!= null){
				retval = tmp;
			}
		}
		return retval;
	}
	
	public ArrayList<String> getAvailableLabels()
	{
		return availableLabels;
	}

	
	public void languageCodeChanged(String code)
	{
		if (!languages.containsKey(code))
		{
			languageCode = getDefaultLanguageCode();
		}
		else
		{
			languageCode = code;
		}
	}
	
	public YMLConfigLocalizationSource(){}
	
	public YMLConfigLocalizationSource(File file){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		defaultLanguageCode = config.getString("defaultLanguageCode");
		languageCode = defaultLanguageCode;
		List<String> labels = config.getStringList("labels");
		List<String> langs = config.getStringList("langs");

		for(String label : labels){
			for(String lang : langs){
				AddLabel(label,lang,config.getString(lang+"."+label));
			}
		}
	}
	
	public void save(File file) throws IOException{
		YamlConfiguration config = new YamlConfiguration();
		config.set("defaultLanguageCode", defaultLanguageCode);
		List<String> langs = new ArrayList<String>(languages.keySet());
		List<String> labels = getAvailableLabels();
		/*if(langs.size()>0){
			labels = new ArrayList<String>(languages.get(langs.get(0)).keySet());
		}else{
			labels = new ArrayList<String>();
		}*/
		config.set("labels",labels);
		config.set("langs",langs);
		
		for(String label : labels){
			for(String lang : langs){
				config.set(lang+"."+label, get(lang,label));
			}
		}
		
		config.save(file);
	}
	
	

	/*public void AddLabel(String label, String[] languageCodes, String[] values)
	{
		if (languageCodes.length != values.length)
		{
			throw new Error("\"languageCodes\" and \"values\" have to have the same length.");
		}
		if (!availableLabels.contains(label))
		{
			availableLabels.add(label);
		}
		for (int l = 0; l < languageCodes.length; l++)
		{
			AddLabel(label, languageCodes[l], values[l]);
		}
	}*/

	public void AddLabel(String label, String languageCode, String value)
	{
		if (!availableLabels.contains(label))
		{
			availableLabels.add(label);
		}
		HashMap<String, String> lang = null;
		if (!languages.containsKey(languageCode))
		{
			lang = new HashMap<String, String>();
			languages.put(languageCode,lang);
		}
		else
		{
			lang = languages.get(languageCode);
		}
		lang.put(label, value);
	}
	
	public String toString(){
		String retval = "Labels:\n";
		for(String l : getAvailableLabels()){
			retval+=l+"\n";
		}
		retval+="\nlanguageCode: "+languageCode+"\n";
		retval+="defaultLanguageCode: "+defaultLanguageCode+"\n";
		return retval;
	}
}
