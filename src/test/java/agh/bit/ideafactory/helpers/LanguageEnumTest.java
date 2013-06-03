package agh.bit.ideafactory.helpers;

import org.junit.Test;

import agh.bit.ideafactory.helpers.LanguageEnum;
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
	public void shouldReturnTrueWhenMatchingExtensionExists() {
		
		String extension = "cpp";
		
		boolean extensionExists = LanguageEnum.checkIfExtensionExists(extension);
		
		assertEquals(true, extensionExists);
		
	}
	
	@Test
	public void shouldReturnFalseWhenMatchingExtensionDoesntExist() {
		
		String extension = "cpppp";
		
		boolean extensionExists = LanguageEnum.checkIfExtensionExists(extension);
		
		assertEquals(false, extensionExists);
		
	}
	
	
}
