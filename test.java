Initialize
min = processID
processState = active     State of the process is either active/relay
                          Active means still participating in leader election
                          Relay means just forward the appropriate messages
                          
                          Goal: Elect process with lowest id as leader
Algorithm:

if(received recID from right)
{
  if(recID<min)
  {
    processState=Relay                Process is no longer considered for election since its id>min and only the min will be chosen
    min=recID
  }
  if(recID=processID)
  {
    electProcessAsLeader()            Process has the smallest id in the system
  }
}

if(the id stored in min was received 2^(min)-1 rounds ago)
{
 send min to left                     Smallest values get sent first while large values have to wait longer
}

proof:

For protocol to end successfully, 
1. One process only elected
2. Other processes become Relays

1. There are only 2 possible states of a process, Active and Relay.
In both of these states, the minimum id will be passed on.
Thus, it is sure to reach the process with the minimum id.

2. The process with the lowest ID is elected when the ID goes around the ring and reaches itself.
For this to occur, the ID must go through all other processes before reaching itself.
Also, the ID of the processor to be elected will be smaller than any other process.
Due to the property that the process becomes a Relay if it encounters an ID less than its own id,
when the chosen ID reaches the process (it has to), it will make that process a Relay and thus it wont be elected leader.
