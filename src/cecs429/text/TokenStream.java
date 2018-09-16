package cecs429.text;

/**
 * Creates a sequence of String tokens from the contents of another stream, breaking the bytes of the stream into tokens
 * in some way.
 */
public interface TokenStream {
	Iterable<String> getTokens();
}
