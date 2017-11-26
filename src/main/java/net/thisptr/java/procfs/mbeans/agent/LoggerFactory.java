package net.thisptr.java.procfs.mbeans.agent;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;

/**
 * A custom LoggerFactory that is tightly bound to logback implementation in order to achieve a relocation-safety of logging mechanics (by maven-shade-plugin).
 */
public class LoggerFactory {
	private static final LoggerContext CONTEXT = new LoggerContext();
	private static final String PATTERN = "%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %level [%thread] %logger{10} [%file:%line] %msg%n";
	private static final String ROOT_LOGGER_NAME = "ROOT";
	private static final String STDOUT = "-";

	public static void configure(final Level level, final String path) {
		final LoggerContext context = CONTEXT;
		context.reset();

		final PatternLayout layout = new PatternLayout();
		layout.setContext(context);
		layout.setPattern(PATTERN);
		layout.start();

		final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		encoder.setCharset(StandardCharsets.UTF_8);
		encoder.start();

		if (path == null || path.isEmpty() || path.equals(STDOUT)) {
			final ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
			appender.setEncoder(encoder);
			appender.setContext(context);
			appender.setLayout(layout);
			appender.setTarget(STDOUT.equals(path) ? "System.out" : "System.err");
			appender.setName("CONSOLE");
			appender.start();
			context.getLogger(ROOT_LOGGER_NAME).addAppender(appender);
		} else {
			final FileAppender<ILoggingEvent> appender = new FileAppender<ILoggingEvent>();
			appender.setEncoder(encoder);
			appender.setContext(context);
			appender.setLayout(layout);
			appender.setFile(path);
			appender.setName("FILE");
			appender.start();
			context.getLogger(ROOT_LOGGER_NAME).addAppender(appender);
		}

		context.getLogger(ROOT_LOGGER_NAME).setLevel(level);
		context.start();
	}

	public static Logger getLogger(final Class<?> clazz) {
		return CONTEXT.getLogger(clazz.getName());
	}
}
