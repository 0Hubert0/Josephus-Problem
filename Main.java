package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        int height = 717;
        int width = 1200;
        int recHeight = 50;
        final int[] step = { 2 };
        final int[] remainder = new int[1];
        final int[] remainder3 = new int[1];
        final int[] remainder2 = new int[1];
        final int[] numberOfPeople = new int[1];
        final int[] numberOfPeopleInRow = new int[1];
        final int MAX=168, MIN=2;
        final int[] timelineCounter = {-1};
        final boolean[] toBeChanged = {true};

        AnchorPane root = new AnchorPane();

        Image image2 = new Image("file:human.png");
        Image image3 = new Image("file:arrow.png");

        Scene scene = new Scene(root, width, height+recHeight);

        Rectangle rec = new Rectangle(0, height, width, recHeight);
        rec.setStroke(Color.BLACK);
        rec.setFill(Color.BEIGE);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gr = canvas.getGraphicsContext2D();
        Image image = new Image("file:background.png");
        gr.drawImage(image, 0, 0);

        Canvas canvas1 = new Canvas(scene.getWidth(), 300);
        GraphicsContext gr1 = canvas1.getGraphicsContext2D();
        canvas1.setTranslateY(scene.getHeight()-recHeight-200);

        Button startButton = new Button("start");
        startButton.setFont(Font.font("Comic Sans MS"));
        startButton.setTranslateX(1050);
        startButton.setTranslateY(height+10);

        Button setButton = new Button("set");
        setButton.setFont(Font.font("Comic Sans MS"));
        setButton.setTranslateX(950);
        setButton.setTranslateY(height+10);

        Text numberText = new Text("Students' number: ");
        numberText.setFont(Font.font("Comic Sans MS"));
        numberText.setFont(Font.font(25));
        numberText.setTranslateX(10);
        numberText.setTranslateY(height+33);

        Text eliminationText = new Text("Elimination step: ");
        eliminationText.setFont(Font.font("Comic Sans MS"));
        eliminationText.setFont(Font.font(25));
        eliminationText.setTranslateX(455);
        eliminationText.setTranslateY(height+33);

        TextField numberTextField = new TextField("20");
        numberTextField.setFont(Font.font("Comic Sans MS"));
        numberTextField.setTranslateX(220);
        numberTextField.setTranslateY(height+10);

        TextField stepTextField = new TextField("2");
        stepTextField.setFont(Font.font("Comic Sans MS"));
        stepTextField.setTranslateX(645);
        stepTextField.setTranslateY(height+10);

        root.getChildren().addAll(rec, canvas, canvas1, startButton, setButton,
                numberText, eliminationText, numberTextField, stepTextField);

        Circle circle = new Circle();
        final Person[] current = new Person[1];
        final Person[] previous = new Person[1];

        setButton.setOnAction(event -> {
            circle.empty();
            gr1.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            numberOfPeople[0] = Integer.parseInt(numberTextField.getText());
            step[0] =Integer.parseInt(stepTextField.getText());
            if(step[0] <1)
            {
                step[0] =1;
                stepTextField.setText("1");
            }
            if(numberOfPeople[0] >MAX)
            {
                numberTextField.setText("168");
                numberOfPeople[0] =MAX;
            }
            if(numberOfPeople[0] <2)
            {
                numberTextField.setText("2");
                numberOfPeople[0] =MIN;
            }
            remainder[0] = numberOfPeople[0] %3;
            remainder3[0] = (int)(canvas1.getWidth()/(numberOfPeople[0] /3+ remainder[0])/2);
            remainder2[0] = (int)(canvas1.getWidth()/(numberOfPeople[0] /3)/2);
            numberOfPeopleInRow[0] = numberOfPeople[0] /3;
            for (int i = 0; i <2; i++) {
                for (int j = 0; j < numberOfPeopleInRow[0]; j++) {
                    Person person = new Person((int)(remainder2[0] +j * (canvas1.getWidth() / numberOfPeopleInRow[0]))-8,
                            10+i * 60);
                    circle.add(person);
                    gr1.drawImage(image2, remainder2[0] +j * (canvas1.getWidth() / numberOfPeopleInRow[0])-8, 10+i * 60);
                }
            }
            for (int i = 0; i < numberOfPeople[0] /3+ remainder[0]; i++) {
                Person person = new Person((int)(remainder3[0] +i *
                        (canvas1.getWidth() / ((double)numberOfPeople[0] / 3+ remainder[0])))-8, 130);
                circle.add(person);
                gr1.drawImage(image2,
                        (int)(remainder3[0] +i * (canvas1.getWidth() / ((double) numberOfPeople[0] / 3+ remainder[0])))-8, 130);
            }
            current[0] = circle.getFirst();
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if(timelineCounter[0]!=-1){
                gr1.clearRect(previous[0].getX(), previous[0].getY()-13,
                        image3.getWidth(), image3.getHeight());
                if(timelineCounter[0]%step[0]==0){
                    gr1.clearRect(current[0].getX(), current[0].getY(), image2.getWidth(), image2.getHeight());
                    gr1.drawImage(image3, current[0].getX(), current[0].getY()-13);
                    previous[0] = current[0];
                    current[0] = current[0].getPrevious();
                    circle.pop(current[0].getNext());
                    toBeChanged[0]=false;
                    timelineCounter[0] = 0;
                }
            }
            else{
                timelineCounter[0]=1;
            }
            if(toBeChanged[0]) {
                previous[0] = current[0];
                gr1.drawImage(image3, current[0].getX(), current[0].getY()-13);
            }
            else toBeChanged[0] = true;
            current[0] = current[0].getNext();
            timelineCounter[0]++;
        }));

        startButton.setOnAction(event -> {
            timeline.setCycleCount(step[0] * numberOfPeople[0]-2);
            timeline.play();
        });

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
