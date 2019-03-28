# mica-jmh
mica 功能压测

## bean 拷贝工具

- [MapStruct (编译期生成 Mapper 实现)](http://mapstruct.org/) 
- [Selma (编译期生成 Mapper 实现)](http://www.selma-java.org/)
- [yangtu222 - BeanUtils (第一次生成 copy 实现字节码)](https://github.com/yangtu222/BeanUtils)
- [mica (第一次生成 copy 实现字节码)](https://github.com/lets-mica/mica)
- [hutool (反射)](https://gitee.com/loolly/hutool)

## 模型
#### 无类型转换
```java
/**
 * 来源用户
 *
 * @author L.cm
 */
@Data
public class FormUser {
	private Long id;
	private String nickName;
	private Integer age;
	private String phone;
	private String email;
	private String password;
	private Integer gender;
	private String avatar;
}

/**
 * 转换的用户
 *
 * @author L.cm
 */
@Data
public class ToUser {
	private String nickName;
	private String phone;
	private String email;
	private Integer gender;
	private String avatar;
}

```

#### 带类型转换
```java
/**
 * 附带类型转换的 用户模型
 *
 * @author L.cm
 */
@Data
@Accessors(chain = true)
public class FormConvertUser {
	private Long id;
	private String nickName;
	private Integer age;
	private String phone;
	private String email;
	private String password;
	private Integer gender;
	private String avatar;
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime birthday;
}

/**
 * 附带类型转换的 用户模型
 *
 * @author L.cm
 */
@Data
@Accessors(chain = true)
public class ToConvertUser {
	private String nickName;
	private Integer age;
	private String phone;
	private String email;
	private String password;
	private Integer gender;
	private String avatar;
	private String birthday;
}
```

## Bean copy 压测结果
#### 环境

* OS: macOS Mojave
* CPU: 2.8 GHz Intel Core i5
* RAM: 8 GB 1600 MHz DDR3
* JVM: Oracle 1.8.0_201 64 bits

#### 简单模型

| Benchmark                             |   Mode |  Cnt  |      Score   |    Error |   Units |
| ------------------------------------- | ------ | ----- | ------------ | -------- | ------- |
| BeanCopyBenchmark.hutoolBeanCopy      |  thrpt |    5  |   1939.092 ± |   26.747 |  ops/ms |
| BeanCopyBenchmark.springBeanCopy      |  thrpt |    5  |   3569.035 ± |   39.607 |  ops/ms |
| BeanCopyBenchmark.cglibBeanCopy       |  thrpt |    5  |   9112.785 ± |  560.503 |  ops/ms |
| BeanCopyBenchmark.micaBeanCopy        |  thrpt |    5  |  17753.409 ± |  393.245 |  ops/ms |
| BeanCopyBenchmark.yangtu222BeanCopy   |  thrpt |    5  |  18201.997 ± |  119.189 |  ops/ms |
| BeanCopyBenchmark.cglibMapperBeanCopy |  thrpt |    5  |  37679.510 ± | 3544.624 |  ops/ms |
| BeanCopyBenchmark.mapStructBeanCopy   |  thrpt |    5  |  50328.045 ± |  529.707 |  ops/ms |
| BeanCopyBenchmark.selmaBeanCopy       |  thrpt |    5  | 200859.561 ± | 2370.531 |  ops/ms |

#### 附带类型转换(日期)

| Benchmark                                  |  Mode  | Cnt  |     Score    |  Error     | Units  |
| ------------------------------------------ | ------ | ---- | ------------ | ---------  | ------ |
| BeanCopyConvertBenchmark.micaBeanCopy      | thrpt  |  5   |   1186.375 ± |    64.686  | ops/ms |
| BeanCopyConvertBenchmark.mapStructBeanCopy | thrpt  |  5   |   1623.478 ± |    13.894  | ops/ms |
| BeanCopyConvertBenchmark.selmaBeanCopy     | thrpt  |  5   | 160020.595 ± |  2570.747  | ops/ms |

#### 列表模型(100 item)

| Benchmark                            |   Mode |  Cnt  |  Score     | Error |  Units  |
| ------------------------------------ | ------ | ----- | ---------- | ----- | ------- |
| BeanCopyListBenchmark.springBeanCopy |  thrpt |   5   |   35.974 ± | 0.555 |  ops/ms |
| BeanCopyListBenchmark.micaBeanCopy   |  thrpt |   5   |  169.066 ± | 5.460 |  ops/ms |

#### Map 拷贝到 bean

| Benchmark                           |  Mode  | Cnt   |   Score      | Error  | Units  |
| ----------------------------------- | ------ | ----- | ------------ | ------ | ------ |
| BeanCopyMapBenchmark.hutoolBeanCopy | thrpt  |  5    |   1338.551 ± | 16.746 | ops/ms |
| BeanCopyMapBenchmark.micaBeanCopy   | thrpt  |  5    |  13577.056 ± | 27.795 | ops/ms |

## 结论
和 [java-object-mapper-benchmark](https://github.com/arey/java-object-mapper-benchmark) 测试结果有些出入。

Selma 的表现反而比 MapStruct 更好，可能是模型不一样导致的。

#### 功能比较

| 工具包 | 需要编写Mapper | 支持Map | 支持List、Set | 类型转换 | 性能 |
| ------ | -------------- | ------- | ------------- | -------- | ---- |
| Selma |  是            |  否       | 否           |  需要手写转换   | 极高 |
| MapStruct |  是            |  否       | 否           | 支持常用类型和复杂表达式 | 极高 |
| BeanUtils（yangtu222） |  否            |  否       | 是          |  需要手写转换   | 极高 |
| mica |  否            |  是      | 是          |  是用 Spring 的类型转换  | 极高 |
| Spring |  否            |  否       | 否           | 不支持 | 高 |
| hutool |  否            |  是      | 否           |  不支持   | 高 |

## 开源推荐
- `mica` Spring boot 微服务高效开发工具集：[https://gitee.com/596392912/mica](https://gitee.com/596392912/mica)
- `Avue` 一款基于vue可配置化的神奇框架：[https://gitee.com/smallweigit/avue](https://gitee.com/smallweigit/avue)
- `pig` 宇宙最强微服务（架构师必备）：[https://gitee.com/log4j/pig](https://gitee.com/log4j/pig)
- `SpringBlade` 完整的线上解决方案（企业开发必备）：[https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)
- `IJPay` 支付SDK让支付触手可及：[https://gitee.com/javen205/IJPay](https://gitee.com/javen205/IJPay)

## 微信公众号

![如梦技术](docs/img/dreamlu-weixin.jpg)

精彩内容每日推荐！!