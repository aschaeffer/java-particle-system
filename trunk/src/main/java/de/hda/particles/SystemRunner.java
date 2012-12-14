package de.hda.particles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SystemRunner is a mechanism to run systems
 * implementing the Updateable interface in its own
 * threads.
 * 
 * @author aschaeffer
 *
 */
public class SystemRunner {

	/**
	 * The list of managed systems.
	 */
	private final Map<String, Updateable> updateables = new HashMap<String, Updateable>();
	
	/**
	 * The list of system updaters of the managed systems.
	 */
	private final Map<String, SystemUpdater> systemUpdaters = new HashMap<String, SystemUpdater>();
	
	/**
	 * The list of system updater threads, that are running the managed systems.
	 */
	private final Map<String, Thread> systemUpdaterThreads = new HashMap<String, Thread>();

	/**
	 * True, if all systems are still running.
	 */
	private Boolean running = true;
	
	/**
	 * The logger.
	 */
	private final Logger logger = LoggerFactory.getLogger(SystemRunner.class);

	/**
	 * Adds a updateable to the list of system updaters and creates a thread.
	 * @param name
	 * @param updateable
	 */
	public void add(String name, Updateable updateable) {
		logger.info("Create new system updater thread for " + name);
		updateables.put(name, updateable);
		ThreadedSystemUpdater systemUpdater = new ThreadedSystemUpdater(name, updateable);
		systemUpdaters.put(name, systemUpdater);
		Thread systemUpdaterThread = new Thread(systemUpdater);
		systemUpdaterThreads.put(name, systemUpdaterThread);
	}

	/**
	 * Starts all system updater threads and loops until
	 * one system updater has been stopped.
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		Set<String> systemUpdaterThreadNames = systemUpdaterThreads.keySet();
		Iterator<String> systemUpdaterThreadNamesIterator = systemUpdaterThreadNames.iterator();
		while (systemUpdaterThreadNamesIterator.hasNext()) {
			Thread systemUpdaterThread = systemUpdaterThreads.get(systemUpdaterThreadNamesIterator.next());
			systemUpdaterThread.start();
		}
		// loop until one system updater has been stopped
		running = true;
		while(running) { // !renderUpdater.isFinished() && !physicsUpdater.isFinished()) {
			Set<String> systemUpdaterNames = systemUpdaters.keySet();
			Iterator<String> systemUpdaterNamesIterator = systemUpdaterNames.iterator();
			while (systemUpdaterNamesIterator.hasNext()) {
				SystemUpdater systemUpdater = systemUpdaters.get(systemUpdaterNamesIterator.next());
				if (systemUpdater.isFinished()) {
					running = false;
					break;
				}
			}
			Thread.sleep(500);
		}
		// stop all system updaters
		Set<String> systemUpdaterNames = systemUpdaters.keySet();
		Iterator<String> systemUpdaterNamesIterator = systemUpdaterNames.iterator();
		while (systemUpdaterNamesIterator.hasNext()) {
			systemUpdaters.get(systemUpdaterNamesIterator.next()).stop();
		}
		// wait for a half second
		Thread.sleep(500);
	}
	
}
