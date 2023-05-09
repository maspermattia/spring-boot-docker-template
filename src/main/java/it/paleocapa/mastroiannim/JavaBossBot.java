package it.paleocapa.mastroiannim;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.api.objects.Message;




import java.util.ArrayList;
import java.util.List;

@Service
public class JavaBossBot extends TelegramLongPollingBot {
	private List<String> ordini = new ArrayList<>();

	

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
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String text = message.getText();

            if (text.startsWith("/crealista")) {
                // Comando per creare una nuova lista di ordinazioni
                ordini.clear();
                inviaMessaggio(chatId, "Lista creata con successo!");
            } else if (text.startsWith("/aggiungi")) {
                // Comando per aggiungere un'ordinazione alla lista
                String ordine = text.substring(9);
                if (!ordine.isEmpty()) {
                    ordini.add(ordine);
                    inviaMessaggio(chatId, "Ordine aggiunto alla lista: " + ordine);
                } else {
                    inviaMessaggio(chatId, "Inserisci un nome valido per l'ordine!");
                }
            } else if (text.startsWith("/rimuovi")) {
                // Comando per rimuovere un'ordinazione dalla lista
                String ordine = text.substring(8);
                if (ordini.remove(ordine)) {
                    inviaMessaggio(chatId, "Ordine rimosso dalla lista: " + ordine);
                } else {
                    inviaMessaggio(chatId, "L'ordine non è presente nella lista!");
                }
            } else if (text.startsWith("/lista")) {
                // Comando per visualizzare l'elenco completo delle ordinazioni attuali
                StringBuilder elenco = new StringBuilder("Elenco degli ordini:\n");
                for (String ordine : ordini) {
                    elenco.append("- ").append(ordine).append("\n");
                }
                inviaMessaggio(chatId, elenco.toString());
            } else if (text.startsWith("/conferma")) {
                // Comando per confermare l'ordinazione e inviarla al barista
                StringBuilder conferma = new StringBuilder("Ordini confermati:\n");
                for (String ordine : ordini) {
                    conferma.append("- ").append(ordine).append("\n");
                }
                inviaMessaggio(chatId, conferma.toString() + "Grazie per aver ordinato!");
                ordini.clear();
            } else if (text.startsWith("/annulla")) {
                // Comando per annullare l'ordinazione corrente e cancellare la lista
                ordini.clear();
                inviaMessaggio(chatId, "Ordine annullato!");
            } else if (text.startsWith("/aiuto")) {
                // Comando per visualizzare una guida sui comandi disponibili
                StringBuilder guida = new StringBuilder("Ecco la lista dei comandi disponibili:\n");
                guida.append("/crealista - per creare una nuova lista di ordinazioni\n");
                guida.append("/aggiungi [nome] - per aggiungere un'ordinazione alla lista\n");
                guida.append("/rimuovi [nome] - per rimuovere un 'ordinazione dalla lista\n");
				guida.append("/lista - per visualizzare l'elenco completo delle ordinazioni attuali\n");
				guida.append("/conferma - per confermare l'ordinazione e inviarla al barista\n");
				guida.append("/annulla - per annullare l'ordinazione corrente e cancellare la lista\n");
				guida.append("/aiuto - per visualizzare una guida sui comandi disponibili\n");
				inviaMessaggio(chatId, guida.toString());
				} else {
				// Comando non valido, invia un messaggio di errore
				inviaMessaggio(chatId, "Comando non valido! Utilizza /aiuto per visualizzare la lista dei comandi disponibili.");
				}
			}
		}
		private void inviaMessaggio(long chatId, String testo) {
			SendMessage messaggio = new SendMessage();
			messaggio.setChatId(chatId);
			messaggio.setText(testo);
			try {
				execute(messaggio);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
}

    

        

  


