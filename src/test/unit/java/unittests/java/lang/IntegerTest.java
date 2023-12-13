package unittests.java.lang;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class IntegerTest {
	@Test
	public void parsesInMiddleOfString() {
		String input = "t.fafer123:kkg";

		int actual = Integer.parseInt(input, 7, 10, 10);

		assertThat(actual, is(123));
	}

	@ParameterizedTest
	@ValueSource(strings = { "t.2147483648:kkg", "t.4294967296:kkg" })
	public void whenTooBigThrows(String input) {
		//noinspection ResultOfMethodCallIgnored
		assertThrows(NumberFormatException.class, () -> Integer.parseInt(input, 2, 12, 10));
	}
}
