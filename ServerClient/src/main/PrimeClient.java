package main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PrimeClient extends Application {

 @Override
 public void start(Stage primaryStage) {
	 
	 // create variables and constants
     VBox vbox = new VBox();
     Label label = new Label("Enter a number:");
     TextField inputField = new TextField();
     Button checkButton = new Button("Check Prime");
     Label resultLabel = new Label();
     
     // create button that initiates when pressed
     		checkButton.setOnAction(e -> {
     			
     			// try/catch in case of invalid input
         try {
             int number = Integer.parseInt(inputField.getText());
             boolean isPrime = sendRequestToServer(number);

             if (isPrime) {
                 resultLabel.setText("Yes, it's a prime number.");
             } else {
                 resultLabel.setText("No, it's not a prime number.");
             }
         } catch (NumberFormatException ex) {
             resultLabel.setText("Invalid input. Please enter a valid number.");
         }
     });
     		
     		// Create Stage
     vbox.getChildren().addAll(label, inputField, checkButton, resultLabel);
     Scene scene = new Scene(vbox, 300, 200);

     primaryStage.setTitle("Prime Number Checker");
     primaryStage.setScene(scene);
     primaryStage.show();
 }

 // comm with server
 private boolean sendRequestToServer(int number) {
     try {
         Socket socket = new Socket("localhost", 5555);
         ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
         ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

         // Send the number to the server
         outputStream.writeInt(number);
         outputStream.flush();

         // Receive the result from the server
         boolean isPrime = inputStream.readBoolean();
         
         
         socket.close();
         return isPrime;
     } catch (IOException e) {
         e.printStackTrace();
         return false;
     }
 }

 public static void main(String[] args) {
     launch(args);
 }
}
