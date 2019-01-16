package com.example.sharingapp;

import android.content.Context;

import java.util.concurrent.ExecutionException;

/**
 * Command to add a bid
 */
public class AddBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;

    public AddBidCommand(BidList bid_list, Bid bid) {
        this.bid_list = bid_list;
        this.bid = bid;
    }

    // Save bid locally
    public void execute(){
//        bid_list.addBid(bid);
//        super.setIsExecuted(bid_list.saveBids(context));
        ElasticSearchManager.AddBidTask add_bid_task = new ElasticSearchManager.AddBidTask();
        add_bid_task.execute(bid);

        // use get() to access the return of AddBidTask. i.e. AddBidTask returns a Boolean to
        // indicate if the user was successfully saved to the remote server
        try {
            if(add_bid_task.get()) {
                super.setIsExecuted(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
    }
}
