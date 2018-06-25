// JRouterInterface.aidl
package com.example.routersever;

import com.example.routersever.IMessageInterface;
import com.example.routersever.DataAIDLMessage;
// Declare any non-default types here with import statements

interface DataAIDLInterface {

    void Register(in IMessageInterface inter,in int pid);
    void unRegister(in IMessageInterface inter);

    DataAIDLMessage getMessage(in String messageTag,in int pid);

    void notifyAll(in String message,in String type,in int pid);
}
