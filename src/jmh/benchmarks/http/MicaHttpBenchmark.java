package jmh.benchmarks.http;

import net.dreamlu.mica.http.HttpRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * mica http 压测
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MINUTES)
@State(Scope.Thread)
public class MicaHttpBenchmark {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

	private OkHttpClient httpClient = new OkHttpClient();

	@Benchmark
	public String micaHttp() {
		return HttpRequest.get("https://www.baidu.com/")
			.execute()
			.asString();
	}

	@Benchmark
	public String okHttp() throws IOException {
		Request request = new Request.Builder()
			.get()
			.url("https://www.baidu.com")
			.addHeader("User-Agent", USER_AGENT)
			.build();
		return httpClient.newCall(request)
			.execute()
			.body()
			.string();
	}

	@Benchmark
	public String protoTypeOkHttp() throws IOException {
		Request request = new Request.Builder()
			.get()
			.url("https://www.baidu.com")
			.addHeader("User-Agent", USER_AGENT)
			.build();
		return new OkHttpClient().newCall(request)
			.execute()
			.body()
			.string();
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(MicaHttpBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
