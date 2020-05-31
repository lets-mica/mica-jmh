package jmh.benchmarks.date;

import net.dreamlu.mica.core.utils.DateUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java date 压测
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class DateBenchmarkTest1 {
	public static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	@Benchmark
	public String micaDataFormat() {
		return DateUtil.formatDateTime(new Date());
	}

	@Benchmark
	public String fastDateFormat() {
		return FAST_DATE_FORMAT.format(new Date());
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(DateBenchmarkTest1.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
