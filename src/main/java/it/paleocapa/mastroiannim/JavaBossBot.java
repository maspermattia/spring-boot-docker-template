package it.paleocapa.mastroiannim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class JavaBossBot extends TelegramLongPollingBot {

	private static final Logger LOG = LoggerFactory.getLogger(JavaBossBot.class);

	private String botUsername;
	private static String botToken;
	private static JavaBossBot instance;

	public static JavaBossBot getJavaBossBotInstance(String botUsername, String botToken){
		if(instance == null) {
			instance = new JavaBossBot();
			instance.botUsername = botUsername;
			JavaBossBot.botToken = botToken;
		}
		return instance;
	}

	private JavaBossBot(){
		super(botToken);
	}

	@Override
	public String getBotToken() {
		return botToken;
	}
	
	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	@Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            if ("/start".equals(text)) {
                sendMessage(message.getChatId(), "Benvenuto nel bar! Cosa vuoi ordinare?",
                        createOrderKeyboard());
            }
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            String data = callbackQuery.getData();
            if ("bevande".equals(data)) {
                sendMessage(callbackQuery.getMessage().getChatId(),
                        "Hai scelto le bevande! Cosa vuoi bere?", null);
            } else if ("panini".equals(data)) {
                sendMessage(callbackQuery.getMessage().getChatId(),
                        "Hai scelto i panini! Cosa vuoi mangiare?", null);
            }
        }
    }

    private void sendMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text)
                .setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createOrderKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton bevandeButton = new InlineKeyboardButton()
                .setText("Bevande")
                .setCallbackData("bevande");

        InlineKeyboardButton paniniButton = new InlineKeyboardButton()
                .setText("Panini")
                .setCallbackData("panini");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(bevandeButton);
        firstRow.add(paniniButton);

        keyboard.add(firstRow);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

}
