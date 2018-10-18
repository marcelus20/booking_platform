package main;

import controllers.LoginController;
import interfaces.Controlls;


public class Main {

    private Main(){
        new LoginController();

    }


    public static void main(String... args){
        new Main();


    }
}
