package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	static Socket communicationSoket = null;
	static BufferedReader serverInput = null;
	static PrintStream serverOutput = null;
	static BufferedReader konzola = null;

	public static void main(String[] args) {

		try {
			communicationSoket = new Socket("localhost", 9012);
			serverInput = new BufferedReader(new InputStreamReader(communicationSoket.getInputStream()));
			serverOutput = new PrintStream(communicationSoket.getOutputStream());
			konzola = new BufferedReader(new InputStreamReader(System.in));

			new Thread(new Client()).start();

			String input;
			while (true) {
				input = serverInput.readLine();
				System.out.println(input);

				if (input.equals("< Dovidjenja >"))
					break;
			}

			communicationSoket.close();

		} catch (UnknownHostException e) {
			System.out.println("Uneti host nije odgovarajuci!");
		} catch (IOException e) {
			System.out.println("Sa velikom zaloscu vas obavestavamo da je servar crko :(");
		}
	}

	@Override
	public void run() {
		try {
			String poruka;
			while (true) {
				poruka = konzola.readLine();
				serverOutput.println(poruka);
				if (poruka.equals("//izlaz"))
					break;
			}
		} catch (IOException e) {
			System.out.println("Greska na konzoli");
		}

	}

}
