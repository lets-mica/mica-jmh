package jmh.benchmarks.mapper;

import jmh.benchmarks.bean.FormConvertUser;
import jmh.benchmarks.bean.FormUser;
import jmh.benchmarks.bean.ToConvertUser;
import jmh.benchmarks.bean.ToUser;
import net.dreamlu.mica.core.utils.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户 拷贝
 *
 * @author L.cm
 */
@Mapper
public interface MapStructMapper {
	MapStructMapper MAPPER = Mappers.getMapper(MapStructMapper.class);

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
	@Mapping(source = "birthday", target = "birthday", dateFormat = DateUtil.PATTERN_DATETIME)
	ToConvertUser toTarget(FormConvertUser user);
}
