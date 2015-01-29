package de.codolith.Cinema;

import java.util.ArrayList;
import java.util.HashMap;

public class Localizator
{
	private HashMap<String, ILocalizationSource> labelSources = new HashMap<String, ILocalizationSource>();
	private ArrayList<ILocalizationSource> localizationSources = new ArrayList<ILocalizationSource>();

	public void addLocalizationSource(ILocalizationSource source)
	{
		if(!localizationSources.contains(source))
		{
			localizationSources.add(source);
			for (String label : source.getAvailableLabels())
			{
				labelSources.put(label,source);// = source;
			}
			source.languageCodeChanged(languageCode);
		}
	}

	public void removeLocalizationSource(ILocalizationSource source)
	{
		localizationSources.remove(source);
		for (String label : source.getAvailableLabels())
		{
			labelSources.remove(label);
		}
	}

	private ArrayList<ILocalizable> localizables = new ArrayList<ILocalizable>();

	public void addLocalizable(ILocalizable localizable)
	{
		localizables.add(localizable);
		localizable.loadLocalizedTexts();
	}

	public void removeLocalizable(ILocalizable localizable)
	{
		localizables.remove(localizable);
	}

	private String languageCode = "";
	public String getLanguageCode(){
		return languageCode;
	}
	
	public void setLanguageCode(String value){
		if (value != languageCode)
		{
			languageCode = value;
			for (ILocalizationSource source : localizationSources)
			{
				source.languageCodeChanged(languageCode);
			}
			updateAllLocalizables();
		}
	}

	public void updateAllLocalizables()
	{
		for(ILocalizable loc : localizables)
		{
			loc.loadLocalizedTexts();
		}
	}

	public String get(String label)
	{
		if (label.startsWith("@"))
		{
			String lbl = label.substring(1);
			ILocalizationSource source = labelSources.get(lbl);
			if(source != null){
				return source.get(lbl);
			}
		}
		return label;
	}
}
