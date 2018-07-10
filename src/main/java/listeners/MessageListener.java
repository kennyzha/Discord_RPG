package listeners;

import config.ApplicationConstants;
import config.MonsterConstants;
import database.PlayerDatabase;
import handlers.CombatHandler;
import handlers.CommandHandler;
import handlers.TrainingHandler;
import models.CombatResult;
import models.Monster;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()){
            return;
        }
        new CommandHandler().handleCommand(event);
    }
}
