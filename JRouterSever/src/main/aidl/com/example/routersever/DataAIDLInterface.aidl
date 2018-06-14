// JRouterInterface.aidl
package com.example.routersever;

import com.example.routersever.IMessageInterface;
import com.example.routersever.DataAIDLMessage;
// Declare any non-default types here with import statements

interface DataAIDLInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void Register(in IMessageInterface inter,in int pid);
    void unRegister(in IMessageInterface inter);

    DataAIDLMessage getMessage(in String messageTag,in int pid);

}
