package models.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBuilder <T> {

    private List<T> arrayList;

    private ArrayListBuilder(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    public static <T>ArrayListBuilder<T> arrayListBuilder(List<T> list){
        return new ArrayListBuilder<T>(list);
    }

    public ArrayListBuilder() {
        arrayList = new ArrayList();
    }

    public ArrayListBuilder<T> add(T element){
        arrayList.add(element);
        return new ArrayListBuilder<T>(arrayList);
    }

    public ArrayListBuilder<T> add(T... elements){
        for(T element : elements){
            arrayList.add(element);
        }
        return new ArrayListBuilder<T>(arrayList);
    }

    public List<T> build(){
        return arrayList;
    }
}
