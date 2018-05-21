package business;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.SocketFactory;

import org.apache.commons.codec.binary.Base64;

import entities.Utils;

public class MailBL {
	private int delay = 1000;

	private String User;
	private String Pass;
	private String Username;
	private String Password;

	private String HostSMTP;
	private int PortSMTP;

	private String HostIMAP;
	private int PortIMAP;

	private static DataOutputStream dosSMTP;
	private static DataOutputStream dosIMAP;
	private List<Long> ListUID;
	private Long CurrentUID;
	private int Estado;

	private Socket sockIMAP;
	private BufferedReader brIMAP;

	private Socket sockSMTP;
	private BufferedReader brSMTP;

	public MailBL() {
		this.User = "grupo04sc@mail.ficct.uagrm.edu.bo";
		this.Pass = "grupo04grupo04";

		this.HostSMTP = "mail.ficct.uagrm.edu.bo";
		this.PortSMTP = 465;

		this.HostIMAP = "mail.ficct.uagrm.edu.bo";
		this.PortIMAP = 993;

		this.Username = Base64.encodeBase64String(this.User.getBytes(StandardCharsets.UTF_8));
		this.Password = Base64.encodeBase64String(this.Pass.getBytes(StandardCharsets.UTF_8));
		this.ListUID = new ArrayList<Long>();
		this.CurrentUID = 0L;
		this.Estado = 1;
	}

	public MailBL(String user, String pass) {
		this.User = user;
		this.Pass = pass;

		this.HostSMTP = "smtp.gmail.com";
		this.PortSMTP = 465;

		this.HostIMAP = "imap.gmail.com";
		this.PortIMAP = 993;

		this.Username = Base64.encodeBase64String(this.User.getBytes(StandardCharsets.UTF_8));
		this.Password = Base64.encodeBase64String(this.Pass.getBytes(StandardCharsets.UTF_8));
		this.ListUID = new ArrayList<Long>();
		this.CurrentUID = 0L;
		this.Estado = 1;
	}

	private void CrearSMTP() throws UnknownHostException, IOException {
		try{
		sockSMTP = (Socket) ((SocketFactory) SocketFactory.getDefault()).createSocket(this.HostSMTP,
				this.PortSMTP);
		// Socket sock = new Socket("smtp.gmail.com", 587);
		brSMTP = new BufferedReader(new InputStreamReader(sockSMTP.getInputStream()));
		}catch(SocketException sex){
			sex.printStackTrace();
		}
	}

	private void Send() throws Exception {
		CrearSMTP();

		(new Thread(new Runnable() {
			public void run() {
				try {
					String line;
					while (brSMTP != null && (line = brSMTP.readLine()) != null) {
						System.out.println("SERVER: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		})).start();
		dosSMTP = new DataOutputStream(sockSMTP.getOutputStream());

		SendSMTP("EHLO smtp.gmail.com\r\n");
		Thread.sleep(delay);
		SendSMTP("AUTH LOGIN\r\n");
		Thread.sleep(delay);
		SendSMTP(this.Username + "\r\n");
		Thread.sleep(delay);
		SendSMTP(this.Password + "\r\n");
		Thread.sleep(delay);

	}

	public void SendMail(String to, String Subject, String message) {
		try {
			Send();

			SendSMTP("MAIL FROM:<" + User + ">\r\n");
			// send("\r\n");
			Thread.sleep(delay);
			SendSMTP("RCPT TO:<" + to + ">\r\n");
			Thread.sleep(delay);
			SendSMTP("DATA\r\n");
			Thread.sleep(delay);
			SendSMTP("Subject: " + Subject + "\r\n");
			Thread.sleep(delay);
			SendSMTP(message + "\r\n");
			Thread.sleep(delay);
			SendSMTP(".\r\n");
			Thread.sleep(delay);
			SendSMTP("QUIT\r\n");
		} catch (Exception e) {
			e.printStackTrace();

			try {
				sockSMTP.close();
			} catch (IOException e1) {
				e1.printStackTrace();

				SendMail(to, Subject, message);
			}
		}
	}

	private static void SendSMTP(String s) throws Exception {
		dosSMTP.writeBytes(s);
		// System.out.println("CLIENT: " + s);
	}

	private static void SendIMAP(String s) throws Exception {
		dosIMAP.writeBytes(s);
		// System.out.println("CLIENT: " + s);
	}

	public void SearchMail() throws Exception {
		SendIMAP(". SEARCH UNSEEN\r\n");
		Thread.sleep(delay);
	}

	public void ReadSocket() {
		(new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String line;
					String aux = "";

					while ((line = brIMAP.readLine()) != null) {
						// System.out.println("SERVER: " + line + "\r\n");
						// System.out.println("ESTADO: " + Estado + "\n");
						if (Estado == 2) {
							if (line.contains("* SEARCH ")) {
								String[] numbers = line.split(" ");
								int i = 2;
								while (i < numbers.length) {
									ListUID.add(Long.valueOf(numbers[i]));
									i++;
								}

								Estado = 1;
							}
						}

						if (Estado == 3) {
							if (line.contains(
									"* " + CurrentUID + " FETCH (FLAGS (\\Seen) BODY[HEADER.FIELDS (subject from)] ")) {
								aux = line;

								do {
									line = brIMAP.readLine();

									aux = aux + line;
								} while (!line.contains(". OK Success"));

								aux = aux.replace("* " + CurrentUID
										+ " FETCH (FLAGS (\\Seen) BODY[HEADER.FIELDS (subject from)] ", "");
								aux = aux.replace("). OK Success", "");
								AnalizarMessage(aux);
								// System.out.println("MESSAGE: " + aux + "\n");
								Estado = 1;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})).start();
	}

	private void AnalizarMessage(String message) {
		(new Thread(new HiloAnalizador(message) )).start();
	}

	private void ConstructorIMAP() throws UnknownHostException, IOException {
		try{
		sockIMAP = (Socket) ((SocketFactory)SocketFactory.getDefault()).createSocket(this.HostIMAP,
				this.PortIMAP);
		brIMAP = new BufferedReader(new InputStreamReader(sockIMAP.getInputStream()));

		dosIMAP = new DataOutputStream(sockIMAP.getOutputStream());
		}catch(SocketException sex){
			sex.printStackTrace();
		}
	}

	public void ReadMail() throws Exception {

		ConstructorIMAP();
		Thread.sleep(delay);
		ReadSocket();

		SendIMAP(". LOGIN " + this.User + " " + this.Pass + "\r\n");
		Thread.sleep(delay);

		SendIMAP(". LIST \"\" \"*\"\r\n");
		Thread.sleep(delay);

		SendIMAP(". SELECT INBOX\r\n");
		Thread.sleep(delay);

		(new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						// System.out.println("ESTADO: " + Estado + "\n");

						if (ListUID == null || ListUID.size() == 0) {
							Estado = 2;
							SearchMail();

						} else {
							if (ListUID.size() != 0) {
								Long uid = ListUID.get(0);
								CurrentUID = uid;
								Estado = 3;
								SendIMAP(". FETCH " + uid + " body[HEADER.FIELDS (subject from)]\r\n");
								Thread.sleep(delay * 5);
								ListUID.remove(uid);
							}
						}

						if (sockIMAP.isClosed() || sockIMAP.isInputShutdown() || sockIMAP.isOutputShutdown()) {
							try {
								sockIMAP.close();

							} catch (Exception e) {
								e.printStackTrace();
							}

							ConstructorIMAP();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})).start();
	}

}
