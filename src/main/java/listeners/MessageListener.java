package listeners;

import config.ApplicationConstants;
import config.MonsterConstants;
import database.PlayerDatabase;
import handlers.*;
import models.CombatResult;
import models.Monster;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

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
        if(event.getAuthor().isBot()){
            return;
        }
        new CommandHandler(playerDatabase, messageHandler, highscoreHandler).handleCommand(event);
    }
}
