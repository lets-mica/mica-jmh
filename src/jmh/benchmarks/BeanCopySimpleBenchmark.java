package jmh.benchmarks;

import com.tuyang.beanutils.BeanCopyUtils;
import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToUser;
import jmh.benchmarks.mapper.MapStructMapper;
import jmh.benchmarks.mapper.SelmaMapper;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class BeanCopySimpleBenchmark {
	private FormUser user;

	@Setup
	public void setUser() {
		user = new FormUser();
		user.setId(10000L);
		user.setAge(30);
		user.setNickName("如梦技术");
		user.setEmail("596392912@qq.com");
		user.setGender(1);
		user.setPassword("14e1b600b1fd579f47433b88e8d85291");
		user.setAvatar("https://avatars1.githubusercontent.com/u/2115440");
		user.setPhone("180********");
	}

	@Benchmark
	public ToUser micaBeanCopyAOne() {
		return BeanUtil.copy(user, ToUser.class);
	}

	@Benchmark
	public ToUser micaBeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = BeanUtil.copy(user, ToUser.class);
		}
		return toUser;
	}

	@Benchmark
	public ToUser hutoolBeanCopyAOne() {
		return cn.hutool.core.bean.BeanUtil.toBean(user, ToUser.class);
	}

	@Benchmark
	public ToUser hutoolBeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = cn.hutool.core.bean.BeanUtil.toBean(user, ToUser.class);
		}
		return toUser;
	}

	@Benchmark
	public ToUser springBeanCopyAOne() {
		return BeanUtil.copyProperties(user, ToUser.class);
	}

	@Benchmark
	public ToUser springBeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = BeanUtil.copyProperties(user, ToUser.class);
		}
		return toUser;
	}

	@Benchmark
	public ToUser mapStructBeanCopyAOne() {
		return MapStructMapper.MAPPER.toTarget(user);
	}

	@Benchmark
	public ToUser mapStructBeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = MapStructMapper.MAPPER.toTarget(user);
		}
		return toUser;
	}

	@Benchmark
	public ToUser selmaBeanCopyAOne() {
		return SelmaMapper.MAPPER.toTarget(user);
	}

	@Benchmark
	public ToUser selmaBeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = SelmaMapper.MAPPER.toTarget(user);
		}
		return toUser;
	}

	@Benchmark
	public ToUser yangtu222BeanCopyAOne() {
		return BeanCopyUtils.copyBean(user, ToUser.class);
	}

	@Benchmark
	public ToUser yangtu222BeanCopyBMillion() {
		ToUser toUser = null;
		for (int i = 0; i < 10000; i++) {
			toUser = BeanCopyUtils.copyBean(user, ToUser.class);
		}
		return toUser;
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(BeanCopySimpleBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
