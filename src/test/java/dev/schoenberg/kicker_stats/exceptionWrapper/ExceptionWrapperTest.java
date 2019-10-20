package dev.schoenberg.kicker_stats.exceptionWrapper;

import static dev.schoenberg.kicker_stats.exceptionWrapper.ExceptionWrapper.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class ExceptionWrapperTest {
	private boolean noopCalled;

	@Test
	public void callsVoidFunction() {
		noopCalled = false;

		silentThrow(this::noop);

		assertThat(noopCalled).isTrue();
	}

	@Test
	public void callsObjectSupplier() {
		Object expected = new Integer(42);

		Object actual = silentThrow(() -> returnObject(expected));

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void callsPrimitiveSupplier() {
		int expected = 42;

		Object actual = silentThrow(() -> returnPrimitive(expected));

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void wrapsSupplier() {
		Exception toWrap = new Exception();

		Exception actual = null;
		try {
			silentThrow(() -> throwSomethingWhileReturning(toWrap));
		} catch (Exception catched) {
			actual = catched;
		}

		assertThat(actual.getCause()).isEqualTo(toWrap);
	}

	@Test
	public void wrapsVoidException() {
		Exception toWrap = new Exception();

		Exception actual = null;
		try {
			silentThrow(() -> throwSomething(toWrap));
		} catch (Exception catched) {
			actual = catched;
		}

		assertThat(actual.getCause()).isEqualTo(toWrap);
	}

	@Test
	public void doesNotWrapRuntimeException() {
		Exception notToWrap = new RuntimeException();

		Exception actual = null;
		try {
			silentThrow(() -> throwSomething(notToWrap));
		} catch (Exception catched) {
			actual = catched;
		}

		assertThat(actual).isEqualTo(notToWrap);
	}

	private void noop() throws Exception {
		noopCalled = true;
	}

	private Object returnObject(Object toReturn) throws Exception {
		return toReturn;
	}

	private int returnPrimitive(int toReturn) throws Exception {
		return toReturn;
	}

	private int throwSomethingWhileReturning(Exception e) throws Exception {
		throw e;
	}

	private void throwSomething(Exception e) throws Exception {
		throw e;
	}
}
