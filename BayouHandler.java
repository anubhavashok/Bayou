public class BayouHandler
{
    public void initiateClientToServer(String ipClient, Write w)
    {
     Instruction I = new Instruction("Write",w);
     sendInstructionToClien(I,ipClient);
    }

    public void startClient(String ipClient, String ipReplica)
    {
      Instruction I= new Instruction("connectToServer",ipReplica);
      sendInstructionToClient(I,ipClient);
    }
    public void clientDisconnect(Client i)
    {
      Client i = Init.nullClient;
    }
    public void clientReconnect(Client i, Replica j)
    {
      startClient(i,j);
    }
    
    public void pause()
    {
     
    }
    public void continue()
    {
    }
    public void printLog()
    {
     for(Node n: totalNodes)
     {
       printLog(n);
     }
    }
    public void printLog(i)
    {
    }
    public void isolate(Node i)
    {
      Node n = totalNodes.get(i);
      totalNodes.remove(i);
      partitionedNodes.add(n);
    }
    public void reconnect(Node i)
    {
      Node n = partitionedNodes.get(i);
      totalNodes.add(n);
    }
    public void breakConnection(Node i, Node j)
    {
      
    }
    //-------------------------------------------------------------------------------------------------------//
    public void runAntiEntropy(Replica i, Replica j)
    {
        Instruction toJ=new Instruction("startAntiEntropy",Bayou.getIP(i.getId()),0);
        sendInstructionToReplica(toJ,Bayou.getIP(j.getId()));
        Instruction toI=new Instruction("startAntiEntropy",Bayou.getIP(j.getId()),1);
        sendInstructionToReplica(toI,Bayou.getIP(i.getId()));
      /*j.selectToSend();                              //j sends write to i
      i.receiveWriteFromReplica();                  //i receives writes from j
            
      i.selectToSend();                              
      j.receiveWriteFromReplica();  */               
    }
    public void runAntiEntropy(PrimaryReplica i, Replica j)
    {
      //where primaryReplica is the one used on this current server
      Instruction toJ=new Instruction("startAntiEntropy",Bayou.getIP(i.getId()),0);
      sendInstructionToReplica(toJ,Bayou.getIP(j.getId()));
      i.receiveWriteFromReplica();                  //i receives writes from j
      i.selectToSend();                              
               
    }
      public void sendInstructionToReplica(Instruction I,String IP)
      {
        try{
            InetAddress svr = InetAddress.getByName(IP);
  //  Thread.sleep(3000);    					//wait for 3 seconds before sending to ensure process has completed processing and is waiting to receive
        Socket skt = new Socket(svr,2000);
        OutputStream os = skt.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(V);
        
        oos.close();
        os.close();
        skt.close();
   //     Thread.sleep(1000);
      	}
      	catch(Exception e)
      	{
      	}
      }
      public void sendInstructionToClient(Instruction I,String IP)
      {
        try{
            InetAddress svr = InetAddress.getByName(IP);
  //  Thread.sleep(3000);        				//wait for 3 seconds before sending to ensure process has completed processing and is waiting to receive
        Socket skt = new Socket(svr,2002);
        OutputStream os = skt.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(V);
        
        oos.close();
        os.close();
        skt.close();
   //     Thread.sleep(1000);
      	}
      	catch(Exception e)
      	{
      	}
      }
    

  
}
