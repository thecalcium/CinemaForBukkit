package de.codolith.Cinema.Executors;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.Messages;

public class Ceditsave_Executor implements CommandExecutor
{

	private Cinema	cinema;

	public Ceditsave_Executor(Cinema cinema)
	{
		this.cinema = cinema;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (args.length == 0 || args.length == 1)
		{
			if (cinema.getCinemaEditor() != null)
			{
				try
				{
					if (args.length == 0)
					{
						cinema.getCinemaEditor().save();
					}
					else if (args.length == 1)
					{
						File file = new File(cinema.getExtDataFolder(), args[0]);
						cinema.getCinemaEditor().save(file);
					}
				}
				catch (IOException ioe)
				{
					sender.sendMessage(cinema.getMessage(Messages.error_saving_animation));
					//sender.sendMessage("error saving animation");
					ioe.printStackTrace();
				}
			}
			else
			{
				sender.sendMessage(cinema.getMessage(Messages.no_file_in_editor));
			}
			return true;
		}
		return false;
	}
}
