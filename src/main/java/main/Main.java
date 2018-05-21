package main;

import business.MailBL;

public class Main {
	public static void main(String[] args) throws Exception {

		MailBL mail = new MailBL();

		mail.ReadMail();
	}
}
