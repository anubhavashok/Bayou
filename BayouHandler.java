public class BayouHandler
{
    public void initiateClientToServer(Client c, Replica s, Write w)
    {
     c.setServer(s);
     s.receiveWriteFromClient();
     c.writeToServer(w);
    }

    public void startClient(Client i, Replica j)
    {
      Client i = new Client(j.getInetAddress());
      //Client i is a locally stored client
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
      j.selectToSend();                              //j sends write to i
      i.receiveWriteFromReplica();                  //i receives writes from j
            
      i.selectToSend();                              
      j.receiveWriteFromReplica();                 
    }
    public void runAntiEntropy(PrimaryReplica i, Replica j)
    {
      j.selectToSend();                              //j sends write to i
      i.receiveWriteFromReplica();                  //i receives writes from j
            
      i.selectToSend();                              
      j.receiveWriteFromReplica();                 
    }
    

  
}
