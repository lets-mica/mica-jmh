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

#### BeanCopySimpleBenchmark (简单模型)
| Benchmark                                         |  Mode |  Cnt |     Score |      Error |  Units |
| ---- | ---- | ---- | ---- | ---- | ---- |
| BeanCopyBenchmark.selmaBeanCopyAOne               |  avgt |    5 |     0.005 ± |    0.001 |  us/op |
| BeanCopyBenchmark.selmaBeanCopyBMillion           |  avgt |    5 |    43.293 ± |    7.949 |  us/op |
| BeanCopyBenchmark.mapStructBeanCopyAOne           |  avgt |    5 |     0.022 ± |    0.007 |  us/op |
| BeanCopyBenchmark.mapStructBeanCopyBMillion       |  avgt |    5 |   192.860 ± |   48.923 |  us/op |
| BeanCopyBenchmark.micaBeanCopyAOne                |  avgt |    5 |     0.058 ± |    0.003 |  us/op |
| BeanCopyBenchmark.micaBeanCopyBMillion            |  avgt |    5 |   549.505 ± |  151.253 |  us/op |
| BeanCopySimpleBenchmark.yangtu222BeanCopyAOne     |  avgt |    5 |     0.054 ± |    0.005 |  us/op |
| BeanCopySimpleBenchmark.yangtu222BeanCopyBMillion |  avgt |    5 |   604.569 ± |  364.083 |  us/op |
| BeanCopyBenchmark.springBeanCopyAOne              |  avgt |    5 |     0.311 ± |    0.022 |  us/op |
| BeanCopyBenchmark.springBeanCopyBMillion          |  avgt |    5 |  3069.566 ± | 1329.840 |  us/op |
| BeanCopyBenchmark.hutoolBeanCopyAOne              |  avgt |    5 |     0.524 ± |    0.009 |  us/op |
| BeanCopyBenchmark.hutoolBeanCopyBMillion          |  avgt |    5 |  5475.577 ± | 1477.377 |  us/op |

#### BeanCopyListBenchmark (列表模型 100 item)
| Benchmark                             |  Mode |  Cnt |   Score |   Error |  Units |
| ---- | ---- | ---- | ---- | ---- | ---- |
| BeanCopyListBenchmark.micaBeanCopy    |  avgt |    5 |   6.401 ± | 1.367 |  us/op |
| BeanCopyListBenchmark.springBeanCopy  |  avgt |    5 |  31.197 ± | 8.678 |  us/op |

#### BeanCopyConvertBenchmark (附带类型转换-日期)
| Benchmark                                  |   Mode |  Cnt |  Score |   Error |  Units |
| ---- | ---- | ---- | ---- | ---- | ---- |
| BeanCopyConvertBenchmark.selmaBeanCopy     |   avgt |    3 |  0.296 ± | 0.002 |  us/op |
| BeanCopyConvertBenchmark.mapStructBeanCopy |   avgt |    3 |  0.618 ± | 0.022 |  us/op |
| BeanCopyConvertBenchmark.micaBeanCopy      |   avgt |    3 |  0.884 ± | 0.038 |  us/op |

#### BeanCopyMapBenchmark (Map 拷贝到 bean)
| Benchmark                           |   Mode |   Cnt |   Score |    Error |   Units |
| ---- | ---- | ---- | ---- | ---- | ---- |
| BeanCopyMapBenchmark.micaBeanCopy   |   avgt |     5 |   0.081 ± |  0.011 |   us/op |
| BeanCopyMapBenchmark.hutoolBeanCopy |   avgt |     5 |   0.782 ± |  0.041 |   us/op |

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