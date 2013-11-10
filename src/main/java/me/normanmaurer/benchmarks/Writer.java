package me.normanmaurer.benchmarks;

import java.nio.channels.ClosedChannelException;

public class Writer {
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
    static {
        CLOSED_CHANNEL_EXCEPTION.setStackTrace(new StackTraceElement[0]);
    }

    void failPendingWritesNew(PendingWrite... writes) {
        for (PendingWrite write: writes) {
            write.setFailure(new ClosedChannelException());
        }
    }

    void failPendingWritesLazy(PendingWrite... writes) {
        ClosedChannelException cause = null;
        for (PendingWrite write: writes) {
            if (cause == null) {
                cause = new ClosedChannelException();
            }
            write.setFailure(cause);
        }
    }

    void failPendingWritesStatic(PendingWrite... writes) {
        for (PendingWrite write: writes) {
            write.setFailure(CLOSED_CHANNEL_EXCEPTION);
        }
    }
}
