// IBaseInterface.aidl
package com.example.routersever;

// Declare any non-default types here with import statements

interface IEventInterface {
    void notifyAll(in String message,in String type,in int pid);
}
