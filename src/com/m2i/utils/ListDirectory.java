package com.m2i.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListDirectory {
	
	public static void main(String[] args) throws IOException {
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "dir");
		Process process = builder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "CP850"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}

