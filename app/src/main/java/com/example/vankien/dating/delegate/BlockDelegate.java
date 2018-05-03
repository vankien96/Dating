package com.example.vankien.dating.delegate;

/**
 * Created by vanki on 4/21/2018.
 */

public interface BlockDelegate {
    void blockSuccess();
    void blockFailed();
    void checkBlock(String block);
}
