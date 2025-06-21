package taskone;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public synchronized void reverse() {
        Collections.reverse(strings);
    }

    public synchronized List<String> search(String word) {
        return strings.stream()
                .filter(s -> s.contains(word))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return strings.toString();
    }
}