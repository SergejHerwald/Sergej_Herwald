package io.dama.ffi.hoh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadsafeSimplifiedList<T> implements SimplifiedList<T> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();
	private Node<T> first;

	private class Node<U> {
		private U element;
		private final Node<U> prev;
		private Node<U> next;

		private Node(final U element, final Node<U> prev, final Node<U> next) {
			super();
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
	}

	public ThreadsafeSimplifiedList() {
		super();
		this.first = null;
	}

	@Override
	public T get(final int i) {	
		try {
			readLock.lock();
			var ptr = this.first;
			for (var j = 0; j < i; j++) {
				ptr = ptr.next;
			}
			return delay(ptr.element);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean add(final T e) {	
		try {
			writeLock.lock();
			if (this.first != null) {
				var ptr = this.first;
				while (ptr.next != null) {
					ptr = ptr.next;
				}
				ptr.next = new Node<>(e, ptr, null);
			} else {
				this.first = new Node<>(e, null, null);
			}
			return true;
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public T set(final int i, final T e) {	
		try {
			writeLock.lock();
			var ptr = this.first;
			for (var j = 0; j < i; j++) {
				ptr = ptr.next;
			}
			ptr.element = e;
			return e;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {	
		try {
			readLock.lock();
			return this.first == null;
		} finally {
			readLock.unlock();
		}
	}
}
