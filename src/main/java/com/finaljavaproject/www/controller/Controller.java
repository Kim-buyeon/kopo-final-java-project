package com.finaljavaproject.www.controller;

public interface Controller<T> {
	void start();
	void processCreate();
	void processRead();
	void processUpdate();
	void processDelete();
}
