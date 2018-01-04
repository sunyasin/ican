package back.model;

/**
 */
public abstract class LoggerContext {

    private final static String START = "---> ";
    private final static String END = "<--- ";
    private final static String EMPTY = "";

    protected String user;

    public LoggerContext(String user) {
        this.user = user;
    }

    public abstract boolean shouldLog();

    public abstract String getUserName();

    public abstract LoggerContext logout(String prefix, String msg);

    public LoggerContext start(String msg) {
        logout(START, msg);
        return this;
    }

    public void end(String msg) {
        logout(END, msg);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
