package main;

import controllers.Application;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

public class Main {



    public static void main(String... args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        new Application();

    }
}
