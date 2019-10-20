package dev.schoenberg.kicker_stats.core.helper.exceptionWrapper;

public interface ThrowingSupplier<T> {
	T get() throws Throwable;
}
