package bots.bot;

import bots.bot.coin.ProfileRepository;
import bots.bot.coin.VoiceListener;
import bots.bot.commands.SlashCommandListener;
import bots.bot.config.Properties;
import bots.bot.music.JDACommands;
import bots.bot.music.commands.*;
import bots.bot.playlist.PlaylistRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;

public class UtilJdaConfiguration {
    public static JDA jda;
    public final static GatewayIntent[] INTENTS = {GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT};

    public static void initialization(String prefix, Properties properties, ProfileRepository profileRepository, PlaylistRepository playlistRepository, VoiceListener voiceListener) throws InterruptedException {


        JDACommands jdaCommands = new JDACommands(prefix);
        jdaCommands.registerCommand(new Play());
        jdaCommands.registerCommand(new Stop());
        jdaCommands.registerCommand(new Skip());
        jdaCommands.registerCommand(new Pause());
        jdaCommands.registerCommand(new CurrentTrack());
        jdaCommands.registerCommand(new YouTube());

        jda = JDABuilder.createDefault(properties.getToken())
                .setActivity(Activity.watching("checks"))
                .addEventListeners(jdaCommands)
                .addEventListeners(voiceListener)
                .addEventListeners(new SlashCommandListener(profileRepository, playlistRepository))
                .enableIntents(Arrays.asList(INTENTS))
                .build();
        jda.updateCommands().addCommands(
                Commands.slash("play", "Play the chosen playlist by its name.")
                        .addOption(OptionType.STRING, "title", "Title of the playlist", true),
                Commands.slash("balance", "Check your account balance."),
                Commands.slash("create", "Create your own playlist.")
                        .addOption(OptionType.STRING, "title", "Title of the playlist", true)
                        .addOption(OptionType.STRING, "tracks", "List of tracks separated by triple spaces", true),
                Commands.slash("list", "Show the list of playlists.")
                        .addOption(OptionType.STRING, "title", "Show the track list of a specific playlist.")
                        .addOption(OptionType.INTEGER, "number", "Page number"),
                Commands.slash("add", "Add tracks to a playlist.")
                        .addOption(OptionType.STRING, "title", "Name of the playlist to which the tracks will be added", true)
                        .addOption(OptionType.STRING, "tracks", "Names of the tracks to be added, separated by triple spaces", true),
                Commands.slash("remove", "Remove tracks from a playlist.")
                        .addOption(OptionType.STRING, "title", "Name of the playlist from which the tracks will be removed", true)
                        .addOption(OptionType.INTEGER, "number", "Track number to be removed", true),
                Commands.slash("delete", "Delete a chosen playlist.")
                        .addOption(OptionType.STRING, "title", "Title of the playlist to be deleted"),
                Commands.slash("queue", "Check the current composition queue."),
                Commands.slash("spotify", "Convert a Spotify playlist to a Discord one.")
                        .addOption(OptionType.STRING, "link", "Link to your Spotify playlist to be converted")
                        .addOption(OptionType.STRING, "title", "Name of the playlist to be created or to be expanded")
                        .addOption(OptionType.INTEGER, "number", "Amount of composition to be added")
        ).queue();
        jda.awaitReady();
    }
}
