package com.tip.functional;

import java.util.function.*;
import java.util.*;

public class Iterators {

  public static <E, R> R reduce(Iterable<E> es, BiFunction<R, E, R> biFunction, R init) {
    R result = init;
    for (E e : es)
      result = biFunction.apply(result, e);

    return result;
  }

  public static <E, R> R reduce(Iterator<E> es, BiFunction<R, E, R> biFunction, R init) {
    return reduce(() -> es, biFunction, init);
  }

  /**
   * 두 Iterator의 equals를 비교한다.
   * 각 iterator의 요소를 equals로 비교하고 reduce로 확인한다.
   * 그 뒤에 다음 요소의 유무를 판별한다.
   * @param xs
   * @param ys
   * @return boolean 요소의 equals 비ㄴ
   * @param <T>
   */
  public static <T> boolean equals(Iterator<T> xs, Iterator<T> ys) {
    if (xs instanceof InfiniteIterator || ys instanceof InfiniteIterator)
      throw new IllegalArgumentException("equals : InfiniteIterator는 equals 할 수 없습니다.");

    Iterator<Boolean> zipIterator = zip(Object::equals, xs, ys);

    return reduce(zipIterator, (o1,o2) -> o1 && o2, true) && (xs.hasNext() == ys.hasNext());
  }


  /**
   * Iterator의 요소를 출력한다.
   * 요소가 하나일 때 -> es.next()로 첫번째 요소 출력
   * 요소가 하나 이상일 떼 ->  separator 를 추가하여 출력
   * @param es
   * @param separator
   * @return
   * @param <T>
   */
  public static <T> String toString(Iterator<T> es, String separator) { // TODO: redude를 써서
    StringBuilder sb = new StringBuilder();

    //원소가 하나일 때
    if (es.hasNext()) {
      sb.append(es.next());
    }

    //원소가 둘 이상일 때
    if (es.hasNext()) {
      sb.append(reduce(es, (str1, str2) -> (str1 + separator) + str2, ""));
    }

    return sb.toString();
  }

  public static <E, R> Iterator<R> map(Iterator<E> es, Function<E, R> function) {
    return new Iterator<R>() {
      public boolean hasNext() {
        return es.hasNext();
      }

      public R next() {
        return function.apply(es.next());
      }
    };
  }

  /**
   * 반복자에 있어 특정 Predicate를 만족하는 요소만을 순회합니다.
   * @param iterator 반복자
   * @param predicate 요소에 대한 필터
   * @return 필터 조건에 만족하는 요소를 return 합니다.
   */
  public static <E> Iterator<E> filter(Iterator<E> iterator, Predicate<E> predicate) {
    // TODO: Bug를 찾을 수 있는 test code를 IteratorTest.filterTest에 쓰고, Bug 고치기
    // findFirst를 써서 풀기
    return new Iterator<E>() {
      private E current;

      // 필터에 맞는 다음 요소가 있는지를 체크
      public boolean hasNext() {
        if(current != null) {
          return true;
        }
        return (current = findFirst(iterator, predicate)) != null;
      }

      //필터에 맞는 다음 요소를 가져옴
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException("filter");
        }

        E old = current;
        current = findFirst(iterator, predicate);
        return old;
      }
    };
  }

  public static <E> E findFirst(Iterator<E> iterator, Predicate<E> predicate) {
    while (iterator.hasNext()) {
      E first = iterator.next();
      if (predicate.test(first))
        return first;
    }
    return null;
  }

  public static <T> InfiniteIterator<T> iterate(T seed, UnaryOperator<T> f) {
    return new InfiniteIterator<T>() {
      T current = seed;

      @Override
      public T next() {
        T old = current;
        current = f.apply(current);
        return old;
      }


    };
  }

  /** iterator의 제한적인 탐색
   *
   * @param iterator
   * @param maxSize
   * @return maxSize 밑이고 hasNext() 가 존재할시에 return 함
   */

  public static <T> Iterator<T> limit(Iterator<T> iterator, long maxSize) { // TODO
    if (maxSize < 0)
      throw new IllegalArgumentException("limit : maxSize가 음수임");
    return new Iterator<T>() {
      private long iteratorCount = 0;
      @Override
      public boolean hasNext() {
          return iterator.hasNext() && iteratorCount < maxSize;
      }

      @Override
      public T next() {
        iteratorCount = Math.addExact(iteratorCount, 1);
        return iterator.next();
      }
    };
  }

  /**
   * 지정된 "Supplier'를 사용하여 요소를 무한으로 생성하는 무한 반복자 생성
   * @param supplier
   * @return T 타입의 무한 반복자
   */
  public static <T> InfiniteIterator<T> generate(Supplier<T> supplier) { // TODO:
    // TODO
    return new InfiniteIterator<T>() {
      @Override
      public T next() {
        return supplier.get();
      }
    };
  }

  public static <X, Y, Z> Iterator<Z> zip(BiFunction<X, Y, Z> biFunction, Iterator<X> xIterator,
      Iterator<Y> yIterator) {
    return new Iterator<Z>() {
      public boolean hasNext() {
        return xIterator.hasNext() && yIterator.hasNext();
      }

      public Z next() {
        if (!hasNext())
          throw new NoSuchElementException("zip");
        return biFunction.apply(xIterator.next(), yIterator.next());
      }
    };
  }

  /**
   * 반복자가 몇개의 요소가 있는지를 return 합니다.
   * @param iterator
   * @return 반복자의 요소의 갯수

   */
  public static <E> long count(Iterator<E> iterator) {
    // TODO: reduce를 써서
    if (iterator instanceof InfiniteIterator)
      throw new IllegalArgumentException("count : InfiniteIterator는 Count 할 수 없습니다.");

    BiFunction<Integer, E, Integer> biFunction = (e1, e2) -> e1 + 1;
    return reduce(iterator, biFunction, 0);
  }

  public static <T> T get(Iterator<T> iterator, long index) {
    if (index < 0)
      throw new IndexOutOfBoundsException("index < " + index);
    return getLast(limit(iterator, index + 1));
  }

  private static <T> T getLast(Iterator<T> iterator) {
    while (true) {
      T current = iterator.next();
      if (!iterator.hasNext())
        return current;
    }
  }

  public static <T> List<T> toList(Iterator<T> iterator) {
    List<T> list = new ArrayList<>();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    return list;
  }

  public static <E> void print(Iterator<E> iterator, String separator,
      java.io.PrintStream printStream) {
    printStream.print(toString(iterator, separator));
  }

  public static <E> void print(Iterator<E> iterator, String separator) {
    print(iterator, separator, System.out);
  }

  public static <E> void println(Iterator<E> iterator, String separator,
      java.io.PrintStream printStream) {
    print(iterator, separator, printStream);
    printStream.println();
  }

  public static <E> void println(Iterator<E> iterator, String separator) {
    println(iterator, separator, System.out);
  }

  public static <E> void println(Iterator<E> iterator) {
    println(iterator, ", ");
  }

  private Iterators() {}

}


