package jmh.benchmarks.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import jmh.benchmarks.bean.ToUser;
import net.dreamlu.mica.core.utils.BeanUtil;
import net.dreamlu.mica.core.utils.ConvertUtil;
import net.dreamlu.mica.core.utils.JsonUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类型转换测试 压测
 *
 * @author L.cm
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class BeanConvertTest {
	private static final ObjectMapper objectMapper = JsonUtil.getInstance();

	private Map<String, Object> userMap;

	@Setup
	public void setUser() {
		userMap = new HashMap<>();
		userMap.put("id", 10000L);
		userMap.put("nickName", "如梦技术");
		userMap.put("age", 30);
		userMap.put("phone", "180********");
		userMap.put("email", "596392912@qq.com");
		userMap.put("password", "14e1b600b1fd579f47433b88e8d85291");
		userMap.put("gender", 1);
		userMap.put("avatar", "https://avatars1.githubusercontent.com/u/2115440");
	}

	@Benchmark
	public Double PrimitiveSpring() {
		return ConvertUtil.convert("1.23456", Double.TYPE);
	}

	@Benchmark
	public Double PrimitiveJackson() {
		return objectMapper.convertValue("1.23456", Double.TYPE);
	}

	@Benchmark
	public ToUser MapToBeanMica() {
		return BeanUtil.copy(userMap, ToUser.class);
	}

	@Benchmark
	public ToUser MapToBeanJackson() {
		return objectMapper.convertValue(userMap, ToUser.class);
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(BeanConvertTest.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
