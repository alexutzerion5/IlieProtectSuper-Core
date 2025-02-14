package xyz.mythicalsystems.McPanelX.src.Discord.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import xyz.mythicalsystems.McPanelX.src.Config.Config;
import xyz.mythicalsystems.McPanelX.src.Discord.Bot;
import xyz.mythicalsystems.McPanelX.src.Link.Discord;
import xyz.mythicalsystems.McPanelX.src.Messages.Messages;

public class UnLinkDiscordCommand extends Bot {

    public static void register(String name, String description) {
        SlashCommand command = SlashCommand.with(name, description)
                .createForServer(bot, Config.getSetting().getLong("Discord.guild"))
                .join();

        bot.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();
            if (interaction.getCommandName().equals(command.getName())) {
                String user_id = interaction.getUser().getIdAsString();
                if (Discord.isAccountLinked(user_id)) {
                    Discord.unLinkDiscord(user_id);
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(getMessage("Bot.Commands.Unlink.Success.Title"))
                            .setDescription(getMessage("Bot.Commands.Unlink.Success.Description"))
                            .setColor(java.awt.Color.GREEN)
                            .setFooter(Bot.copyright);
                    interaction.createImmediateResponder().addEmbed(embed).respond();
                } else {
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(getMessage("Bot.Commands.Unlink.NotLinked.Title"))
                            .setDescription(getMessage("Bot.Commands.Unlink.NotLinked.Description"))
                            .setColor(java.awt.Color.RED)
                            .setFooter(Bot.copyright);
                    interaction.createImmediateResponder().addEmbed(embed).respond();
                }
            }
        });
    }

    /**
     * Get a message from the messages.yml file
     */
    private static String getMessage(String text) {
        return Messages.getMessage().getString(text);
    }
}
