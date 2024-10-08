package bots.bot.music.player;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.*;

import com.sedmelluq.discord.lavaplayer.tools.*;
import com.sedmelluq.discord.lavaplayer.track.*;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.*;

public class PlayerManager {

    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager(){

        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        YoutubeAudioSourceManager ytm = new YoutubeAudioSourceManager();
        audioPlayerManager.registerSourceManager(ytm);
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild){
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel textChannel, String trackUrl){
        final GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);

                textChannel.sendMessage("```Adding to queue " + audioTrack.getInfo().title + " by " + audioTrack.getInfo().author + "```").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();
                if(!tracks.isEmpty()){
                    musicManager.scheduler.queue(tracks.get(0));
                    textChannel.sendMessage("```Adding to queue " + tracks.get(0).getInfo().title + " by " + tracks.get(0).getInfo().author + "```").queue();
                }
            }

            @Override
            public void noMatches() {
                textChannel.sendMessage("```Sorry, I couldn't find anything suitable. Could you please provide more details for your request?```").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                textChannel.sendMessage("```An unexpected error occurred; perhaps this will help clarify the situation: " + e.getMessage() + "```").queue();
            }
        });
    }
    public void loadAndPlaylist(TextChannel textChannel, List<String> trackUrl) {
        GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());
        Iterator musicIterator = trackUrl.iterator();

        while(musicIterator.hasNext()) {
            String link = String.valueOf(musicIterator.next());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.audioPlayerManager.loadItemOrdered(musicManager, link, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack audioTrack) {
                    musicManager.scheduler.queue(audioTrack);
                    textChannel.sendMessage("```Adding to queue " + audioTrack.getInfo().title + " by " + audioTrack.getInfo().author + "```").queue();
                }

                @Override
                public void playlistLoaded(AudioPlaylist audioPlaylist) {
                    final List<AudioTrack> tracks = audioPlaylist.getTracks();
                    if(!tracks.isEmpty()){
                        musicManager.scheduler.queue(tracks.get(0));
                        textChannel.sendMessage("```Adding to queue " + tracks.get(0).getInfo().title + " by " + tracks.get(0).getInfo().author + "```").queue();
                    }
                }

                @Override
                public void noMatches() {
                    textChannel.sendMessage("Track wasn't found!Sorry...").queue();
                }

                @Override
                public void loadFailed(FriendlyException e) {
                    textChannel.sendMessage("Failed to load " + link + " because of " + e.getMessage()).queue();
                }
            });
        }
    }

    public static PlayerManager getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

}