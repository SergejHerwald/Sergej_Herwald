package io.dama.ffi.hoh;

public class Main {
    static SimplifiedList<Integer> list;

    static Runnable sliceSum(final int start, final int end, final int expected) {
        return () -> {
            var sum = 0;
            for (var i = start; i < end; i++) {
                sum += list.get(i);
            }
            if (sum != expected) {
                System.out.println("Fehler in " + Thread.currentThread().getName() + ": " + sum);
            }
        };
    }

    public static void main(final String[] args) throws InterruptedException {
        list = new ThreadsafeSimplifiedList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(i);
        }
        final var thread0 = new Thread(sliceSum(0, 1250, 780625));
        final var thread1 = new Thread(sliceSum(1250, 2500, 2343125));
        final var thread2 = new Thread(sliceSum(2500, 3750, 3905625));
        final var thread3 = new Thread(sliceSum(3750, 5000, 5468125));
        final var start = System.currentTimeMillis();
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.printf("%s: %d ms\n", list.getClass().toString(), System.currentTimeMillis() - start);
    }

}
