package me.normanmaurer.benchmarks;

import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

@State(Scope.Thread)
public class ThrowableBenchmark {
    // experiment test input
    private PendingWrite[] pendingWrites;

    // Destination buffer, the slayer
    private final Writer writer = new Writer();

    @Setup
    public void init() {
        pendingWrites = new PendingWrite[10000];
        for (int i = 0 ; i < pendingWrites.length; i++) {
            pendingWrites[i] = new PendingWrite();
        }
    }

    private void closeStream(InputStream inStream) {
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void closeReader(Reader buffReader) {
        if (buffReader != null) {
            try {
                buffReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GenerateMicroBenchmark
    public int failPendingWritesNew() {
        writer.failPendingWritesNew(pendingWrites);

        return pendingWrites.length;
    }

    @GenerateMicroBenchmark
    public int failPendingWritesLazy() throws UnsupportedEncodingException {
        writer.failPendingWritesLazy(pendingWrites);
        return pendingWrites.length;
    }

    @GenerateMicroBenchmark
    public int failPendingWritesStatic() throws UnsupportedEncodingException {
        writer.failPendingWritesStatic(pendingWrites);
        return pendingWrites.length;
    }
}
