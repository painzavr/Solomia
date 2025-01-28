package bots.bot;

import bots.bot.coin.ProfileRepository;
import bots.bot.coin.VoiceListener;
import bots.bot.config.Properties;
import bots.bot.playlist.PlaylistRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"bots.bot.coin", "bots.bot", "bots.bot.config"})
@SpringBootApplication
public class mBot {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext applicationContext = SpringApplication.run(mBot.class, args);
        Properties properties = applicationContext.getBean(Properties.class);
        ProfileRepository profileRepository = applicationContext.getBean(ProfileRepository.class);
        PlaylistRepository playlistRepository = applicationContext.getBean(PlaylistRepository.class);
        VoiceListener voiceListener = new VoiceListener(profileRepository);

        UtilJdaConfiguration.initialization("!", properties, profileRepository, playlistRepository, voiceListener);

    }



}
