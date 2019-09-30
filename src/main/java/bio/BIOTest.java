package bio;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.Random;

@BenchmarkMode({Mode.AverageTime})
@Warmup(iterations = 1,time = 1)
@Measurement(iterations = 1,time = 1)
public class BIOTest {

    @Benchmark
    public int run() throws IOException {
        BIOClient bioClient = new BIOClient();
        bioClient.write();
        return new Random(100l).nextInt();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BIOTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
