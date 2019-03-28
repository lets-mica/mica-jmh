package jmh.benchmarks.mapper;

import fr.xebia.extras.selma.*;
import jmh.benchmarks.bean.FormConvertUser;
import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToConvertUser;
import jmh.benchmarks.bean.ToUser;
import net.dreamlu.mica.core.utils.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Selma bean mapper
 *
 * @author L.cm
 */
@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface SelmaMapper {
	SelmaMapper MAPPER = Selma.mapper(SelmaMapper.class);

	/**
	 * bean 转换
	 *
	 * @param user 用户
	 * @return 返回用户
	 */
	ToUser toTarget(FormUser user);

	/**
	 * bean 转换
	 *
	 * @param user 用户
	 * @return 返回用户
	 */
	@Maps(withCustomFields = {
		@Field(value = "birthday", withCustom = BirthdayMapper.class)
	})
	ToConvertUser toTarget(FormConvertUser user);

	/**
	 * This mapper is called to map the birthday field
	 */
	class BirthdayMapper {

		public String mapBirthday(LocalDateTime birthday){
			return DateTimeUtil.formatDateTime(birthday);
		}

	}
}
