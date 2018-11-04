package main;

import utils.Tools;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main extends JFrame {

    Map<Integer, Integer> map;

    public Main(){
        map = new HashMap<>();
        Tools.MyCustomDateTime datetime = new Tools.MyCustomDateTime();

        List<Timestamp> timestamps= new ArrayList<>();
        for(int i = 0; i < 24; i++){
            timestamps.add(datetime.getTimestamp());
            datetime = datetime.add30();
        }
        List<JButton> buttonList = timestamps.stream()
                .map(timestamp->new JButton(String.valueOf(timestamp))).collect(Collectors.toList());
        List<JButton> availableButtons = new ArrayList<>();




        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));
        JPanel panel = new JPanel();
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(0,4));

        outputPanel.setPreferredSize(new Dimension(400,300));
        panel.setLayout(new GridLayout(0,4));

        buttonList.forEach(button->{
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton newButton = new JButton(button.getText());
                    availableButtons.add(newButton);
                    button.setEnabled(false);

                    Integer newButtonIndex = availableButtons.size()-1;
                    JButton currentButton = availableButtons.get(newButtonIndex);

                    currentButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            button.setEnabled(true);
                            outputPanel.remove(newButton);
                            availableButtons.remove(newButtonIndex);
                            repaint();
                            validate();
                        }
                    });

                    outputPanel.add(newButton);



                    repaint();
                    validate();

                    System.out.println(map);
                }
            });

            panel.add(button);

        });


        mainPanel.add(panel);
        mainPanel.add(outputPanel);
        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(800,700);
        this.repaint();
        this.validate();

    }

    public static void main(String... args) throws SQLException {
//
//        new Application();

        new Main();
    }
}
