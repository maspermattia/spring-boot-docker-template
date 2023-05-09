package it.paleocapa.mastroiannim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBotConfig.class);


    @Value("${listaBarMM_bot}")
    private String botUsername;

    @Value("${6062709765:AAF9GB4ByA82rw7E6wl2nCJezZjYvnRFqyg}")
    private String botToken;

    @Bean
    public TelegramLongPollingBot telegramBot() {
        LOG.info(botToken);
        LOG.info(botUsername);
        return JavaBossBot.getJavaBossBotInstance(botUsername, botToken);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot());
        return telegramBotsApi;
    }
}
