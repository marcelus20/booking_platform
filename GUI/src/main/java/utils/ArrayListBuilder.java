package utils;


import java.util.ArrayList;
import java.util.List;

public class ArrayListBuilder <T> {
    private List<T> arrayList;

    public ArrayListBuilder(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayListBuilder() {
        arrayList = new ArrayList<>();
    }

    public List<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<T> arrayList) {
        this.arrayList = arrayList;
    }
    public ArrayListBuilder<T> add(T component){
        arrayList.add(component);
        return new ArrayListBuilder<>(arrayList);
    }
    public List<T> build(){
        return arrayList;
    }

}
