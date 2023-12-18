package unittests.java.time;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.time.*;
import java.time.format.*;
import java.util.stream.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class LocalDateTimeTests {
	@ParameterizedTest
	@MethodSource("parsesStringTestCaseSource")
	public void parsesString(
			String inputString,
			DateTimeFormatter dateTimeFormat,
			LocalDateTime expectedDateTime) {
		var actualDateTime = LocalDateTime.parse(inputString, dateTimeFormat);

		assertThat(actualDateTime, is(expectedDateTime));
	}

	public static Stream<Arguments> parsesStringTestCaseSource() {
		return Stream.of(
			Arguments.of(
				"2020-06-03 01:02:04",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
				LocalDateTime.of(2020, 6, 3, 1, 2, 4, 0)),
			Arguments.of(
				"2020-06-03 15:02:04",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
				LocalDateTime.of(2020, 6, 3, 15, 2, 4, 0)),
			Arguments.of(
				"2020-06-03 16:02:04.005",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
				LocalDateTime.of(2020, 6, 3, 16, 2, 4, 5_000_000)),
			Arguments.of(
				"2020-06-03 16:02:04.500",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
				LocalDateTime.of(2020, 6, 3, 16, 2, 4, 500_000_000)),
			Arguments.of(
				"2020-06-03 16:02:04.7623",
				new DateTimeFormatterBuilder()
					.append(DateTimeFormatter.ISO_LOCAL_DATE)
					.appendLiteral(' ')
					.append(DateTimeFormatter.ISO_LOCAL_TIME)
					.toFormatter(),
				LocalDateTime.of(2020, 6, 3, 16, 2, 4, 762_300_000)));
	}

	@Test
	public void throwsOnParsingStringWhenHourAmPmUsedWithoutAmPmSign() {
		var inputString = "2020-06-03 01:02:04";
		var dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

		assertThrows(DateTimeParseException.class, () -> LocalDateTime.parse(inputString, dateTimeFormat));
	}
}
