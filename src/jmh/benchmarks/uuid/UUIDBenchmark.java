package jmh.benchmarks.uuid;

import cn.hutool.core.util.IdUtil;
import net.dreamlu.mica.core.utils.StringUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * uuid 测试，1 毫秒的吞吐量
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class UUIDBenchmark {

	@Benchmark
	public String micaUUId() {
		return StringUtil.getUUID();
	}

	@Benchmark
	public String hutoolFastSimpleUUID() {
		return IdUtil.fastSimpleUUID();
	}

	@Benchmark
	public String jdk8UUId() {
		return UUID.randomUUID().toString();
	}

	@Benchmark
	public String jdk8ThreadLocalRandomUUId() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		UUID uuid = new UUID(random.nextInt(), random.nextInt());
		return uuid.toString();
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(UUIDBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
