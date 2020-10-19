package router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarketHandler implements Runnable {

    private ServerSocket marketSocket;

    MarketHandler(ServerSocket marketSocket) {
        this.marketSocket = marketSocket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        ExecutorService pool = Executors.newFixedThreadPool(1); // 3
        System.out.println("Market listening on port " + marketSocket.getLocalPort());
        System.out.println("Waiting for a market to connect...");

        try {
            while (true) {
                pool.execute(new Market(marketSocket.accept()));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private class Market implements Runnable {

        private final Socket socket;
        private PrintWriter marketOut;
        private BufferedReader marketIn;
        Generator genny = new Generator();

        Market(Socket sock) {
            this.socket = sock;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String inputLine;
            System.out.println("Market connected!");

            try {
                marketOut = new PrintWriter(socket.getOutputStream(), true);
                marketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    inputLine = marketIn.readLine();
    
                    if (inputLine.equals("Market Connecting")) {
                        int ID = genny.genMarketID();
                        // markets.add(ID);
                        Router.addNewMarket(new router.SomeName(ID, marketOut, marketIn)); //LOGIC REQUIRED
                        marketOut.println(ID);
    
                        System.out.println("Added New Market: " + ID);
                    
                    //                     // construct executor class
                    //                     Executor brokerExec = new Executor(marketIn, marketOut);
                    //                     brokerExec.openServer();
                    //                     break;
                    } else {
                        // Pass FIX order to broker via router
                        Router.messageBroker(inputLine);
                    }
                }
            } catch (IOException e) {
                //TODO Handle
                e.printStackTrace();
            }



        }
        
    }
    
}
