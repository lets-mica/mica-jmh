# mica-jmh
mica 功能压测

## bean 拷贝工具

- [MapStruct](http://mapstruct.org/)
- [Selma](http://www.selma-java.org/)
- [hutool](https://gitee.com/loolly/hutool)
- [mica](https://github.com/lets-mica/mica)

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
环境:

* OS: macOS Mojave
* CPU: 2.8 GHz Intel Core i5
* RAM: 8 GB 1600 MHz DDR3
* JVM: Oracle 1.8.0_201 64 bits

#### BeanCopySimpleBenchmark (简单模型)
| Benchmark                                   |  Mode |  Cnt |     Score |      Error |  Units |
| ---- | ---- | ---- | ---- | ---- | ---- |
| BeanCopyBenchmark.selmaBeanCopyAOne         |  avgt |    5 |     0.005 ± |    0.001 |  us/op |
| BeanCopyBenchmark.selmaBeanCopyBMillion     |  avgt |    5 |    43.293 ± |    7.949 |  us/op |
| BeanCopyBenchmark.mapStructBeanCopyAOne     |  avgt |    5 |     0.022 ± |    0.007 |  us/op |
| BeanCopyBenchmark.mapStructBeanCopyBMillion |  avgt |    5 |   192.860 ± |   48.923 |  us/op |
| BeanCopyBenchmark.micaBeanCopyAOne          |  avgt |    5 |     0.060 ± |    0.008 |  us/op |
| BeanCopyBenchmark.micaBeanCopyBMillion      |  avgt |    5 |   510.567 ± |  162.835 |  us/op |
| BeanCopyBenchmark.springBeanCopyAOne        |  avgt |    5 |     0.311 ± |    0.022 |  us/op |
| BeanCopyBenchmark.springBeanCopyBMillion    |  avgt |    5 |  3069.566 ± | 1329.840 |  us/op |
| BeanCopyBenchmark.hutoolBeanCopyAOne        |  avgt |    5 |     0.524 ± |    0.009 |  us/op |
| BeanCopyBenchmark.hutoolBeanCopyBMillion    |  avgt |    5 |  5475.577 ± | 1477.377 |  us/op |

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
| BeanCopyMapBenchmark.micaBeanCopy   |   avgt |     5 |   0.081 ± |  0.011 |   us/opop |
| BeanCopyMapBenchmark.hutoolBeanCopy |   avgt |     5 |   0.782 ± |  0.041 |   us/op |

## 结论
和 [java-object-mapper-benchmark](https://github.com/arey/java-object-mapper-benchmark) 测试结果有些出入。

Selma 的表现反而比 MapStruct 更好，可能是模型不一样导致的。

## 开源推荐
- `Avue` 一款基于vue可配置化的神奇框架：[https://gitee.com/smallweigit/avue](https://gitee.com/smallweigit/avue)
- `pig` 宇宙最强微服务（架构师必备）：[https://gitee.com/log4j/pig](https://gitee.com/log4j/pig)
- `SpringBlade` 完整的线上解决方案（企业开发必备）：[https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)
- `IJPay` 支付SDK让支付触手可及：[https://gitee.com/javen205/IJPay](https://gitee.com/javen205/IJPay)

## 微信公众号

![如梦技术](docs/img/dreamlu-weixin.jpg)

精彩内容每日推荐！!