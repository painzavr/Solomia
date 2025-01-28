package bots.bot.music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONException;

import java.io.IOException;

public interface ICommand {
    String getName();
    void execute(MessageReceivedEvent event) throws InterruptedException, IOException, JSONException;
}
