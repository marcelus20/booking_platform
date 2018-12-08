package views.captcha;
import views.customComponents.MyCustomJField;
import views.customComponents.MyCustomJPanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MyCaptcha extends MyCustomJPanel {

    private File captchaFile;
    private Map<String, File> dictionary;
    private MyCustomJField<JTextField> field;

    public MyCaptcha() {
        super("captcha", 300, 200);
        loadCaptchaNames();
    }

    public void loadCaptchaNames() {
        List<File> bf = new ArrayList<>();
        File[] folder = new File("src/main/java/views/captcha/images").listFiles();
        for (File file : folder){
            bf.add(file);
        }
        showImageCaptcha(bf);
    }

    private void showImageCaptcha(List<File> captchaImages) {
        dictionary = MapFilesWithStrings(captchaImages);
        Collections.shuffle(captchaImages);
        Integer randomIndex = new Random().nextInt(captchaImages.size());
        try {
            BufferedImage image = ImageIO.read(captchaImages.get(randomIndex));
            getContent().setLayout(new BorderLayout());
            getContent().add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        field = new MyCustomJField<>(new JTextField(), "upper case matters",50);
        getContent().add(field.getInput(), BorderLayout.SOUTH);
        captchaFile = captchaImages.get(randomIndex);
    }

    public Boolean captchaIsValid(String key){
        return dictionary.get(key) == captchaFile;
    }

    private Map<String, File> MapFilesWithStrings(List<File> bf) {

        Map<String, File> dictionary = new HashMap<>();
        dictionary.put("WKRH5", bf.get(0));
        dictionary.put("PBNN", bf.get(1));
        dictionary.put("N3YS3", bf.get(2));
        dictionary.put("SWY6M", bf.get(3));
        dictionary.put("S5TB", bf.get(4));
        dictionary.put("3JYP4", bf.get(5));
        dictionary.put("UCHB46", bf.get(6));
        dictionary.put("3UPURY", bf.get(7));
        dictionary.put("YKPU3U", bf.get(8));
        dictionary.put("VCKR", bf.get(9));
        dictionary.put("CUXE", bf.get(10));
        dictionary.put("YS4ARE", bf.get(11));
        dictionary.put("HK5B6", bf.get(12));
        dictionary.put("CYKHXD", bf.get(13));
        dictionary.put("YC3P", bf.get(14));

        return dictionary;
    }


    public String getField() {
        return field.getInput().getText();
    }

}
