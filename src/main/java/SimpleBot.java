import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

@Slf4j
public class SimpleBot extends TelegramLongPollingBot {
    private final String BOT_NAME = "Amitamaru chatBot";
    private final String BOT_TOKEN = "5886340757:AAGDF0ZD0yySRRxu2h5ao5EgdcEHfIDZkOU";


    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Message inMessage = update.getMessage();
            Long chatId = inMessage.getChatId();
            String msg = inMessage.getText();
            String userName = inMessage.getFrom().getUserName();

            String response = createResponse(msg);


            log.info("[{}, {}] : {}", chatId, userName, msg);

            SendMessage outMessage = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(response)
                    .replyMarkup(initKeyBoard())
                    .build();

            try {
                this.sendApiMethod(outMessage);
                log.info("[{}, {}] response: {}", chatId, userName, response);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String createResponse(String msg) {
        String response;
        if (msg.equals("/start") || msg.equalsIgnoreCase("start")) {
            response = "Hello and welcome to demo chat bot. \nNow bot can't do any.\n\"" + BOT_NAME + "\" in developing.";
        } else if ( msg.equalsIgnoreCase("test 1")) {
            response = "Test 1 button works!";
        } else if ( msg.equalsIgnoreCase("test 2")) {
            response = "Test 2 button works!";
        } else {
            response = "You send me: " + msg;
        }
        return response;
    }

    private ReplyKeyboardMarkup initKeyBoard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRow.add(new KeyboardButton("Test 1"));
        keyboardRow.add(new KeyboardButton("Test 2"));
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
