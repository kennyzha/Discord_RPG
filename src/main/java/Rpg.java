import config.ApplicationConstants;
import listeners.MessageListener;
import net.dv8tion.jda.bot.sharding.DefaultShardManager;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.utils.cache.CacheFlag;
import utils.PostStats;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Rpg {

    public static ShardManager shardManager;
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException{
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder()
                        .setToken(ApplicationConstants.TOKEN)
                        .setDisabledCacheFlags(EnumSet.of(CacheFlag.GAME))
                        .addEventListeners(new MessageListener());
        shardManager = builder.build();
    }

    public static ShardManager getShardManager(){
        return shardManager;
    }
}
