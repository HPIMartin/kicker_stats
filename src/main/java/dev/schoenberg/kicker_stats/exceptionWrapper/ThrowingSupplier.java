package dev.schoenberg.kicker_stats.exceptionWrapper;

public interface ThrowingSupplier<T> {
	T get() throws Throwable;
}
