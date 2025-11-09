package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    final private T[] elements;
    private Predicate<T> predicate;

    public IterableWithPolicyImpl(T[] elements, Predicate<T> predicate){
        this.elements = elements;
        this.predicate = predicate;
    }

    public IterableWithPolicyImpl(T[] elements){
        this(elements, new Predicate<>() {
            public boolean test(final T elem) {
                return true;
            }
        }
        );
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl();
    }

        @Override
    public String toString() {
        final List<T> filteredElements = new ArrayList<>();

        for (final T element : this) {
            filteredElements.add(element);
        }

        return filteredElements.toString();
    }

    class IteratorImpl implements Iterator<T>{

        private int index = 0;

        @Override
        public boolean hasNext() {
            while(IterableWithPolicyImpl.this.elements.length > index
                     && !IterableWithPolicyImpl.this.predicate.test(IterableWithPolicyImpl.this.elements[index])){
                        index++;
                }
            if (index < IterableWithPolicyImpl.this.elements.length){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public T next() {
            if (hasNext()){
                return IterableWithPolicyImpl.this.elements[index++];
            }
            return null;
        }
        
    }
    
}
