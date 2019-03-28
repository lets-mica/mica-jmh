package jmh.benchmarks.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import net.dreamlu.mica.core.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
