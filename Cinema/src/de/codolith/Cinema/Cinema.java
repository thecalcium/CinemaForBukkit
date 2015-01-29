package de.codolith.Cinema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.codolith.Cinema.Executors.*;

public class Cinema extends JavaPlugin
{
	public static Version		version			= null;									// from plugin.yml in constructor
	//public static Cinema		currentInstance	= null;
	private File				dataFolder		= new File("plugins/cinema/files");
	private File				restorationFile	= new File("plugins/cinema/hibernate.dat");
	private File				messagesFile	= new File("plugins/cinema/messages.yml");
	private List<CinemaPlayer>	players			= new LinkedList<CinemaPlayer>();
	private Logger				logger			= null;
	private HashMap<String,Region> regions = new HashMap<String,Region>();
	//private Region				region			= new Region();
	private CinemaEditor		cinemaEditor	= null;
	private VersionChecker		versionChecker	= new VersionChecker();
	private Localizator         localizator		= new Localizator();
	private Formatter			formatter		= new Formatter();

	public Cinema()
	{
		version = new Version(this.getDescription().getVersion());
	}

	public Version getNewestVersion()
	{
		versionChecker.check();
		// version name is like "Cinema v*.*.*.*"
		if (versionChecker.getVersionName() != null)
		{
			String[] sa = versionChecker.getVersionName().split(" ", 3);
			if (sa.length >= 2)
			{
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
		}
		return new Version(0, 0, 0, 0);
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
	
	public Localizator getLocalizator(){
		return localizator;
	}

	@Override
	public void onEnable()
	{
		logger = this.getLogger();

		// create folders in plugins...
		dataFolder.mkdirs();

		saveDefaultConfig(); // does not overwrite
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
		
		//localization
		if(!messagesFile.exists()){
			createMessagesFile();
		}
		YMLConfigLocalizationSource locsource = new YMLConfigLocalizationSource(messagesFile);
		localizator.addLocalizationSource(locsource);
		localizator.setLanguageCode("messages");
		

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
		getCommand("cstep").setExecutor(new Cstep_Executor(this));
		
		// restore players from last time
		restore();
	}
	
	private void createMessagesFile(){
		YMLConfigLocalizationSource source = new YMLConfigLocalizationSource();
		
		String l = "messages";
		source.setDefaultLanguageCode(l);
		source.AddLabel(Messages.s, l, "%s");
		source.AddLabel(Messages.no_anims_active, l, "No animations active");
		source.AddLabel(Messages.X_anim_active, l, "%1$s animation active:");
		source.AddLabel(Messages.X_anims_active, l, "%1$s animations active:");
		source.AddLabel(Messages.pos_X_set_to_Y, l, "Position %1$s set to %2$s");
		source.AddLabel(Messages.X_blocks_selected, l, "%1$s blocks selected");
		source.AddLabel(Messages.invalid_value_for_param_X, l, "Invalid value for parameter \"%1$s\"");
		source.AddLabel(Messages.invalid_value_for_param_X_no_quot, l, "Invalid value for parameter %1$s");
		source.AddLabel(Messages.available_alignments, l, "Available Alignments (you can use the number too):");
		source.AddLabel(Messages.X_colon_Y, l, "%1$s: %2$s");
		source.AddLabel(Messages.apikey_colon_X, l, "ApiKey: %1$s");
		source.AddLabel(Messages.cinema_version_colon_X, l, "Cinema Version: %1$s");
		source.AddLabel(Messages.new_version_you_have_X_newest_is_Y, l, "NEW VERSION AVAILABLE FOR CINEMA PLUGIN! You have \"%1$s\" and the newest version is \"%2$s\"");
		source.AddLabel(Messages.get_cinema_here_colon_X, l, "Get Cinema here: %1$s");
		source.AddLabel(Messages.deleted_animation_X, l, "Deleted animation \"%1$s\"");
		source.AddLabel(Messages.no_animation_with_name_X, l, "No animation with name \"%1$s\"");
		source.AddLabel(Messages.positions_not_set, l, "Positions not set. Can't continue");
		source.AddLabel(Messages.positions_have_to_be_same_world, l, "Positions have to be in the same world");
		source.AddLabel(Messages.player_X_paused, l, "Player \"%1$s\" paused");
		source.AddLabel(Messages.player_X_doesnt_exist, l, "Player \"%1$s\" doesn't exist");
		source.AddLabel(Messages.animation_X_doesnt_exist, l, "Animation \"%1$s\" doesn't exist");
		source.AddLabel(Messages.error_opening_animation_X, l, "Error opening animation \"%1$s\"");
		source.AddLabel(Messages.player_id_already_in_use, l, "Player id already in use");
		source.AddLabel(Messages.player_X_resumed, l, "Player \"%1$s\" resumed");
		source.AddLabel(Messages.player_X_reversed, l, "Player \"%1$s\" reversed");
		source.AddLabel(Messages.apikey_set_to_X, l, "ApiKey set to \"%1$s\"");
		source.AddLabel(Messages.apikey_removed, l, "ApiKey removed");
		source.AddLabel(Messages.stepped_X_frames, l, "Stepped %1$s frames");
		source.AddLabel(Messages.player_has_to_be_paused, l, "The cinema player has to be paused for this");
		source.AddLabel(Messages.player_X_stopped, l, "Player \"%1$s\" stopped");
		source.AddLabel(Messages.file_not_found_colon_X, l, "file not found \"%1$s\"");
		source.AddLabel(Messages.conversion_successful, l, "Conversion successful");
		source.AddLabel(Messages.conversion_failed, l, "Conversion failed, can't save file");
		source.AddLabel(Messages.pos_X_not_set, l, "Position %1$s not set");
		source.AddLabel(Messages.no_file_in_editor, l, "No file opened in editor");
		source.AddLabel(Messages.error_saving_animation, l, "Error saving animation");
		source.AddLabel(Messages.animation_X_loaded, l, "Animation \"%1$s\" loaded");
		source.AddLabel(Messages.error_see_console, l,"An error occoured. See server console for more infos");
		source.AddLabel(Messages.animation_still_loaded, l,"There is still an animation loaded in the editor. You have to close it first with /ceditclose");
		
		try{
			source.save(messagesFile);
		}catch(IOException ex){
			logger.warning("Error creating messages.yml file!");
			ex.printStackTrace();
		}
	}
	
	public String getMessage(String label)
	{
		return localizator.get("@"+label);
	}
	
	public Formatter getFormatter(){
		return formatter;
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
			new RestorationFile(this, restorationFile); // auto restores everything
		}
		catch (FileNotFoundException fnfe)
		{
			// well no players then
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
			RestorationFile rf = new RestorationFile(this);
			rf.save(restorationFile);
		}
		catch (IOException e)
		{
			logger.warning("Error saving cinema players!");
			e.printStackTrace();
		}
	}

	public Region getRegion(CommandSender sender)
	{
		Region retval = null;
		String sendername = sender.getName();
		if(regions.containsKey(sender.getName())){
			retval = regions.get(sendername);
		}else{
			if(sender instanceof Player){
				Player player = (Player) sender;
				retval = new Region(player.getWorld());
			}else{
				retval = new Region(null);
			}
			regions.put(sendername, retval);
		}
		return retval;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		sender.sendMessage("You shouldn't see this message O_o. This would mean a command wasn't registered properly");
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

	public void addCinemaPlayer(CinemaPlayer player)
	{
		players.add(player);
	}

	public void removeCinemaPlayer(CinemaPlayer player)
	{
		players.remove(player);
	}

	public CinemaPlayer getCinemaPlayer(String id)
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

	public boolean containsCinemaPlayer(String id)
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

	public int getCinemaPlayerCount()
	{
		return players.size();
	}

	public List<CinemaPlayer> getCinemaPlayers()
	{
		return players;
	}
}
