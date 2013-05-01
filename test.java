  public void receiveWriteFromReplica() throws IOException,InterruptedException
  {
    sendVersionVector(V);  			//sends V to replica server
    sendCSN(CSN);					//sends CSN to replica server    
    System.out.println("Send V done");
    Write w;
    do
    {
	    try{
	    w=receiveWrite();                                    //new write received from Replica
	    if(w.getCSN()!=-1)					// SUPPORT COMMITTED WRITES
	    {
              for(Write existingWrite : writeLog)
	      {
                  if((existingWrite.getReplicaId()==w.getReplicaId())&&(existingWrite.getAcceptTime()==w.getAcceptTime())	//check that its the same write
		  {
			writeLog.remove(existingWrite);
			committedLog.add(w);			//remove write from writeLog and add to committedLog
		  }
	      }
	      committedLog.add(w);			//if committed write doesnt exist in writeLog, add to committedLog
            }

	    System.out.println(w.getOp());
	    writeLog.add(w);
	    Integer i = V.get(w.getReplicaId());
	    i=new Integer(i.intValue() + 1);
	    V.set(w.getReplicaId(),i);                  //update version vector value of write
	    }
	    catch (Exception e)
	    {
		Thread.sleep(1000);
	      break;
	    }

    }while(w!=null);

    //check for CSN
    
    
  }
