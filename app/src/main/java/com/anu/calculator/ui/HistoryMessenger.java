package com.anu.calculator.ui;

/**
 * This interface describes methods required by an activity to enable communication between
 * fragments and the History fragment.
 *
 * This is to ensure the practice of not allowing communication directly between fragments is
 * maintained. The Google position on fragment communication is:
 *
 * Often you will want one Fragment to communicate with another, for example to change the content
 * based on a user event. All Fragment-to-Fragment communication is done through the associated
 * Activity. Two Fragments should never communicate directly.
 *
 * @author: Michael Betterton (u6797866)
 */
public interface HistoryMessenger {
    /**
     * Send a message to the history fragment.
     *
     * @author: Michael Betterton (u6797866)
     * @param data The new history data as a string to send to the history fragment
     */
    void sendHistory(String data);
}
