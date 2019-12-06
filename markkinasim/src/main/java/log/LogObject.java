package log;

/**
 * interface for saving log events to Accountants log list
 */
public interface LogObject {
	public int getDay();
	public void register();
}
