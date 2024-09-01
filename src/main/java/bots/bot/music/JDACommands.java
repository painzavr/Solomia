package bots.bot.music;//

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
@Getter
@Setter
public class JDACommands extends ListenerAdapter {
    private ArrayList<ICommand> commands = new ArrayList();
    private final String prefix;

    public JDACommands(String prefix) {
        this.prefix = prefix;
    }
    public void registerCommand(ICommand command) {
        this.commands.add(command);
    }

    private void init(MessageReceivedEvent event) throws InterruptedException, IOException, JSONException {
        Iterator commandIterator = this.commands.iterator();

        while(commandIterator.hasNext()) {
            ICommand command = (ICommand)commandIterator.next();
            String message = event.getMessage().getContentRaw();
            if (message.startsWith(this.prefix + command.getName())) {
                command.execute(event);
                break;
            }
        }

    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(this.prefix)) {
            try {
                this.init(event);
            } catch (InterruptedException | JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
