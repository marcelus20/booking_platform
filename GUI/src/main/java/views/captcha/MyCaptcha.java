package views.captcha;
import models.utils.Tools;
import views.customComponents.MyCustomFont;
import views.customComponents.MyCustomJButton;
import views.customComponents.MyCustomJField;
import views.customComponents.MyCustomJPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


/**
 * THIS IS MY CAPTCHA CLASS
 * The algorithmic steps for the generation of a the MyCaptcha is the following:
 *
 *
 * First: The Tools.createRandomText() method will generate a string with Random characters
 * with length of 7 with upper lowar case and numbers randomly
 *
 * Second: Once the randomText is generated, an image will be created from the text using the method
 * drawImageFromText()
 *
 * Third: after the image has been created, it will be skewed/distorted by using the method skewImage()
 *
 * Once these three steps are finished, it will gather a textfield, a button for change captcha and the image
 * in one panel (getContent())
 *
 *
 * THIS IS THE ONLY CLASS IN THE WHOLE PROJECT THAT USES THE LIBRARY JAVAFX THAT IS USED JUST FOR DISTORTING THE IMAGE
 * IN THE METHOD skewImage()
 */
public class MyCaptcha extends MyCustomJPanel {


    /**
     * ATTRIBUTES
     */
    private String randomText;
    private BufferedImage img;
    private JButton changeCaptcha;
    private JTextField field;


    /**
     * CONSTRUTOR
     */
    public MyCaptcha() {
        super("CAPTCHA - UPPER AND LOWER CASE MATTER", 400, 300);
        changeCaptcha = new MyCustomJButton("change captcha", 300,30).getButton();
        field = new MyCustomJField<JTextField>(new JTextField(), 25).getInput();
        generateCaptcha();


        /**
         * ON THE CLICK OF BUTTON, A NEW CAPTCHA WILL BE GENERATED
         */
        changeCaptcha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContent().removeAll();//removing first everything
                generateCaptcha();//generating and populating panel again
            }
        });
    }

    /**
     * generating the captcha image
     */
    public void generateCaptcha(){
        randomText = Tools.createRandomText();
        img = SkewImage(drawImageFromText(randomText));
        mountCaptchaComponent();
    }

    /**
     * gathering all attributes in one panel
     */
    private void mountCaptchaComponent(){
        getContent().setLayout(new BorderLayout());
        getContent().add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
        getContent().add(changeCaptcha, BorderLayout.NORTH);
        getContent().add(field, BorderLayout.SOUTH);
        validate();
        repaint();
    }


    /**
     * This method will draw an image from the passed string as parameter.
     * @param text to be drawn in image
     * @return the image itself
     */
    private BufferedImage drawImageFromText(String text) {
        BufferedImage bufferedImage = new BufferedImage(300, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.setFont(new MyCustomFont("serif",40).getFont());
        g.drawString(text, 95, 50);
        return bufferedImage;
    }


    /**
     * Method for distorting image: uses JAVAFX Library
     * @param image base image to be distorted
     * @return distorted image
     */

    private static BufferedImage SkewImage(BufferedImage image) {
        new JFXPanel();
        final BufferedImage[] imageContainer = new BufferedImage[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            int width = image.getWidth();
            int height = image.getHeight();
            Canvas canvas = new Canvas(width, height);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
            PerspectiveTransform trans = new PerspectiveTransform();
            trans.setUlx(0);
            trans.setUly(height / 4);
            trans.setUrx(width);
            trans.setUry(0);
            trans.setLrx(width);
            trans.setLry(height);
            trans.setLlx(0);
            trans.setLly(height - height / 2);
            imageView.setEffect(trans);
            imageView.setRotate(2);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            Image newImage = imageView.snapshot(params, null);
            graphicsContext.drawImage(newImage, 0, 0);
            imageContainer[0] = SwingFXUtils.fromFXImage(newImage, image);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return imageContainer[0];
    }


    /**
     * FOR THE LOGINCONTROLLER TO USE:
     * When login button is hit, this method is triggered, if textfield text matches the random text, it will return true.
     * @return false or true
     */
    public Boolean captchaIsValid(){
        return  field.getText().equals(randomText) ;
    }

}
