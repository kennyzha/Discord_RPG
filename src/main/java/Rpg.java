import config.ApplicationConstants;
import listeners.MessageListener;
import net.dv8tion.jda.bot.sharding.DefaultShardManager;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import utils.PostStats;

import javax.security.auth.login.LoginException;

public class Rpg {

    public static ShardManager shardManager;
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException{
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder()
                        .setToken(ApplicationConstants.TOKEN).
                        addEventListeners(new MessageListener());
        shardManager = builder.build();

        System.out.println(shardManager.getShards().size());
        int total = 0;

        for(JDA jda : shardManager.getShards()){
            total += jda.getGuilds().size();
        }
    }

    public static ShardManager getShardManager(){
        return shardManager;
    }
}
