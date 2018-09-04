package listeners;

import config.ApplicationConstants;
import database.PlayerDatabase;
import handlers.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    private PlayerDatabase playerDatabase;
    private MessageHandler messageHandler;
    private HighscoreHandler highscoreHandler;

    public MessageListener(){
        this.playerDatabase = new PlayerDatabase();
        this.messageHandler = new MessageHandler();
        this.highscoreHandler = new HighscoreHandler();
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot() || ApplicationConstants.getBlackList().contains(event.getAuthor().getId())){
            return;
        }
        new CommandHandler(playerDatabase, messageHandler, highscoreHandler).handleCommand(event);
    }


}
