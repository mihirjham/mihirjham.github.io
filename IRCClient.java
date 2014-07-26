import java.io.*;
import java.net.*;

class IRCClient
{
	public static void main(String args[]) throws Exception
	{
		//Server Details
		String server = "irc.freenode.net";
		String nickname = "mihirjham";
		String login = "mihirjham";

		//IRC Channel we want to join
		String channel = "#irchacks";

		Socket socket = new Socket(server, 6667);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		//Log on
		writer.write("NICK " + nickname + "\r\n");
		writer.write("USER " + login + " 8 * : Mihir's Java IRC Client Bot\r\n");
		writer.flush();

		//Reading lines from the server until connected
		String line = null;

		while((line = reader.readLine()) != null)
		{
			if(line.indexOf("004") >= 0)
			{
				break;
			}
			else if(line.indexOf("443") >= 0)
			{
				System.out.println("Nickname is already in use.");
				return;
			}
		}
		
		//Joining a channel
		writer.write("JOIN " + channel + "\r\n");
		writer.flush();

		//Keep reading lines from the server
		while((line = reader.readLine()) != null)
		{
			//Responding to pings to avoid disconnection
			if(line.toLowerCase().startsWith("PING "))
			{
				writer.write("PONG " + line.substring(5) + "\r\n");
				writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
				writer.flush();
			}
			else
			{
				//Print out lines to client
				System.out.println(line);
			}
		}
	}
}
