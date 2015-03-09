package org.dembol.blue;

import lombok.extern.slf4j.Slf4j;
import org.dembol.blue.shared.InternalException;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class Launcher {

	private static final String DATABASE_FILE = "file:/tmp/blue2";
	private static final String DATABASE_NAME = "blue2";

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.startDBServer();
		launcher.startContext();
	}

	private void startContext() {
		new ClassPathXmlApplicationContext(
				"META-INF/spring/bundle-interfaces-context.xml",
				"META-INF/spring/bundle-application-context.xml",
				"META-INF/spring/bundle-infrastructure-context.xml",
				"META-INF/spring/bundle-validation-context.xml");
	}

	public void startDBServer() {
		HsqlProperties props = new HsqlProperties();
		props.setProperty("server.database.0", DATABASE_FILE);
		props.setProperty("server.dbname.0", DATABASE_NAME);
		Server server = new org.hsqldb.Server();

		try {
			server.setProperties(props);
		} catch (Exception e) {
			throw new InternalException("cannot start database server");
		}

		server.start();
	}
}
