package com.fixme.router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Executor {

    // open server for brokers
    // new broker added to executor thread -> given an ID (hand shake happens)
    //                                     -> buy/sell msg sent to market
    //                                     -> sends outcome msg to broker
    public Executor() {
        try (
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket brokerSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(brokerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("Broker Connecting")) {
                    out.println("Give the broker a unique ID");
                }
                // else if (inputLine = a fix msg) { then queue it in thread and send to market }
            }
        } catch (
                IOException e) {
            System.out.println("IOException: " + e);
        }
    }

//    CachedThreadPool:
//    This thread pool is mostly used where there are lots of short-lived parallel tasks to be executed.
//    Unlike the fixed thread pool, the number of threads of this executor pool is not bounded.
//    If all the threads are busy executing some tasks and a new task comes, the pool will create and add
//    a new thread to the executor. As soon as one of the threads becomes free, it will take up the execution
//    of the new tasks. If a thread remains idle for sixty seconds, they are terminated and removed from cache.
//
//    However, if not managed correctly, or the tasks are not short-lived, the thread pool will have lots of
//    live threads. This may lead to resource thrashing and hence performance drop.
//
//    ExecutorService executorService = Executors.newCachedThreadPool();

//    https://stackabuse.com/concurrency-in-java-the-executor-framework/#:~:text=To%20use%20the%20Executor%20Framework,results%20from%20the%20thread%20pool.
}
