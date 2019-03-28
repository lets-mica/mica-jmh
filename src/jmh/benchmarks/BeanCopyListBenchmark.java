package jmh.benchmarks;

import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToUser;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
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
public class BeanCopyListBenchmark {
	private List<FormUser> userList;

	@Setup
	public void setUser() {
		userList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			FormUser user = new FormUser();
			user.setId(10000L + i);
			user.setAge(30 + i);
			user.setNickName("如梦技术" + i);
			user.setEmail("596392912@qq.com");
			user.setGender(1);
			user.setPassword("14e1b600b1fd579f47433b88e8d85291");
			user.setAvatar("https://avatars1.githubusercontent.com/u/2115440");
			user.setPhone("180********");
			userList.add(user);
		}
	}

	@Benchmark
	public List<ToUser> micaBeanCopy() {
		return BeanUtil.copy(userList, ToUser.class);
	}

	@Benchmark
	public List<ToUser> springBeanCopy() {
		return BeanUtil.copyProperties(userList, ToUser.class);
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
			.include(BeanCopyListBenchmark.class.getSimpleName())
			.warmupIterations(5)
			.measurementIterations(5)
			.jvmArgs("-server")
			.forks(1)
			.resultFormat(ResultFormatType.TEXT)
			.build();
		new Runner(opts).run();
	}
}
