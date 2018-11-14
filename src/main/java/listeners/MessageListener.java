package listeners;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import config.ApplicationConstants;
import database.PlayerDatabase;
import handlers.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {
    private PlayerDatabase playerDatabase;
    private MessageHandler messageHandler;
    private HighscoreHandler highscoreHandler;
    private LoadingCache<String, Integer> rateLimitCache;

    public MessageListener(){
        this.playerDatabase = new PlayerDatabase();
        this.messageHandler = new MessageHandler();
        this.highscoreHandler = new HighscoreHandler();
        initRateLimitCache();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot() || ApplicationConstants.getBlackList().contains(event.getAuthor().getId())){
            return;
        }

        if(ApplicationConstants.TEST_SERVER && checkIsTestServer(event)){
            new CommandHandler(playerDatabase, messageHandler, highscoreHandler, rateLimitCache).handleCommand(event);
        }
    }

    public boolean checkIsTestServer(MessageReceivedEvent event){
        if((!event.getMessage().getChannelType().isGuild() ||
                        !event.getGuild().getId().equals(ApplicationConstants.OFFICIAL_GUILD_ID) ||
                        !event.getChannel().getId().equals("495015240124203019"))){
            System.out.println("not official server channel. " + event.getAuthor().getName() + event.getAuthor().getDiscriminator()
                    + " : " + event.getMessage().toString());
            return false;
        }
        return true;
    }

    public void initRateLimitCache(){
        CacheLoader<String, Integer> loader = new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                System.out.println("loadingg");
                return 0;
            }
        };

        this.rateLimitCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build(loader);
    }

}
