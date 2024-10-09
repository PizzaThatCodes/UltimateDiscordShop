package me.pizzathatcodes.ultimatediscordshop.discord.listeners;

import me.pizzathatcodes.ultimatediscordshop.Main;
import me.pizzathatcodes.ultimatediscordshop.database.DiscordUsersManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.DiscordCoins;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class DiscordOnChatListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        if(util.getDiscordFile().getConfig().getList("coin-settings.blocked-channels").contains(event.getChannel().getId())) {
            Main.getInstance().getLogger().info("Blocked channel");
            return;
        }

        // TODO: give coins to user by using the formula in the config
        Context context = Context.enter();
        String formulaString = util.getDiscordFile().getConfig().getString("coin-settings.coin-per-message");
        int formula;

        try {
            Scriptable scope = context.initStandardObjects();
            Object result = context.evaluateString(scope, formulaString, "RandomNumberScript", 1, null);
            formula = (int) Context.toNumber(result);
        } finally {
            Context.exit();
        }
        DiscordCoins coins = DiscordUsersManager.findDiscordUserByID(event.getAuthor().getId());
        if(coins == null) {
            coins = new DiscordCoins(event.getAuthor().getId(), util.getDiscordFile().getConfig().getInt("coin-settings.startingCoins") + formula);
            DiscordUsersManager.createDiscordUserInformation(coins);
        } else {
            coins.addCoins(formula);
            DiscordUsersManager.updateDiscordUserInformation(coins);
        }

    }
}
