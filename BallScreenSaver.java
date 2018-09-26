package screensaver;

import java.awt.Dimension;
import java.awt.Toolkit;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ikepr
 */
public class BallScreenSaver extends Application{
	
    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
    	//creates balls as a group for linear gradient
         Group balls = new Group(); 
        
        //Get Screen Size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
    	Pane canvas = new Pane();
    	Scene scene = new Scene(canvas, screenSize.getWidth(), screenSize.getHeight());
    
        Bounds bounds = canvas.getBoundsInLocal();
      
        Random random = new Random(); 
        
        ArrayList <Circle> BallList = new ArrayList<Circle>(); 
        ArrayList <Integer> Ball_x = new ArrayList<Integer>(); 
        ArrayList <Integer> Ball_y = new ArrayList<Integer>(); 
        
        //choose your amount of circles
        int circleamount = 300; 
        
           for (int i = 0; i < circleamount; i++) {
            Circle ball = new Circle(40);
           ball.setStrokeType(StrokeType.OUTSIDE);
            ball.setStroke(Color.web("white", 0.16));
           ball.setStrokeWidth(4);
          
          
          Ball_y.add(random.nextInt(2)*2 - 1 ); 
          Ball_x.add(random.nextInt(2)*2 - 1 );
          
                ball.relocate(random.nextInt((int)bounds.getWidth() - (int)ball.getRadius()*3), random.nextInt((int)bounds.getHeight()- (int)ball.getRadius()*3));  
             BallList.add(ball); 
             canvas.getChildren().add(ball);
            balls.getChildren().add(ball);
             
          }
          Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.web("#f8bd55")),
                    new Stop(0.14, Color.web("#c0fe56")),
                    new Stop(0.28, Color.web("#5dfbc1")),
                    new Stop(0.43, Color.web("#64c2f8")),
                    new Stop(0.57, Color.web("#be4af7")),
                    new Stop(0.71, Color.web("#ed5fc2")),
                    new Stop(0.85, Color.web("#ef504c")),
                    new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        Group blendModeGroup = new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),Color.BLACK), balls), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        canvas.getChildren().add(blendModeGroup);
        balls.setEffect(new BoxBlur(10, 10, 1));
        
       
        
      
        
        stage.setTitle("Ball ScreenSaver");
        stage.setScene(scene);
        stage.show();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), 
                new EventHandler<ActionEvent>() {

        	
            @Override
            public void handle(ActionEvent t) {
            	double dx = .76; //Step on x or velocity
        	double dy = .95; //Step on y
        	
                
                for( Circle ball : BallList){
                
                
                //move ball
            	ball.setLayoutX(ball.getLayoutX() + dx * Ball_x.get(BallList.indexOf(ball)) );
            	ball.setLayoutY(ball.getLayoutY() + dy * Ball_y.get(BallList.indexOf(ball)) );

                
                
                //ball reaches the left or right border make the step negative
                if(ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius()) || 
                        ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()) ){

                	Ball_x.set(BallList.indexOf(ball),Ball_x.get(BallList.indexOf(ball)) * -1 ); 

                }

                //ball reaches the bottom or top border make the step negative
                if((ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius())) || 
                        (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius()))){

                	Ball_y.set(BallList.indexOf(ball),Ball_y.get(BallList.indexOf(ball)) * -1 ); 

                }
                
               // System.out.println(BallList.indexOf(ball));
                
                }
             
            
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
      
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
