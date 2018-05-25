import config.ApplicationConstants;
import listener.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class Rpg {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException{
        JDA jda = new JDABuilder(AccountType.BOT).setToken(ApplicationConstants.TOKEN).buildBlocking();
        jda.addEventListener(new MessageListener());
    }
}
