package unittests.java.lang;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class ClassTests {
	private static class TestClass {
	}

	@Test
	public void returnsClassSimpleName() {
		var simpleName = TestClass.class.getSimpleName();

		assertThat(simpleName, is("TestClass"));
	}
}
