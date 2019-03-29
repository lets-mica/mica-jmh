package jmh.benchmarks.beanutils;

import jmh.benchmarks.bean.FormConvertUser;
import jmh.benchmarks.bean.ToConvertUser;
import jmh.benchmarks.mapper.MapStructMapper;
import jmh.benchmarks.mapper.SelmaMapper;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * bean copy 测试
 *
 * <p>
 * https://github.com/arey/java-object-mapper-benchmark
 * </p>
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class BeanCopyConvertBenchmark {
	private FormConvertUser user;

	@Setup
	public void setUser() {
		user = new FormConvertUser();
		user.setId(10000L);
		user.setAge(30);
		user.setNickName("如梦技术");
		user.setEmail("596392912@qq.com");
		user.setGender(1);
		user.setPassword("14e1b600b1fd579f47433b88e8d85291");
		user.setAvatar("https://avatars1.githubusercontent.com/u/2115440");
		user.setPhone("180********");
		user.setBirthday(LocalDateTime.now());
	}

	@Benchmark
	public ToConvertUser micaBeanCopy() {
		return BeanUtil.copyWithConvert(user, ToConvertUser.class);
	}

	@Benchmark
	public ToConvertUser mapStructBeanCopy() {
		return MapStructMapper.MAPPER.toTarget(user);
	}

	@Benchmark
	public ToConvertUser selmaBeanCopy() {
		return SelmaMapper.MAPPER.toTarget(user);
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(BeanCopyConvertBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
