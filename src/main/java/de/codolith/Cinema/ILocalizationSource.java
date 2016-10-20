package de.codolith.Cinema;

import java.util.ArrayList;

public interface ILocalizationSource
{
	ArrayList<String> getAvailableLabels();
	void languageCodeChanged(String code);
	String get(String label);
}
