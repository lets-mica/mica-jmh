package jmh.benchmarks.hex;

import cn.hutool.crypto.SecureUtil;
import net.dreamlu.mica.core.utils.DigestUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * hex 测试，1 毫秒的吞吐量
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class HexBenchmark {

	@Benchmark
	public String md5HexUtils() {
		return HexUtils.md5Hex("Miss程_高精准IP地址库 评论了你的文章 mica 2.0.1 发布新增最好用的 ip2region boot stater", StandardCharsets.UTF_8);
	}

	@Benchmark
	public String md5HexMica() {
		return DigestUtil.md5Hex("Miss程_高精准IP地址库 评论了你的文章 mica 2.0.1 发布新增最好用的 ip2region boot stater");
	}

	@Benchmark
	public String md5HexHutool() {
		return SecureUtil.md5().digestHex("Miss程_高精准IP地址库 评论了你的文章 mica 2.0.1 发布新增最好用的 ip2region boot stater");
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(HexBenchmark.class.getSimpleName())
			.warmupIterations(3)
			.measurementIterations(3)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
