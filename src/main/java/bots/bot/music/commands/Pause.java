package bots.bot.music.commands;

import bots.bot.music.ICommand;
import bots.bot.music.player.PlayerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Pause implements ICommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        if(PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.isPaused()){
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.setPaused(false);
            event.getChannel().sendMessage("```Playback has been resumed```").queue();
        }else{
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.setPaused(true);

            event.getChannel().sendMessage("```Playback has been paused```").queue();
        }
    }

    @Override
    public String getName() {
        return "pause";
    }
}