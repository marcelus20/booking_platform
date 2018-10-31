package utils;

import models.Location;
import models.users.ServiceProvider;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tools {

    public static String[][] convertListToArray(List<List> list){
        final Integer lineSize = list.get(0).size();
        final Integer listSize = list.size();

        String[][] convertedList = new String[listSize][lineSize];

        for(int i = 0; i < listSize; i++){
            for(int j = 0; j < lineSize; j++){
                convertedList[i][j] = String.valueOf(list.get(i).get(j));
            }
        }
        return convertedList;

    }

    public static ServiceProvider serviceMapper(List<String> line){
        ServiceProvider serviceProvider = new ServiceProvider();
        Location location = new Location();
        serviceProvider.withId(Long.parseLong(line.get(0)));
        serviceProvider.withEmail(line.get(1));
        serviceProvider.withDateOfAccountCreation(Date.valueOf(line.get(2)));
        serviceProvider.withPhone(line.get(3));
        serviceProvider.withCompanyFullName(line.get(4));
        serviceProvider.withApprovedStatus(line.get(5));

        location.withFirstLineAddress(line.get(6));
        location.withEirCode(line.get(7));
        location.withCity(line.get(8));
        location.withSecondLineAddress(line.get(9));
        serviceProvider.withLocations(location);
        return serviceProvider;
    }

//    public static List<String> reduceListToEvery4Element(List<String> biggerList){
//        List<String> reducedList = new ArrayList<>();
//
//        IntStream.range(0, biggerList.size())
//                .forEach(idx->{
//                    if(idx %4 == 0){
//                        reducedList.add(biggerList.get(idx));
//                    }
//                });
//
//        return reducedList;
//    }
}
