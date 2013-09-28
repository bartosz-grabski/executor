package agh.bit.ideafactory.helpers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum LanguageEnum {
	C("C", "c"),
	JAVA("JAVA", "java"),
	PYTHON("PYTHON", "py"),
	CPLUSPLUS("C++", "cpp");

	private String name;
	private String extension;

	private LanguageEnum(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public static LanguageEnum getLanguageByName(String name) {
		for (LanguageEnum lang : LanguageEnum.values()) {
			if (lang.getName().equalsIgnoreCase(name)) {
				return lang;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Language is : " + this.name + " with extension : " + this.extension;
	}

	public static List<LanguageEnum> getAllLanguagesAsList() {
		return new ArrayList<>(EnumSet.allOf(LanguageEnum.class));
	}

	public static boolean extensionExists(String extension) {

		for (LanguageEnum language : LanguageEnum.values()) {
			if (language.getExtension().equals(extension))
				return true;
		}
		return false;
	}

}
