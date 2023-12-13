package unittests.org.apache.commons.configuration2;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.ex.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class INIConfigurationTests {
	@Test
	public void notThrowsWhenRequestedNonExistingSection() throws ConfigurationException, IOException {
		var ini = readIni("[section]", "key = value");

		var nonExistingSection = ini.getSection("no-section");

		assertTrue(nonExistingSection.isEmpty());
	}

	@Test
	public void returnsNullForNonExistingKey() throws ConfigurationException, IOException {
		var ini = readIni("[section]", "key = value");

		var section = ini.getSection("section");
		var nonExistingValue = section.getProperty("non-existing");

		assertNull(nonExistingValue);
	}

	@Test
	public void returnsStringForArbitraryPropertyValue() throws ConfigurationException, IOException {
		var ini = readIni("[section]", "string key = some string");

		var section = ini.getSection("section");
		var stringValue = section.getProperty("string key");

		assertThat(stringValue, is("some string"));
	}

	@Test
	public void returnsStringForIntegerPropertyValue() throws ConfigurationException, IOException {
		var ini = readIni("[section]", "number key = 123");

		var section = ini.getSection("section");
		var integerValue = section.getProperty("number key");

		assertThat(integerValue, is("123"));
	}

	private static INIConfiguration readIni(String... iniLines) throws ConfigurationException, IOException {
		var iniString = String.join(System.lineSeparator(), iniLines);
		var ini = new INIConfiguration();
		ini.read(new StringReader(iniString));
		return ini;
	}
}
