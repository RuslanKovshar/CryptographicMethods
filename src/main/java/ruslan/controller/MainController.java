package ruslan.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ruslan.encoder.*;

public class MainController {

    @FXML
    private TextField result;

    @FXML
    private Button encryptBtn;

    @FXML
    private Button decryptBtn;

    @FXML
    private TextArea textInput;

    @FXML
    private TextField key;

    @FXML
    private Text keyText;

    @FXML
    private ChoiceBox<Encoder> choice;

    private Encoder encoder;

    @FXML
    void initialize() {
        ObservableList<Encoder> encoders = FXCollections.observableArrayList(new CaesarEncoder(),
                new GammaEncoder(),
                new BookEncoder(),
                new DESEncoder(),
                new BackpackEncoder(),
                new RSAEncoder(),
                new SignRSAEncoder());

        choice.setItems(encoders);
        choice.setOnAction(event -> {
            encoder = choice.getValue();
            if (encoder instanceof EncoderWithoutKey) {
                key.setVisible(false);
                keyText.setVisible(false);
            } else {
                key.setVisible(true);
                keyText.setVisible(true);
            }
        });

        encryptBtn.setOnAction(actionEvent -> {
            String message = textInput.getText().trim();
            String resultMessage;
            if (encoder instanceof EncoderWithoutKey) {
                EncoderWithoutKey encoderWithoutKey = (EncoderWithoutKey) encoder;
                resultMessage = encoderWithoutKey.encode(message);
            } else {
                EncoderWithKey encoderWithKey = (EncoderWithKey) encoder;
                String keyMessage = key.getText().trim();
                resultMessage = encoderWithKey.encode(message, keyMessage);
            }
            result.setText(resultMessage);
        });

        decryptBtn.setOnAction(actionEvent -> {
            String message = textInput.getText().trim();
            String resultMessage;
            if (encoder instanceof EncoderWithoutKey) {
                EncoderWithoutKey encoderWithoutKey = (EncoderWithoutKey) encoder;
                resultMessage = encoderWithoutKey.decode(message);
            } else {
                EncoderWithKey encoderWithKey = (EncoderWithKey) encoder;
                String keyMessage = key.getText().trim();
                resultMessage = encoderWithKey.decode(message, keyMessage);
            }
            result.setText(resultMessage);
        });
    }
}
