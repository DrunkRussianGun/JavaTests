package integrationtests.java.io;

import integrationtests.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TextFileReadTest extends IntegrationTest {
	private static final String utf8FileName = "utf-8.txt";
	private static final String oem866FileName = "oem-866.txt";
	private static final String utf32FileName = "utf-32.txt";
	private static final String windows1251FileName = "windows-1251.txt";

	@Test
	public void readerSucceedsOnUtf8() throws IOException {
		var reader = new BufferedReader(
			new InputStreamReader(getClass().getClassLoader().getResourceAsStream(utf8FileName)));

		String firstLine = reader.readLine();
		String secondLine = reader.readLine();

		assertThat(firstLine, equalTo("Hello"));
		assertThat(secondLine, equalTo("Привет"));
	}

	@ParameterizedTest
	@ValueSource(strings = { oem866FileName, utf32FileName, windows1251FileName })
	public void whenNotUtf8ReaderFails(String fileName) throws IOException {
		var reader = new BufferedReader(
			new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));

		String firstLine = reader.readLine();
		String secondLine = reader.readLine();

		assertThat(firstLine + secondLine, not(equalTo("HelloПривет")));
	}
}
