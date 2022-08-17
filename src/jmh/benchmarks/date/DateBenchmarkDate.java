package jmh.benchmarks.date;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java 各种转 date 压测
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DateBenchmarkDate {

	@Benchmark
	public Date calendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, 1970);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.SECOND, 1);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		return calendar.getTime();
	}

	@Benchmark
	public Date localDate() {
		Instant instant = LocalDate.now().atStartOfDay()
			.atZone(ZoneOffset.UTC)
			.toInstant();
		return Date.from(instant);
	}

//	@Benchmark
//	public Date localTime() {
//		LocalTime lt = LocalTime.now();
//		Instant instant = lt.atDate(LocalDate.EPOCH)
//			.atZone(ZoneOffset.UTC)
//			.toInstant();
//		return Date.from(instant);
//	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(DateBenchmarkDate.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
