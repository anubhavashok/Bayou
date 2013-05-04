import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class PrimaryReplica extends Replica
{
  PrimaryReplica(int id,String ip)
  {
    super(id, ip);

  }
  //private int CSNCount=0;
  //private ArrayList<Write> committedLog;
  
  public void mergeWithDatabase()
  {
    for(Write w: writeLog)
    {
//-------------ADD---------------//
      if(w.getOp().equals("add"))
      { System.out.println("Song has been added to database");
  database.add(w.getSongEntry());
        int i =writeLog.indexOf(w);
        w.setCSN(CSN);
        CSN++;
        writeLog.set(i,w);
        committedLog.add(w);
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
//-------------DELETE--------------//
      if(w.getOp().equals("delete"))
      {System.out.println("Song has been deleted from database");
	for(SongEntry s: database)
	{
 		if(s.getSongName().equals(w.getSongEntry().getSongName()))
		{
			        database.remove(s);
		}
	}
        int i =writeLog.indexOf(w);
        w.setCSN(CSN);
        CSN++;
        writeLog.set(i,w);
        committedLog.add(w);
        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
//-------------MODIFY--------------//
      if(w.getOp().equals("modify"))
      {
        for(SongEntry s : database)
        {
          if(s.getSongName().equals(w.getSongEntry().getSongName()))
          {
            database.remove(s);
            database.add(w.getSongEntry());
            //store w with updated CSN in writelog for further propogation.
            int i =writeLog.indexOf(w);
            w.setCSN(CSN);
            CSN++;
            writeLog.set(i,w);
            committedLog.add(w);
          }
        }

        //increment CSN, ADD CSN TO WRITE IN WRITE LOG
      }
    }
  }
  /*
  public void selectToSend()
  {
    ArrayList<Integer> V=receiveVersionVector(); 	//Version vector received from replica server
    int RCSN = receiveCSN();				//highest CSN from the replica server
    Thread.sleep(1000);					//wait for Other server to start receiving
    
 //-----------------------//				
	//SEND COMMITTED WRITES
    if(RCSN<CSN)
    {
      for(int i=RCSN; i<CSN; i++) 			//Send over all the committed writes after RCSN
      {
            Write w = committedWriteLog.get(i);
              sendWrite(w);
      }
    }
//-------------------------//
	//SEND TENTATIVE WRITES
    for(int i=0;i<writeLog.size();i++)
    {
      Write w = writeLog.get(i);
      System.out.println("Song that is being sent: "+(w.getSongEntry()).getSongName());
      if(V.get(w.getReplicaId())<=w.getAcceptTime()) 	 //possible error comparing 2 Integers
      {
	System.out.println("sent");
        sendWrite(w);                          		 //send new write to the server
      }
    }
  }*/
}
