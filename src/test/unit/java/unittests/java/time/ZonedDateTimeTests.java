package unittests.java.time;

import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.time.*;
import java.time.format.*;
import java.util.stream.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class ZonedDateTimeTests {
	@ParameterizedTest
	@MethodSource("convertsToStringTestCaseSource")
	public void convertsToString(
			ZonedDateTime inputDateTime,
			DateTimeFormatter dateTimeFormat,
			String expectedString) {
		var actualString = inputDateTime.format(dateTimeFormat);

		assertThat(actualString, is(expectedString));
	}

	public static Stream<Arguments> convertsToStringTestCaseSource() {
		return Stream.of(
			Arguments.of(
				ZonedDateTime.of(2020, 6, 3, 13, 2, 4, 0, ZoneId.of("+00")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"),
				"2020-06-03 01:02:04"),
			Arguments.of(
				ZonedDateTime.of(2020, 6, 3, 15, 2, 4, 0, ZoneId.of("+00")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
				"2020-06-03 15:02:04"),
			Arguments.of(
				ZonedDateTime.of(2020, 6, 3, 16, 2, 4, 5_000_000, ZoneId.of("+00")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
				"2020-06-03 16:02:04.005"),
			Arguments.of(
				ZonedDateTime.of(2020, 6, 3, 16, 2, 4, 500_000_000, ZoneId.of("+00")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
				"2020-06-03 16:02:04.500"),
			Arguments.of(
				ZonedDateTime.of(2020, 6, 3, 16, 2, 4, 762_300_000, ZoneId.of("+00")),
				new DateTimeFormatterBuilder()
					.append(DateTimeFormatter.ISO_LOCAL_DATE)
					.appendLiteral(' ')
					.append(DateTimeFormatter.ISO_LOCAL_TIME)
					.toFormatter(),
				"2020-06-03 16:02:04.7623"));
	}
}
