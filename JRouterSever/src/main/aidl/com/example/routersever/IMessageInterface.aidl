// IMessageInterface.aidl
package com.example.routersever;

import com.example.routersever.DataAIDLMessage;
// Declare any non-default types here with import statements

interface IMessageInterface {
    DataAIDLMessage getMessage(in String messageTag,in int pid);
}
