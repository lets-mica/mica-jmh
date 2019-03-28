package jmh.benchmarks.bean;

import lombok.Data;
import lombok.experimental.Accessors;

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
