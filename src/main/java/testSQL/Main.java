package testSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Main {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/rocco";
		String user = "root";
		String password = "root";

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl(url);
		cpds.setUser(user);
		cpds.setPassword(password);

		try (Connection mysqlConnection = cpds.getConnection()) {
			String query = "";
			String nome_tab = "";
			String valore = "";
			try (Scanner scan = new Scanner(System.in)) {
				int num = 0;
				do {
					istruzioni();
					System.out.print("Inserisci il tuo numero: ");
					num = scan.nextInt();
					switch (num) {
					case 1:
						System.out.print("Inserisci il nome della tabella da creare: ");
						nome_tab = scan.next();
						query = "create table " + nome_tab + " (id" + nome_tab
								+ " int not null auto_increment primary key," + "nome varchar(30) not null)";
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							stmt.executeUpdate(query);
						}
						break;
					case 2:
						System.out.print("Inserisci il nome della tabella da eliminare: ");
						nome_tab = scan.next();
						query = "drop table " + nome_tab;
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							stmt.executeUpdate(query);
						}
						break;
					case 3:
						System.out.print("Inserisci il nome della tabella da svuotare: ");
						nome_tab = scan.next();
						query = "truncate table " + nome_tab;
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							stmt.executeUpdate(query);
						}
						break;
					case 4:
						System.out.print("Inserisci il nome della tabella: ");
						nome_tab = scan.next();
						System.out.print("Inserisci valore: ");
						valore = scan.next();
						query = "insert into " + nome_tab + " (nome) values (?)";
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							stmt.setString(1, valore);
							stmt.executeUpdate();
						}
						break;
					case 5:
						System.out.print("Inserisci il nome della tabella: ");
						nome_tab = scan.next();
						System.out.print("Inserisci valore: ");
						valore = scan.next();
						query = "delete from " + nome_tab + " where nome=?";
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							stmt.setString(1, valore);
							stmt.executeUpdate();
						}
						break;
					case 6:
						System.out.print("Inserisci il nome della tabella: ");
						nome_tab = scan.next();
						query = "select * from " + nome_tab;
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							try (ResultSet rs = stmt.executeQuery()) {
								while (rs.next()) {
									int id = rs.getInt("id"+nome_tab);
									String nome = rs.getString("nome");
									System.out.println(id + "\t" + nome);
								}
							}
						}
						break;
						
					case 7:
						System.out.print("Inserisci il nome della tabella: ");
						nome_tab = scan.next();
						query = "select count(*) conta from " + nome_tab;
						try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
							try (ResultSet rs = stmt.executeQuery()) {
								while (rs.next()) {
									int conta = rs.getInt("conta");									
									System.out.println("Il numero delle righe è: "+conta);
								}
							}
						}
						break;
					}
				} while (num != 0);
			}
		}
	}

	private static void istruzioni() {
		System.out.println("Premi 1 per creare una tabella\n" + "Premi 2 per eliminare una tabella\n"
				+ "Premi 3 per svuotare una tabella\n" + "Premi 4 per inserire delle righe\n"
				+ "Premi 5 per eliminare delle righe\n" + "Premi 6 per leggere delle righe\n"
				+ "Premi 7 per contare delle righe\n" + "Premi 0 per uscire");
	}

//	try (PreparedStatement stmt = mysqlConnection.prepareStatement(query)) {
//		stmt.executeUpdate(query);
//		try (ResultSet rs = stmt.executeQuery()) {
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String nome = rs.getString("nome");
//				System.out.println(id + "\t" + nome);
//			}
//	}
}
