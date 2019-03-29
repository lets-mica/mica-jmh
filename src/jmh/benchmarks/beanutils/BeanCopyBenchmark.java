package jmh.benchmarks.beanutils;

import com.tuyang.beanutils.BeanCopyUtils;
import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToUser;
import jmh.benchmarks.mapper.CglibMapper;
import jmh.benchmarks.mapper.MapStructMapper;
import jmh.benchmarks.mapper.SelmaMapper;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.TimeUnit;

/**
 * bean copy 测试，1 毫秒的吞吐量
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
public class BeanCopyBenchmark {
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
	public ToUser micaBeanCopy() {
		return BeanUtil.copy(user, ToUser.class);
	}

	@Benchmark
	public ToUser hutoolBeanCopy() {
		return cn.hutool.core.bean.BeanUtil.toBean(user, ToUser.class);
	}

	@Benchmark
	public ToUser springBeanCopy() {
		return BeanUtil.copyProperties(user, ToUser.class);
	}

	@Benchmark
	public ToUser mapStructBeanCopy() {
		return MapStructMapper.MAPPER.toTarget(user);
	}

	@Benchmark
	public ToUser selmaBeanCopy() {
		return SelmaMapper.MAPPER.toTarget(user);
	}

	@Benchmark
	public ToUser yangtu222BeanCopy() {
		return BeanCopyUtils.copyBean(user, ToUser.class);
	}

	@Benchmark
	public ToUser cglibBeanCopy() {
		ToUser toUser = BeanUtil.newInstance(ToUser.class);
		BeanCopier beanCopier = BeanCopier.create(FormUser.class, ToUser.class, false);
		beanCopier.copy(user, toUser, null);
		return toUser;
	}

	@Benchmark
	public ToUser cglibMapperBeanCopy() {
		ToUser toUser = BeanUtil.newInstance(ToUser.class);
		CglibMapper.MAPPER.copy(user, toUser, null);
		return toUser;
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(BeanCopyBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
