package in.ashar.design_patterns.iterator;

import java.util.List;

public class IterateList <T> implements MyIterator {

    private final List<T> list;
    private int position =0;

    public IterateList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public T next() {
        return list.get(position++);
    }


}
