package jmh.benchmarks.bean;

import lombok.Data;

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
