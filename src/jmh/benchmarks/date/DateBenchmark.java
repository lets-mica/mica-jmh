package jmh.benchmarks.date;

import net.dreamlu.mica.core.utils.DateUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java date 压测
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DateBenchmark {

	@Benchmark
	public String java8DateUtil() {
		return DateUtil.formatDateTime(LocalDateTime.now());
	}

	@Benchmark
	public String micaDateUtil() {
		return DateUtil.formatDateTime(new Date());
	}

	@Benchmark
	public String hutoolDateUtil() {
		return cn.hutool.core.date.DateUtil.formatDateTime(new Date());
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(DateBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
