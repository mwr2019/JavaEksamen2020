package Server;

import sample.Bruker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TaskBrukerLogin implements Runnable {
    Socket socket;
    DataOutputStream utTekst = null;
    ObjectOutputStream outObject = null;

    int personID;
    ArrayList<Bruker> interessertI = new ArrayList<>();
    ArrayList<Bruker> likerMeg = new ArrayList<>();
    Bruker minBruker;

    public TaskBrukerLogin(Socket socket, int personID){
        this.socket = socket;
        this.personID = personID;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bruker Login");

            //Lag dataOutput til klient
            //utTekst = new DataOutputStream(socket.getOutputStream());

            outObject = new ObjectOutputStream(socket.getOutputStream());

            minBruker = DatingDB.minProfil(personID);
            System.out.println(minBruker);
            interessertI = DatingDB.mineValg(personID);
            System.out.println(interessertI);
            likerMeg = DatingDB.interessertIMeg(personID);
            System.out.println(likerMeg);

            outObject.writeObject(interessertI);
            outObject.writeObject(likerMeg);
            outObject.writeObject(minBruker);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                utTekst.close();
                outObject.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
