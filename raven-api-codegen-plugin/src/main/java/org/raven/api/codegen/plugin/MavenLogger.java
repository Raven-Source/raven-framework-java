package org.raven.api.codegen.plugin;

import org.apache.maven.plugin.logging.Log;
import org.slf4j.Logger;
import org.slf4j.Marker;

public class MavenLogger implements Logger {

    private final Log log;

    public MavenLogger(Log log) {
        this.log = log;
    }

    @Override
    public String getName() {
        return log.getClass().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
    }

    @Override
    public void trace(String format, Object arg) {

    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(String format, Object... arguments) {

    }

    @Override
    public void trace(String msg, Throwable t) {

    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        log.debug(String.format(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

        log.debug(String.format(format, arg1, arg1));
    }

    @Override
    public void debug(String format, Object... arguments) {
        log.debug(String.format(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        log.debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        log.debug(String.format(format, arg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        log.debug(String.format(format, arg1, arg1));
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        log.debug(String.format(format, arguments));
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        log.debug(msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        log.info(String.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log.info(String.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        log.info(String.format(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        log.info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return log.isInfoEnabled();
    }

    @Override
    public void info(Marker marker, String msg) {
        log.info(msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        log.info(String.format(format, arg));
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        log.info(String.format(format, arg1, arg2));
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        log.info(String.format(format, arguments));
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        log.info(msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        log.warn(String.format(format, arg));
    }

    @Override
    public void warn(String format, Object... arguments) {
        log.warn(String.format(format, arguments));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log.warn(String.format(format, arg1, arg2));
    }

    @Override
    public void warn(String msg, Throwable t) {
        log.warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        log.warn(String.format(format, arg));
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        log.warn(String.format(format, arg1, arg2));
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        log.warn(String.format(format, arguments));
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        log.warn(msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        log.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        log.error(String.format(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        log.error(String.format(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        log.error(String.format(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        log.error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return log.isErrorEnabled();
    }

    @Override
    public void error(Marker marker, String msg) {
        log.error(msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        log.error(String.format(format, arg));
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        log.error(String.format(format, arg1, arg2));

    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        log.error(String.format(format, arguments));
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        log.error(msg, t);
    }
}
