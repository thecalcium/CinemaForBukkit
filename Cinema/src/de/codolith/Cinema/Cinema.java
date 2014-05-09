package de.codolith.Cinema;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.codolith.Cinema.Executors.*;

public class Cinema extends JavaPlugin
{
	public static Version		version			= null;									// from plugin.yml in constructor
	public static Cinema		currentInstance	= null;
	private File				dataFolder		= new File("plugins/cinema/files");
	private File				restorationFile	= new File("plugins/cinema/hibernate.dat");
	private List<CinemaPlayer>	players			= new LinkedList<CinemaPlayer>();
	private Logger				logger			= null;
	private Region				region			= new Region();
	private CinemaEditor		cinemaEditor	= null;
	private VersionChecker		versionChecker	= new VersionChecker();

	public Cinema()
	{
		if (version == null)
		{
			version = new Version(this.getDescription().getVersion());
		}
		currentInstance = this;
	}

	public Version getNewestVersion()
	{
		versionChecker.check();
		// version name is like "Cinema v*.*.*.*"
		String[] sa = versionChecker.getVersionName().split(" ", 2);
		sa[1] = sa[1].substring(1); // getting rid of the v
		try
		{
			return new Version(sa[1]);
		}
		catch (Exception e)
		{ // if we cant handle our shit we will just use
			// the lowest possible version
			return new Version(0, 0, 0, 0);
		}
	}

	public File getExtDataFolder()
	{
		return dataFolder;
	}

	public VersionChecker getVersionChecker()
	{
		return versionChecker;
	}

	public void setDataFolder(File dataFolder)
	{
		this.dataFolder = dataFolder;
	}

	public Logger getExtLogger()
	{
		return logger;
	}

	@Override
	public void onEnable()
	{
		logger = this.getLogger();

		// create folders in plugins...
		dataFolder.mkdirs();

		reloadConfig();
		// version check
		if (this.getConfig().contains("Plugin.ApiKey"))
		{
			versionChecker.setApiKey(this.getConfig().getString("Plugin.ApiKey"));
		}
		else
		{
			versionChecker.setApiKey(null);
		}
		boolean checkForNewVersion = this.getConfig().getBoolean("Plugin.CheckForNewVersion");
		if (checkForNewVersion)
		{
			Version newest = getNewestVersion();
			if (newest.isNewerThan(version))
			{
				logger.info("NEW VERSION AVAILABLE FOR CINEMA PLUGIN! You have \"" + version + "\" and the newest version is \"" + newest + "\"");
				logger.info("Get Cinema here: http://dev.bukkit.org/server-mods/cinema/files/");
			}
		}

		// restore players from last time
		restore();

		getCommand("cpos1").setExecutor(new Cpos1_Executor(this));
		getCommand("cpos2").setExecutor(new Cpos2_Executor(this));
		getCommand("canimations").setExecutor(new Canimations_Executor(this));
		getCommand("ceditclose").setExecutor(new Ceditclose_Executor(this));
		getCommand("ceditinfo").setExecutor(new Ceditinfo_Executor(this));
		getCommand("ceditopen").setExecutor(new Ceditopen_Executor(this));
		getCommand("ceditremove").setExecutor(new Ceditremove_Executor(this));
		getCommand("ceditreverse").setExecutor(new Ceditreverse_Executor(this));
		getCommand("ceditsave").setExecutor(new Ceditsave_Executor(this));
		getCommand("ceditshow").setExecutor(new Ceditshow_Executor(this));
		getCommand("cinemagif").setExecutor(new Cinemagif_Executor(this));
		getCommand("cinemagifalignments").setExecutor(new Cinemagifalignments_Executor(this));
		getCommand("cinemainfo").setExecutor(new Cinemainfo_Executor(this));
		getCommand("cinemaremove").setExecutor(new Cinemaremove_Executor(this));
		getCommand("cplay").setExecutor(new Cplay_Executor(this));
		getCommand("cplayworld").setExecutor(new Cplayworld_Executor(this));
		getCommand("creverse").setExecutor(new Creverse_Executor(this));
		getCommand("csave").setExecutor(new Csave_Executor(this, false));
		getCommand("csavedelta").setExecutor(new Csave_Executor(this, true));
		getCommand("cinsert").setExecutor(new Cinsert_Executor(this, false));
		getCommand("cinsertdelta").setExecutor(new Cinsert_Executor(this, true));
		getCommand("csetapikey").setExecutor(new Csetapikey_Executor(this));
		getCommand("cstop").setExecutor(new Cstop_Executor(this));
		getCommand("cpause").setExecutor(new Cpause_Executor(this));
		getCommand("cresume").setExecutor(new Cresume_Executor(this));
	}

	@Override
	public void onDisable()
	{
		// store players
		store();
		for (CinemaPlayer cp : players)
		{
			cp.stop();
		}
		players.clear();
		saveConfig();
	}

	private void restore()
	{
		try
		{
			RestorationFile rf = new RestorationFile(this, restorationFile);
			rf.addTo(players);
		}
		catch (IOException e)
		{
			logger.warning("Error loading stored cinema players!");
			e.printStackTrace();
		}
	}

	private void store()
	{
		try
		{
			RestorationFile rf = new RestorationFile(players);
			rf.save(restorationFile);
		}
		catch (IOException e)
		{
			logger.warning("Error saving cinema players!");
			e.printStackTrace();
		}
	}

	public Region getRegion()
	{
		return region;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		sender.sendMessage("this command is broken O_o. please report that you got this message");
		return true;
	}

	public CinemaEditor getCinemaEditor()
	{
		return cinemaEditor;
	}

	public void setCinemaEditor(CinemaEditor cinemaEditor)
	{
		this.cinemaEditor = cinemaEditor;
	}

	public void addPlayer(CinemaPlayer player)
	{
		players.add(player);
	}

	public void removePlayer(CinemaPlayer player)
	{
		players.remove(player);
	}

	public CinemaPlayer getPlayer(String id)
	{
		for (CinemaPlayer cp : players)
		{
			if (cp.getId().equals(id))
			{
				return cp;
			}
		}
		return null;
	}

	public boolean containsPlayer(String id)
	{
		for (CinemaPlayer cp : players)
		{
			if (cp.getId().equals(id))
			{
				return true;
			}
		}
		return false;
	}

	public int getPlayerCount()
	{
		return players.size();
	}

	public List<CinemaPlayer> getPlayers()
	{
		return players;
	}
}
