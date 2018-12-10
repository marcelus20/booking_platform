package models.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for making an arrayList more easily by recursively calling the add() method with the element
 * you want to add. Eg: builder.add(1).add(2).add(3).build = [1,2,3]
 * @param <T> any type
 */
public class ArrayListBuilder <T> {

    private List<T> arrayList; // the array list to be manipulated

    /**
     * private constructor, it will be made by factoring
     * @param arrayList
     */
    private ArrayListBuilder(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    /**
     * public factory static method to initialise the provate constructor
     * @param list
     * @param <T>
     * @return
     */
    public static <T>ArrayListBuilder<T> arrayListBuilder(List<T> list){
        return new ArrayListBuilder<T>(list);
    }

    /**
     * empty construcotr
     */
    public ArrayListBuilder() {
        arrayList = new ArrayList();
    }

    /**
     * add method that returns another ArrayListBuilder with the current attribute state.
     * This will allow recursion add calls
     * @param element
     * @return
     */
    public ArrayListBuilder<T> add(T element){
        arrayList.add(element);
        return new ArrayListBuilder<T>(arrayList);
    }

    /**
     * same for passing n arguments. instead of doig:
     * builder.add(a).add(b).add(c)...
     * just do builder.add(a, b, c...)
     * @param elements
     * @return
     */
    public ArrayListBuilder<T> add(T... elements){
        for(T element : elements){
            arrayList.add(element);
        }
        return new ArrayListBuilder<T>(arrayList);
    }

    /**
     * returns the arrayList attribute itself (getter)
     * @return
     */
    public List<T> build(){
        return arrayList;
    }
}
