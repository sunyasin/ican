package back.model;

import org.apache.log4j.Logger;

/**
 */
public class LoggerImpl extends LoggerContext {

    private boolean doLog = false;

    private Logger logger;
    private Level level;

    public enum Level {
        DEBUG, INFO, WARNING, ERROR
    }

    public LoggerImpl(String user, Logger logger, Level level) {
        super(user);
        this.logger = logger;
        this.level = level;
    }

    public LoggerContext logout(String prefix, String msg) {
        String out = prefix + " " + getUserName() + " " + msg;
        switch (level) {
            case DEBUG:
                logger.debug(out);
                break;
            case INFO:
                logger.info(out);
                break;
            case WARNING:
                logger.warn(out);
                break;
            case ERROR:
                logger.error(out);
                break;
        }
        return this;
    }

    @Override
    public boolean shouldLog() {
        return doLog;
    }

    @Override
    public String getUserName() {
        return user;
    }

    public void setDoLog(boolean doLog) {
        this.doLog = doLog;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
