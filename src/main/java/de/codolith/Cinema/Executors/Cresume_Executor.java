package de.codolith.Cinema.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.codolith.Cinema.Cinema;
import de.codolith.Cinema.CinemaPlayer;
import de.codolith.Cinema.Messages;

public class Cresume_Executor implements CommandExecutor
{

	private Cinema	cinema;

	public Cresume_Executor(Cinema cinema)
	{
		this.cinema = cinema;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (args.length >= 1)
		{
			for (int i = 0; i < args.length; i++)
			{
				if (cinema.containsCinemaPlayer(args[i]))
				{
					CinemaPlayer cp = cinema.getCinemaPlayer(args[i]);
					cp.play();
					sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_resumed),args[i]));
					//sender.sendMessage("Player \"" + args[i] + "\" resumed");
				}
				else
				{
					sender.sendMessage(String.format(cinema.getMessage(Messages.player_X_doesnt_exist),args[i]));
					//sender.sendMessage("Player \"" + args[i] + "\" doesn't exist");
				}
			}
			return true;
		}
		return false;
	}

}
