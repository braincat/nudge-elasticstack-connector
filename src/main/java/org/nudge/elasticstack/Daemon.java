package org.nudge.elasticstack;

/**
 * @author Sarah Bourgeois
 * @author Frederic Massart
 *
 * Description : Class which permits to send rawdatas to elasticSearch with -startDeamon
 */

import mapping.Mapping;
import type.Mbean;
import com.nudge.apm.buffer.probe.RawDataProtocol.Dictionary;
import com.nudge.apm.buffer.probe.RawDataProtocol.MBean;
import com.nudge.apm.buffer.probe.RawDataProtocol.RawData;
import com.nudge.apm.buffer.probe.RawDataProtocol.Transaction;
import type.Sql;
import type.TransactionLayer;
import org.apache.log4j.Logger;
import org.nudge.elasticstack.config.Configuration;
import org.nudge.elasticstack.connection.Connection;
import org.nudge.elasticstack.json.bean.EventMBean;
import org.nudge.elasticstack.json.bean.EventSQL;
import org.nudge.elasticstack.json.bean.EventTransaction;
import org.nudge.elasticstack.json.bean.MappingProperties;
import org.nudge.elasticstack.json.bean.MappingPropertiesBuilder;
import org.nudge.elasticstack.json.bean.NudgeEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")

public class Daemon {

	private static final Logger LOG = Logger.getLogger(Daemon.class);
	private static ScheduledExecutorService scheduler;
	private static List<String> analyzedFilenames = new ArrayList<>();
	private static final long ONE_MIN = 60000;

	/**
	 * Description : Launcher Deamon.
	 *
	 * @param config
	 *
	 */
	public static void start(Configuration config) {
		scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				Thread thread = new Thread(runnable);
				thread.setName("nudge-es-daemon");
				thread.setDaemon(false);
				return thread;
			}
		});
		scheduleDaemon(scheduler, config);
	}

	private static void scheduleDaemon(ScheduledExecutorService scheduler, Configuration config) {
		scheduler.scheduleAtFixedRate(new DaemonTask(config), 0L, 1L, TimeUnit.MINUTES);
	}

	public static void stop() {
		scheduler.shutdown();
	}

	protected static class DaemonTask implements Runnable {
		private Configuration config;

		DaemonTask(Configuration config) {
			this.config = config;
		}

		/**
		 * Description : Collect data from Nudge API and push it.
		 */
		@Override
		public void run() {
			try {
				// Connection and load configuration
				Connection c = new Connection(config.getNudgeUrl());
				c.login(config.getNudgeLogin(), config.getNudgePwd());
				for (String appId : config.getAppIds()) {
					List<String> rawdataList = c.requestRawdataList(appId, "-10m");
					// analyse files, comparaison and push
					for (String rawdataFilename : rawdataList) {
						if (!analyzedFilenames.contains(rawdataFilename)) {
							RawData rawdata = c.requestRawdata(appId, rawdataFilename);

							// ==============================
							// Type : Transaction and Layer
							// ==============================
							TransactionLayer tl = new TransactionLayer();
							List<Transaction> transactions = rawdata.getTransactionsList();
							List<EventTransaction> events = tl.buildTransactionEvents(transactions);
							for (EventTransaction eventTrans : events) {
								tl.nullLayer(eventTrans);
							}
							List<String> jsonEvents = tl.parseJson(events);
							tl.sendToElastic(jsonEvents);

							// ===========================
							// Type : MBean
							// ===========================
							Mbean mb = new Mbean();
							List<MBean> mbean = rawdata.getMBeanList();
							Dictionary dictionary = rawdata.getMbeanDictionary();
							List<EventMBean> eventsMBeans = mb.buildMbeanEvents(mbean, dictionary);
							List<String> jsonEvents2 = mb.parseJsonMBean(eventsMBeans);
							mb.sendElk(jsonEvents2);

							// ===========================
							// Type : SQL
							// ===========================
							Sql sql = new Sql();
							List<EventSQL> sqlList = sql.buildSqlEvents(transactions);
							List<String> jsonEventsSql = sql.parseJsonSQL(sqlList);
							sql.sendSqltoElk(jsonEventsSql);

							// ===========================
							// Mapping
							// ===========================
							// Transaction mapping
							Mapping.pushMapping(config);

						}
					}
					analyzedFilenames = rawdataList;
				}
			} catch (Throwable t) {
				LOG.fatal("The daemon has encountered a crash error", t);
				if (null != scheduler) {
					// Reschedule the daemonTask
					LOG.info("Restart the daemon in " + ONE_MIN + "ms");
					try {
						Thread.sleep(ONE_MIN);
					} catch (InterruptedException e) {
						LOG.warn("Interrupted before a daemon restart", e);
					}
					LOG.info("Restarting daemon ...");
					scheduleDaemon(scheduler, config);
				}
			}
		}

	
	} // end of class

}
