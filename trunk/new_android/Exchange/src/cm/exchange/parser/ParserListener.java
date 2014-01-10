package cm.exchange.parser;

/**
 * you can implements this interface when parser xml file
 * and it can asynchronously return object when parser. 
 * <br><strong>it's important that you would't wait until the whole xml file parser over by listener.</strong>
 * @author qh
 *
 * @param <T> entity class
 */
public interface ParserListener<T> {
	public void onParserOverListener(T t);

}
