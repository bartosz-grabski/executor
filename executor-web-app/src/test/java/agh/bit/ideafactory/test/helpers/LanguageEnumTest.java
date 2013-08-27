package agh.bit.ideafactory.test.helpers;

import agh.bit.ideafactory.helpers.LanguageEnum;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageEnumTest {

	@Test
	public void shouldReturnValidLanguageEnumByName() {

		String name = "C";
		LanguageEnum lang = LanguageEnum.getLanguageByName(name);

		assertEquals(name, lang.getName());
		assertEquals("c", lang.getExtension());

	}

	@Test
	public void shouldReturnNullWhenInvalidLanguageName() {

		String name = "not existing language";

		LanguageEnum lang = LanguageEnum.getLanguageByName(name);

		assertNull(lang);
	}

	@Test
	public void shouldReturnNullWhenNullLanguageName() {

		LanguageEnum lang = LanguageEnum.getLanguageByName(null);

		assertNull(lang);
	}

	@Test
	public void shouldReturnTrueWhenMatchingExtensionExists() {

		String extension = "cpp";

		boolean extensionExists = LanguageEnum.extensionExists(extension);

		assertTrue(extensionExists);
	}

	@Test
	public void shouldReturnFalseWhenMatchingExtensionDoesntExist() {

		String extension = "cpppp";

		boolean extensionExists = LanguageEnum.extensionExists(extension);

		assertFalse(extensionExists);

	}

	@Test
	public void shouldReturnFalseWhenMatchingExtensionIsNull() {

		boolean extensionExists = LanguageEnum.extensionExists(null);

		assertFalse(extensionExists);

	}

}
