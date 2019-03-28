package jmh.benchmarks.bean;

import lombok.Data;

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
